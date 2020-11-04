package com.nts.iot.manage;

import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.dto.DeviceVo;
import com.nts.iot.dto.Fence;
import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 电子围栏管理
 * <p>
 * 数据引擎模块，判断当前所有的车辆是否都在规定的电子围栏进行巡逻。
 * 1.每10分钟执行一次
 * // 不做判断 ----2.查询在运行的车辆的信息（读取缓存）
 * 3.根据车辆所在的辖区，找到该辖区的电子围栏。调用高德的api，判断当前车辆是否在该辖区的电子围栏中，如果不在围栏中进行报警。流出接口即可。
 */
@Component
public class ElectronicFenceManager {

    @Autowired
    private RedisUtil redisUtil;

//    @Autowired
//    private GaoDeMapApiManager gaoDeMapApiManager;

    /**
     * 电子围栏监控
     */
    public List<CollectMessage> electronicFenceMonitoring() {
        // 返回越界的锁结果集
        List<CollectMessage> resultList = new ArrayList<>();
        // 车锁list
        List<String> lockList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.TRACKER_LIST_KEY), String.class);
        // 声明数据类型
        Map<Long, List<CollectMessage>> map = new HashMap<>();
        for (String lockCode : lockList) {
            String key = RedisKey.VECHILE_STATE + lockCode;
            /*
             *  获得状态key
             *  取出车辆的坐标点
             */
            CollectMessage collectMessage = JsonUtil.jsonConvertObject(redisUtil.getData(key), CollectMessage.class);
            /*
             *  获得车辆redisKey
             *  此步骤是为了到缓存中查询辖区
             */
            if (collectMessage != null) {
                // 从redis中获得车辆信息
                DeviceVo device = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.TRACKER_KEY + collectMessage.getDeviceNo()), DeviceVo.class);
                if (device != null) {
                    /*
                     * 判断车辆在哪个辖区中
                     *
                     * Map key辖区编号  value 消息体
                     * Map<String,CollectMessage>
                     */
                    // 判空
                    Long fenceId = device.getFenceId();
                    if (null == fenceId) {
                        continue;
                    }
                    // 辖区编号是否在map中存在
                    if (map.containsKey(fenceId)) {
                        List<CollectMessage> collectMessageList = map.get(fenceId);
                        // 添加消息体
                        collectMessageList.add(collectMessage);
                        // 加入到map中
                        map.put(fenceId, collectMessageList);
                    }
                    // 不存在
                    else {
                        List<CollectMessage> collectMessageList = new ArrayList<>();
                        // 添加消息体
                        collectMessageList.add(collectMessage);
                        // 加入到map中
                        map.put(fenceId, collectMessageList);
                    }
                }
            }
        }
        // map有值的情况
        if (map.size() > 0) {
            // map 判断是否出围栏
            resultList = getRail(map);
        }
        return resultList;
    }

    /**
     * 获得围栏信息
     */
    private List<CollectMessage> getRail(Map<Long, List<CollectMessage>> map) {
        List<CollectMessage> resultList = new ArrayList<>();
        //遍历map中的键
        for (Map.Entry<Long, List<CollectMessage>> entry : map.entrySet()) {
            // 围栏id
            Long Key = entry.getKey();
            // 查询电子围栏集合
            List<Fence> railList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.RESTRICTIONS_FENCE + Key), Fence.class);
            // 指定辖区下，锁信息返回结果集合
            List<CollectMessage> resultCollectMessageList = borderFence(railList, entry.getValue());
            // 锁信息返回结果集合判空
            if (!CollectionUtils.isEmpty(resultCollectMessageList)) {
                resultList.addAll(resultCollectMessageList);
            }
//            if (resultCollectMessageList != null && resultCollectMessageList.size() > 0) {
//                for (int i = 0; i < resultCollectMessageList.size(); i++) {
//                    resultList.add(resultCollectMessageList.get(i));
//                }
//            }
        }
        return resultList;
    }

    /**
     * 调用高德地图判断是否越出围栏
     *
     * @param railList           电子围栏集合
     * @param collectMessageList 车辆消息体集合
     * @return
     */
    private List<CollectMessage> borderFence(List<Fence> railList, List<CollectMessage> collectMessageList) {
        // 调用高德Api 查询是否出围栏
        return EnclosureUtil.checkRail(railList, collectMessageList);
    }
}

package com.nts.iot.manage;

import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.Bike;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.dto.VirtualPile;
import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 虚拟装
 */
@Component
public class VirtualPileManager {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 校验车辆是否在虚拟装内
     */
    public List<CollectMessage> checkVirtualPile() {
        // 返回越界的车辆结果集
        List<CollectMessage> resultList = new ArrayList<>();
        // 声明数据类型
        Map<Long, List<CollectMessage>> map = new HashMap<>();
        // 车锁list
        List<String> lockList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.TRACKER_LIST_KEY), String.class);
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
            // 从redis中获得车辆信息
            Bike bike = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.TRACKER_KEY + collectMessage.getDeviceNo()), Bike.class);

            /*
             * 判断车辆在哪个辖区中
             *
             * Map key辖区编号  value 消息体
             * Map<String,CollectMessage>
             */
            // 判空
            if (bike != null && collectMessage != null) {
                Long jurisdiction = bike.getJurisdiction();
                // 辖区编号是否在map中存在
                if (map.containsKey(jurisdiction)) {
                    List<CollectMessage> collectMessageList = map.get(jurisdiction);
                    // 添加消息体
                    collectMessageList.add(collectMessage);
                    // 加入到map中
                    map.put(jurisdiction, collectMessageList);
                }
                // 不存在
                else {
                    List<CollectMessage> collectMessageList = new ArrayList<>();
                    // 添加消息体
                    collectMessageList.add(collectMessage);
                    // 加入到map中
                    map.put(jurisdiction, collectMessageList);
                }
            }
        }
        // map有值的情况
        if (map.size() > 0) {
            // map 判断是否出围栏
            resultList = getVirtualPile(map);
        }

        return resultList;
    }

    /**
     * 获得虚拟装信息
     */
    private List<CollectMessage> getVirtualPile(Map<Long, List<CollectMessage>> map) {
        List<CollectMessage> resultList = new ArrayList<>();
        //遍历map中的键
        for (Map.Entry<Long, List<CollectMessage>> entry : map.entrySet()) {
            // 辖区编号
            Long Key = entry.getKey();
            // 车辆信息
            List<CollectMessage> collectMessageList = entry.getValue();
            // 查询虚拟装
            String coordinateAndRadius = getVirtualPileCoordinateAndRadius(JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.VIRTUAL_PILE + Key), VirtualPile.class));
            List<CollectMessage> resultCollectMessageList = EnclosureUtil.checkVirtualPile(coordinateAndRadius, collectMessageList);
            // 锁信息返回结果集合判空
            if (resultCollectMessageList != null && resultCollectMessageList.size() > 0) {
                for (int i = 0; i < resultCollectMessageList.size(); i++) {
                    resultList.add(resultCollectMessageList.get(i));
                }
            }
        }
        return resultList;
    }

    /**
     * 获得虚拟装 坐标加半径  经度，纬度，半径
     */
    private String getVirtualPileCoordinateAndRadius(VirtualPile virtualPile) {
        return virtualPile.getLongitude() + "," + virtualPile.getLatitude() + "," + virtualPile.getScope();
    }
}

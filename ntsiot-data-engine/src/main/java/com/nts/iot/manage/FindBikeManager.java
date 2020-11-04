package com.nts.iot.manage;

import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询指定区域内的车辆
 */
@Component
public class FindBikeManager {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询指定区域内的车辆 根据坐标点
     *
     * @param point 点 = 经度，纬度
     * @return
     */
    public List<CollectMessage> getCollectMessageByPoint(String point) {
        List<CollectMessage> collectMessageList = new ArrayList<>();
        // 车锁list
        List<String> lockList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.TRACKER_LIST_KEY), String.class);
        for (String lockCode : lockList) {
            String key = RedisKey.VECHILE_STATE + lockCode;
            CollectMessage collectMessage = JsonUtil.jsonConvertObject(redisUtil.getData(key), CollectMessage.class);
            if (collectMessage != null) {
                String collectMessagePoint = collectMessage.getLongitude() + "," + collectMessage.getLatitude();
                // 判断车辆是否在圈内
                if (checkPoint(point, collectMessagePoint)) {
                    collectMessageList.add(collectMessage);
                }
            }
        }
        return collectMessageList;
    }

    /**
     * 校验车是否在圈中，在的情况返回true
     *
     * @param point               传入的点
     * @param collectMessagePoint 车的坐标点
     * @return
     */
    private Boolean checkPoint(String point, String collectMessagePoint) {
        // 加上半径 2千米
        return EnclosureUtil.checkPoint("2", point, collectMessagePoint);
    }
}

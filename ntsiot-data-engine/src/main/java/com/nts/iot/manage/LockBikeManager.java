package com.nts.iot.manage;


import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.Bike;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 锁车管理
 */
@Component
public class LockBikeManager {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 锁车管理方法
     *
     * @param deviceNo 锁编号
     * @return
     */
    public Boolean lockBike(String deviceNo) {
        Boolean flag = false;
        // 先通过key查询车辆上一次状态
        String stateJson = redisUtil.getData(RedisKey.VECHILE_STATE + deviceNo);
        // 车辆状态存在
        if (stateJson != null && !"".equals(stateJson)) {
            CollectMessage collectMessage = JsonUtil.jsonConvertObject(stateJson, CollectMessage.class);
            // 获得围栏集合
            List<String> railList = getRail(collectMessage);
            // 判断是否在围栏中 true:在围栏中,false:不在围栏中
            flag = EnclosureUtil.checkRail(railList, collectMessage);
        }
        return flag;
    }

    /**
     * 获得电子围栏
     *
     * @param collectMessage 车锁信息
     * @return
     */
    private List<String> getRail(CollectMessage collectMessage) {
        // 从redis中获得车辆信息
        Bike bike = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.TRACKER_KEY + collectMessage.getDeviceNo()), Bike.class);
        // 查询指定辖区下电子围栏集合
        return JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.RESTRICTIONS_FENCE + bike.getJurisdiction()), String.class);
    }

}

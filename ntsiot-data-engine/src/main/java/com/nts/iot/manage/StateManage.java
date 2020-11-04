package com.nts.iot.manage;


import cn.hutool.core.date.DateUtil;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 自行车状态管理
 * <p>
 * 值存储一条记录，后续的根据key来更新这条记录。用于查看当前车的位置。
 */
@Slf4j
@Component
public class StateManage {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据车辆code 更新车辆状态
     *
     * @param message 加入kafka的消息体
     */
    public void updateState(CollectMessage message) {
        System.out.println("**********************************************");
        System.out.println("***************更新单车状态: " + message.getDeviceNo() + "*******************");
        System.out.println("***************经度: " + message.getLongitude() + "::维度：" + message.getLatitude() + "*******************");
        System.out.println("*********************" + message.getCourse() + "*************************");
        System.out.println("**********************************************");
        log.info("updateState KafkaListener=" + message);
        // 先通过key查询车辆上一次状态
        message.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        redisUtil.deleteByKey(RedisKey.VECHILE_STATE + message.getDeviceNo());
        redisUtil.addRedis(RedisKey.VECHILE_STATE + message.getDeviceNo(), JsonUtil.getJson(message));
        System.out.println("***************更新单车状态成功******************************");
    }

}

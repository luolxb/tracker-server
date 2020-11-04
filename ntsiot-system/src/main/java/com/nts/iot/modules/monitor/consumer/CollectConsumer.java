/*******************************************************************************
 * @(#)CollectConsumer.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.monitor.consumer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.modules.miniApp.dto.BikeDto;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.JurisdictionConfiguration;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.JurisdictionConfigurationService;
import com.nts.iot.modules.system.service.OrderManagerService;
import com.nts.iot.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * system监听
 */
@Slf4j
@Component
public class CollectConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private BikeManagerService bikeManagerService;
    @Autowired
    private OrderManagerService orderManagerService;
    @Autowired
    private MaUserService maUserService;
    @Autowired
    private JurisdictionConfigurationService jurisdictionConfigurationService;

    private static final String TOPIC = "collect.message.topic";

    @KafkaListener(topics = TOPIC)
    public void listen(String message) {
        try {
            log.info("recieve msg from data-engine, msg --> {}", message);
            CollectMessage collectMessage = JsonUtil.jsonConvertObject(message, CollectMessage.class);
            String deviceNo = collectMessage.getDeviceNo();
            //根据deviceNo获取jurisdiction_id
            Bike bike = bikeManagerService.getBikeByDeviceNo(deviceNo);
            if (Objects.isNull(bike) || Objects.isNull(bike.getJurisdiction())) {
                log.info("error data , bike --> {}", JSON.toJSONString(bike));
                return;
            }
            JurisdictionConfiguration jurisdictionConfiguration = jurisdictionConfigurationService.getJurisdictionConfiguration(bike.getJurisdiction());
            if (Objects.isNull(jurisdictionConfiguration)) {
                log.info("error data , jurisdictionConfiguration --> {}", JSON.toJSONString(jurisdictionConfiguration));
                return;
            }
            BikeDto bikeDto = new BikeDto();
            BeanUtils.copyProperties(bike, bikeDto);
            String bikeCode = bike.getBikeBarcode();
            bikeDto.setBikeCode(bikeCode);
            if (bike.getBikeBarcode().length() > 7) {
                // 截取后7位
                bikeCode = bike.getBikeBarcode().substring(bike.getBikeBarcode().length() - 7);
            }
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("bike_barcode", bike.getBikeBarcode());
            queryWrapper.eq("status", 2L);
            Order order = orderManagerService.getOne(queryWrapper);
            //如果车辆有正在进行的订单，则前端显示成 “车辆编号-用户名；如果没订单但是车辆绑定用户，则前端显示成 “车辆编号-用户名”
            if (Objects.nonNull(order)) {
                MaUser maUser = maUserService.getById(order.getUserId());
                bike.setBikeBarcode(bikeCode + "-" + maUser.getName());
                bike.setPhone(maUser.getPhone());
            } else if (StrUtil.isNotEmpty(bike.getUser())) {
                bike.setBikeBarcode(bikeCode + "-" + bike.getUser());
            } else {
                bike.setBikeBarcode(bikeCode);
            }
            bikeDto.setShowRealLine(jurisdictionConfiguration.getShowRealLine());
            bikeDto.setColor(jurisdictionConfiguration.getColor());
            bikeDto.setCollectMessage(collectMessage);
            log.info("bikeDto {}", JSON.toJSONString(bikeDto));
            messagingTemplate.convertAndSend("/topic/bikes/monitor/" + bike.getJurisdiction(), bikeDto);
            log.info("push msg success! jurisdiction --> {}, deviceNo --> {}", bike.getJurisdiction(), deviceNo);
        } catch (Exception e) {
            log.error("consumer error", e);
        }
    }

}

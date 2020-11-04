/*******************************************************************************
 * @(#)CollectConsumer.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.consumer;

import com.nts.iot.dto.CollectLock;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.manage.LockManager;
import com.nts.iot.manage.OrderTrackManager;
import com.nts.iot.manage.StateManage;
import com.nts.iot.manage.TrackManager;
import com.nts.iot.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 采集消费者的业务处理
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-02 21:54
 */
@Slf4j
@Component
public class CollectConsumer {

    @Autowired
    private OrderTrackManager orderTrackManager;
    @Autowired
    private StateManage stateManage;
    @Autowired
    private TrackManager trackManager;
    @Autowired
    private LockManager lockManager;
//    private static final String LOCALPOSITION_TOPIC_WIFLLBS = "collect.message.topic.wifi";


    private static final String TOPIC = "collect.message.topic";

    private static final String COLLECT_LOCK_TOPIC = "collect.lock.topic";

    @KafkaListener(topics = TOPIC)
    public void listen(String message) {
       /* System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("KafkaListener="+message);
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");*/
        log.info("listen start KafkaListener=" + message);
        CollectMessage collectMessage = JsonUtil.jsonConvertObject(message, CollectMessage.class);
        /**
         * 1.值存储一条记录，后续的根据key来更新这条记录。用于查看当前车的位置。
         * 2.如果生成订单了，那么会在缓存中产生一条记录，key是订单号+设备编号的一个字符串，如果有这种key那么每次就将数据上传加入到这个value中，
         * value是一个list集合，订单完成之后将这个集合写入到es中，key就是订单号，value中有轨迹，还有必到点停留的时间计算。
         * 3.存储一个集合记录，每次都是添加进入。一个小时已清空，并且将一个小时的记录写入到es中，key是年月日时
         */
        // 添加更新设备状态
        stateManage.updateState(collectMessage);
        // 添加订单轨迹
        orderTrackManager.addOrderTrack(collectMessage);
        // 增加动态添加锁信息
        trackManager.addTrack(collectMessage);
        log.info("listen end KafkaListener=" + message);
    }

    @KafkaListener(topics = COLLECT_LOCK_TOPIC)
    public void listenerLock(String message) {
       /* System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("KafkaListener=" + message);
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");*/
        log.info("listenerLock KafkaListener=" + message);
        CollectLock collectLock = JsonUtil.jsonConvertObject(message, CollectLock.class);
        lockManager.addLock(collectLock);
    }

//    /**
//     * WiFiLBS定位消息接受
//     * @param message
//     */
//    @KafkaListener(topics = LOCALPOSITION_TOPIC_WIFLLBS)
//    public void listenerWiFiLBSk(String message) {
//        /**
//         * 协议未完整，还需要增加除位置信息外，增加锁，电池等状态。

//             此段接口移植到网关程序中。
//         */
//        log.info(message);
//    }
}

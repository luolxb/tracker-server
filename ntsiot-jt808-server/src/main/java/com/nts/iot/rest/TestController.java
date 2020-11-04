/*******************************************************************************
 * @(#)TestController.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.rest;

import com.alibaba.fastjson.JSON;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.jt808.protocol.JT_0200;
import com.nts.iot.jt808.protocol.message.T808Message;
import com.nts.iot.jt808.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-02 21:40
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("test")
    public String test() {
        kafkaTemplate.send("test", "hello world");
        return "ok";
    }

    @GetMapping("CollectConsumer")
    public String CollectConsumer() {
        T808Message jt0200 = new T808Message();
        byte[] bytes = Tools.HexString2Bytes("7e0200003ac280361367780000000000000000000001d96a30073d24e0000000000000000000000000300117310100e104073d24e0e2020040e306005c102a1068e5044f019b104e7e");
        jt0200.readFromBytes(bytes);
        System.out.println(jt0200.getHeader().getSimId());
        System.out.println(jt0200.toString());
        CollectMessage message = new CollectMessage(jt0200.getHeader().getSimId(),(JT_0200) jt0200.getMessageContents());
        String messageStr = JSON.toJSONString(message);
        System.out.println(messageStr);
        kafkaTemplate.send("collect.message", messageStr);
        return "ok";
    }


    /* 反控 */


}

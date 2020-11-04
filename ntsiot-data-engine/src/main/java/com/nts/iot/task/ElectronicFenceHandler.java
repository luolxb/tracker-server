/*******************************************************************************
 * @(#)ElectronicFenceHandler.java 2019-05-03
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.task;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.manage.ElectronicFenceManager;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 电子围栏任务，每5分钟，将所有的车辆的当前坐标，通过高德地图进行电子围栏判断，当前车辆是否已经超出电子围栏，如果超出围栏进行报警。调用后台管理系统
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-03 09:22
 */
@Slf4j
@Component
//@JobHandler
@EnableScheduling
public class ElectronicFenceHandler {

    @Autowired
    private ElectronicFenceManager electronicFenceManager;

    @Value("${api.server.url}")
    private String serverUrl;

    // 每10分钟执行一次
    @Scheduled(cron = "0 */10 * * * *")
    public void execute() {
        /**
         * 返回不在围栏中车辆的信息
         */
        List<CollectMessage> collectMessageList = electronicFenceManager.electronicFenceMonitoring();
        //System.out.println("电子围栏监控=" + collectMessageList);
        log.info("电子围栏监控=" + collectMessageList);
        if (collectMessageList != null && collectMessageList.size() > 0) {
            /*for (CollectMessage collectMessage : collectMessageList) {
                System.out.println("collectMessage电子围栏监控=" + collectMessage);
            }*/
//            String result1 = HttpRequest.post(serverUrl + "/ma/noticeOuter").body(JSON.toJSONString(collectMessageList)).execute().body();
            String result1 = HttpRequest.post(serverUrl + "/api/alert/create").body(JSON.toJSONString(collectMessageList)).execute().body();
            //System.out.println("发送成功 "+ result1);
            log.info("发送成功 " + result1);
        }
        //System.out.println("电子围栏监控定时任务！time=" + System.currentTimeMillis());
        //log.info("电子围栏监控定时任务完成！");
    }

}

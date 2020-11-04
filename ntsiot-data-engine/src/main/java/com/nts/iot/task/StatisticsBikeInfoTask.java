package com.nts.iot.task;

import com.nts.iot.entity.Security;
import com.nts.iot.service.StatisticsBikeInfoService;
import com.nts.iot.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
//@JobHandler
@EnableScheduling
public class StatisticsBikeInfoTask {
    @Autowired
    private StatisticsBikeInfoService statisticsBikeInfoService;

    // 每天0点15分执行
    @Scheduled(cron = "0 15 0 ? * *")
    // 每10分钟执行一次
//    @Scheduled(cron = "0 */1 * * * ?")
    public void execute() {
        /**
         * 定时统计车辆订单信息
         */
        List<Security> securityList = statisticsBikeInfoService.statisticsBikeInfo();
        //System.out.println("定时统计车辆订单信息=" + securityList);
        log.info("定时统计车辆订单信息=" + JsonUtil.getJson(securityList));
        /*if (securityList != null && securityList.size() > 0) {
            for (int i = 0; i < securityList.size(); i++) {
                System.out.println("securityList定时统计车辆订单信息=" + securityList);
            }
        }
        System.out.println("定时统计车辆订单信息！time=" + System.currentTimeMillis());*/
    }
}

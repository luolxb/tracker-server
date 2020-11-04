package com.nts.iot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: ntsiot-jt808-server
 * @description: 批量升级任务启动
 * @author: wongshan
 * @create: 2019-07-31 14:40
 **/
@Slf4j
@Component
public class UpgradeListener implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        new Thread(new UpgradeRunable(),"开始启动升级任务进程").start();
        //取消 批量升级任务

    }
}

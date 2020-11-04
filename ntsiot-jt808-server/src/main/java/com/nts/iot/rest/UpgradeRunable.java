package com.nts.iot.rest;

import com.nts.iot.session.SessionManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: ntsiot-jt808-server
 * @description: 批量升级任务
 * @author: wongshan
 * @create: 2019-07-31 14:36
 **/
@Slf4j
public class UpgradeRunable implements Runnable{
    private SessionManager manager = SessionManager.INSTANCE;

    @Override
    public void run() {
//        log.info("批量升级线程已经启动！");
        while (true){
            try {
                // 睡眠一下，不能全速跑
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/*******************************************************************************
 * @(#)EngineRun.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-02 21:42
 */
@SpringBootApplication
public class EngineRun {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(EngineRun.class, args);
        System.out.println("=====================数据引擎启动成功===================");
        System.out.println("=====================数据引擎启动成功===================");
        System.out.println("=====================数据引擎启动成功===================");
        System.out.println("=====================数据引擎启动成功===================");
        System.out.println("=====================数据引擎启动成功===================");
    }

}

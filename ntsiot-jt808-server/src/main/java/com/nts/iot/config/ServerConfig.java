/*******************************************************************************
 * @(#)ServerConfig.java 2019-04-29
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.config;

import com.nts.iot.util.SpringContextHolder;
import com.nts.iot.server.TCPJT808Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-29 11:41
 */
@Configuration
public class ServerConfig {

    @Value("${jt808.server.port}")
    private Integer port;

    @Bean
    public TCPJT808Server tcpServer() {
        return new TCPJT808Server(port);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @PostConstruct
    public void init() {
        tcpServer().start();
    }

}

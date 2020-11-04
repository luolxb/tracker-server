/*******************************************************************************
 * @(#)TCPServer.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.server;

import com.nts.iot.jt808.codec.JT808MessageDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * jt808协议的server
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 13:28
 */
@Slf4j
public class TCPJT808Server {

    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;
    private int port;


    public TCPJT808Server() {

    }

    public TCPJT808Server(int port) {
        this.port = port;
    }

    private void bind() throws Exception {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) {
                channel.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
                channel.pipeline().addLast("decoder", new JT808MessageDecoder());
                channel.pipeline().addLast("encoder", new ByteArrayEncoder());
                channel.pipeline().addLast(new TCPServerHandler());
            }
        });
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 不延迟发送消息
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.option(ChannelOption.TCP_NODELAY, true);
        log.info("TCP服务启动完毕,port={}", this.port);
        channelFuture = serverBootstrap.bind(port).sync();
//        channelFuture.channel().closeFuture().sync();
    }

    public synchronized void start() {
        if (this.isRunning) {
            throw new IllegalStateException("Jt808协议server已经启动！");
        }
        this.isRunning = true;
        new Thread(() -> {
            try {
                this.bind();
            } catch (Exception e) {
                log.info("TCP服务启动出错:{}", e.getMessage());
                e.printStackTrace();
            }
        }, this.getName()).start();
    }

    private String getName() {
        return "TCP-Server";
    }
}

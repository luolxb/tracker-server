/*******************************************************************************
 * @(#)TCPServerHandler.java 2019-04-29
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.server;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.influxService.LogsService;
import com.nts.iot.jt808.protocol.message.T808Message;
import com.nts.iot.util.RedisUtil;
import com.nts.iot.util.SpringContextHolder;
import com.nts.iot.command.MessageCommand;
import com.nts.iot.session.Session;
import com.nts.iot.session.SessionManager;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-29 14:51
 */
@Slf4j
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private LogsService logsService;

    @Autowired
    private RedisUtil redisUtil;

    private final SessionManager sessionManager = SessionManager.INSTANCE;

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {

        log.info("TCPServerHandler => channelRead =>{}",msg);
        T808Message messageResponse;
        try {
            Channel channel = context.channel();
            // 如果通道关闭
            if (!channel.isOpen() || !channel.isWritable() || !channel.isActive()){
                System.out.println("发现问题通道，该包丢弃");
                return;
            }
            if (msg instanceof T808Message) {
                T808Message message = (T808Message) msg;
                log.info("来消息:"+((T808Message) msg).getPacketDescr());
                Session session = sessionManager.getByMobileNumber(message.getSimNo());
                if (session == null) {
                    session = Session.buildSession(channel, message.getSimNo());
                    sessionManager.put(session.getId(), session);
                }
                // 如果通道号不同，表示最开始保存的通道已经关闭，需要更新通道号
                if (!channel.id().asLongText().equals(session.getId())) {
                    sessionManager.removeBySessionId(session.getId());
                    session = Session.buildSession(channel, message.getSimNo());
                    sessionManager.put(session.getId(), session);
                }
                message.getHeader().setMessageSerialNo((short)session.currentFlowId());
                MessageCommand command = SpringContextHolder.getBean(MessageCommand.class);
                messageResponse = command.command(message);
                byte[] datas = messageResponse.writeToBytes();
                if (messageResponse != null) {
                    ChannelFuture cf = channel.writeAndFlush(Unpooled.copiedBuffer(datas)).sync();
                    //添加ChannelFutureListener以便在写操作完成后接收通知
                    cf.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            //写操作完成，并没有错误发生
                            if (future.isSuccess()){
                                log.info("应答消息发送成功！");
                            }else{
                                //记录错误
                                log.info("应答消息发送失败.error");
                                future.cause().printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("======================channelActive======================");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("======================channelInactive======================");
        super.channelInactive(ctx);
        /**
         * 设备主动离线（1.设备主动下载。2.基站踢下线），也需要把session清除掉。
         */
        Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
        log.info("设备离线通知 :"+session.getTerminalId());
        logsService.addPointNormalLog(session.getTerminalId(),session.getTerminalId()+" 设备主动下线");

        // 移除心跳
        redisUtil.deleteByKey(RedisKey.TRACKER_HEARTBEAT_KEY+ session.getTerminalId());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        String sessionId = Session.buildId(ctx.channel());
        Session session = sessionManager.getBySessionId(sessionId);
//        log.error("发生异常"+session.getTerminalId());
//        e.printStackTrace();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
                log.info("设备心跳超时 "+session.getTerminalId());
                logsService.addPointNormalLog(session.getTerminalId(),session.getTerminalId()+" 设备心跳超时");
                ctx.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            Map<String, Object> params1 = new HashMap<>();
            params1.put("header", "8500");
            params1.put("deviceNo", "C27045635328");
            params1.put("type", "0100");
            String result1 = HttpRequest.post("http://115.159.71.171:8081/api/counterControl").body(JSON.toJSONString(params1)).execute().body();
            System.out.println("开锁：" + result1);
            Thread.sleep(10000);
            Map<String, Object> params = new HashMap<>();
            params.put("header", "8500");
            params.put("deviceNo", "C27045635328");
            params.put("type", "0101");
            String result = HttpRequest.post("http://115.159.71.171:8081/api/counterControl").body(JSON.toJSONString(params)).execute().body();
            System.out.println("解锁：" + result);
            Thread.sleep(10000);
            System.out.println("执行完第:" + (i + 1) + "次");
        }
    }
}

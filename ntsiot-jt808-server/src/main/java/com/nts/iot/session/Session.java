/*******************************************************************************
 * @(#)Session.java 2019-05-01
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.session;

import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-01 11:10
 */
public class Session {

    private String id;
    private String terminalId;
    private Channel channel = null;
    private boolean isAuthenticated = false;

    /**
     * 消息流水号 word(16) 按发送顺序从 0 开始循环累加
     */
    private int currentFlowId = 0;

    /**
     * 客户端上次的连接时间，该值改变的情况: 1.terminal --> server 心跳包  2.terminal --> server 数据包
     */
    private Long lastCommunicateTimeStamp = 0L;

    public Session() {
    }

    public static String buildId(Channel channel) {
        return channel.id().asLongText();
    }

    public static Session buildSession(Channel channel) {
        return buildSession(channel, null);
    }

    public static Session buildSession(Channel channel, String terminalId) {
        Session session = new Session();
        session.setChannel(channel);
        session.setId(buildId(channel));
        session.setTerminalId(terminalId);
        session.setLastCommunicateTimeStamp(System.currentTimeMillis());
        return session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public long getLastCommunicateTimeStamp() {
        return lastCommunicateTimeStamp;
    }

    public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
        this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
    }

    public SocketAddress getRemoteAddr() {
        return this.channel.remoteAddress();
    }

    public synchronized int currentFlowId() {
        if (currentFlowId >= 0xffff) {
            currentFlowId = 0;
        }
        return currentFlowId++;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Session other = (Session) that;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Session [identity=" + id + ", terminalId=" + terminalId + ", channel=" + channel + "]";
    }

}

/*******************************************************************************
 * @(#)JT_0100.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import cn.hutool.core.util.HexUtil;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * <p>
 *     反控包
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:33
 */
public class JT_9100 implements IMessageBody {

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 消息ID
     */
    private int msgId;

    /**
     * body内容
     */
    private String msgCmd;


    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getMsgCmd() {
        return msgCmd;
    }

    public void setMsgCmd(String msgCmd) {
        this.msgCmd = msgCmd;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getDeviceNo());
        buff.put(getMsgId());
        buff.put(getMsgCmd());
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        // deviceNo
        byte[] deviceNoBytes = buff.gets(6);
        String deviceNoStr = HexUtil.encodeHexStr(deviceNoBytes);
        setDeviceNo(deviceNoStr);
        // msgId
        setMsgId((buff.getShort() & 0xffff));
        // msgCmd
        setMsgCmd(buff.getString(bytes.length-8));
    }

    @Override
    public String toString() {
        return String.format("设备编号：%1$s,消息ID：%2$s,body内容：%3$s",
                getDeviceNo(), getMsgId(), getMsgCmd());
    }

}

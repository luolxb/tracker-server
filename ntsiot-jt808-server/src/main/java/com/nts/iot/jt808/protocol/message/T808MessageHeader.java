/*******************************************************************************
 * @(#)T808MessageHeader.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.message;

import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import com.nts.iot.jt808.utils.Tools;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:16
 */
public class T808MessageHeader {

    public final int getHeaderSize() {
        // 有分包
        if (getIsPackage()) {
            // 两个分隔符，一个校验字节
            return 16 + 3;
        } else {
            // 两个分隔符，一个校验字节
            return 12 + 3;
        }
    }
    /**
     * 消息ID，2个字节，无符号16位
     */
    private int messageType;

    public final int getMessageType() {
        return messageType;
    }

    public final void setMessageType(int value) {
        messageType = value;
    }

    /**
     * 消息体长度
     */
    public final int getMessageSize() {
        return getMessageBodyProperty() & 0x03FF;
    }

    public final void setMessageSize(int value) {
        boolean res = getIsPackage();
        if (res) {
            setMessageBodyProperty((short) (0x2000 | value));
        } else {
            setMessageBodyProperty((short) (value));
        }
    }

    /**
     * 消息体属性
     */
    private short messageBodyProperty;

    public final short getMessageBodyProperty() {
        return messageBodyProperty;
    }

    public final void setMessageBodyProperty(short value) {
        messageBodyProperty = value;
    }

    /**
     * 终端手机号
     */
    private String simId;

    public final String getSimId() {

        return simId;
    }

    public final void setSimId(String value) {

        simId = value;
    }

    /**
     * 消息流水号
     */
    private short messageSerialNo;

    public final short getMessageSerialNo() {
        return messageSerialNo;
    }

    public final void setMessageSerialNo(short value) {
        messageSerialNo = value;
    }

    /**
     * 总包数
     */

    private short messageTotalPacketsCount;

    public final short getMessageTotalPacketsCount() {
        return messageTotalPacketsCount;
    }

    public final void setMessageTotalPacketsCount(short value) {
        messageTotalPacketsCount = value;
    }

    /**
     * 分包号
     */

    private short messagePacketNo;

    public final short getMessagePacketNo() {
        return messagePacketNo;
    }

    public final void setMessagePacketNo(short value) {
        messagePacketNo = value;
    }

    /**
     * 分包发送
     */
    public final boolean getIsPackage() {
        return (getMessageBodyProperty() & 0x2000) == 0x2000;
    }

    public final void setIsPackage(boolean value) {
        if (value) {
            messageBodyProperty |= 0x2000;
        } else {
            messageBodyProperty &= 0xDFFF;
        }
    }

    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put((short) getMessageType());
        buff.put(getMessageBodyProperty());
        while (simId.length() < 12) {
            simId = "0" + simId;
        }
        byte[] simIdBytes = Tools.HexString2Bytes(simId);
        buff.put(simIdBytes);
        buff.put(getMessageSerialNo());

        if (getIsPackage()) {
            buff.put(getMessageTotalPacketsCount());
            buff.put(getMessagePacketNo());
        }
        return buff.toByteArrayAndRelease();
    }

    public final void readFromBytes(byte[] headerBytes) {
        ByteBuffer buff = new ByteBuffer(headerBytes);
        setMessageType(buff.getShort() & 0xffff);
        setMessageBodyProperty(buff.getShort());
        byte[] simIdBytes = buff.gets(6);
        setSimId(String.format("%02x", simIdBytes[0])
                + String.format("%02x", simIdBytes[1])
                + String.format("%02x", simIdBytes[2])
                + String.format("%02x", simIdBytes[3])
                + String.format("%02x", simIdBytes[4])
                + String.format("%02x", simIdBytes[5]));
        setMessageSerialNo(buff.getShort());
        if (getIsPackage()) {
            setMessageTotalPacketsCount(buff.getShort());
            setMessagePacketNo(buff.getShort());
        }
    }

}

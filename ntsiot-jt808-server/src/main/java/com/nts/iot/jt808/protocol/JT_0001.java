/*******************************************************************************
 * @(#)JT_0001.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * <p>
 *     终端通用应答
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:30
 */
public class JT_0001 implements IMessageBody {

    /**
     * 应答消息流水号
     */
    private short responseMessageSerialNo;

    public final short getResponseMessageSerialNo() {
        return responseMessageSerialNo;
    }

    public final void setResponseMessageSerialNo(short value) {
        responseMessageSerialNo = value;
    }

    /**
     * 应答消息ID
     */
    private short responseMessageId;

    public final short getResponseMessageId() {
        return responseMessageId;
    }

    public final void setResponseMessageId(short value) {
        responseMessageId = value;
    }

    /**
     * 应答结果，0：成功/确认；1：失败；2：消息有误；3：不支持
     */
    private byte responseResult;

    public final byte getResponseResult() {
        return responseResult;
    }

    public final void setResponseResult(byte value) {
        responseResult = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getResponseMessageSerialNo());
        buff.put(getResponseMessageId());
        buff.put(getResponseResult());
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] messageBodyBytes) {
        ByteBuffer buff = new ByteBuffer(messageBodyBytes);
        try{
            setResponseMessageSerialNo(buff.getShort());
            setResponseMessageId(buff.getShort());
            setResponseResult(buff.get());
        }finally {
            buff.release();
        }
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("SN：%1$s,ID：%2$s,应答结果:%3$s", getResponseMessageSerialNo(), getResponseMessageId(),this.responseResult));
        return sBuilder.toString();
    }

}

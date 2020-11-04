package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 提问应答
 */
public class JT_0302 implements IMessageBody {

    /**
     * 对应的提问下发消息的流水号
     */
    private short responseMessageSerialNo;

    /**
     * 提问下发中附带的答案ID
     */
    private byte answerId;

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(BitConverter.getBytes(getResponseMessageSerialNo()));
        buff.put(getAnswerId());
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setResponseMessageSerialNo((short) BitConverter.toUInt16(bytes, 0));
        setAnswerId(bytes[2]);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("流水号：%1$s,答案ID：%2$s", getResponseMessageSerialNo(), getAnswerId()));
        return sBuilder.toString();
    }

    public final short getResponseMessageSerialNo() {
        return responseMessageSerialNo;
    }

    public final void setResponseMessageSerialNo(short value) {
        responseMessageSerialNo = value;
    }

    public final byte getAnswerId() {
        return answerId;
    }

    public final void setAnswerId(byte value) {
        answerId = value;
    }
}
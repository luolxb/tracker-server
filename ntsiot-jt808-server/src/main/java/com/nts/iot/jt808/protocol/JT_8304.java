package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 信息服务
 */
public class JT_8304 implements IMessageBody {

    /**
     * 信息类型
     */
    private byte messageType;

    public final byte getMessageType() {
        return messageType;
    }

    public final void setMessageType(byte value) {
        messageType = value;
    }

    /**
     * 信息长度
     */
    private short messageLength;

    public final short getMessageLength() {
        return messageLength;
    }

    public final void setMessageLength(short value) {
        messageLength = value;
    }

    /**
     * 信息内容
     */
    private String message;

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String value) {
        message = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        bytes.put(this.messageType);
        byte[] messageBytes = BitConverter.getBytes(getMessage());
        this.messageLength = (short) (messageBytes.length);
        bytes.put(messageLength);
        bytes.put(messageBytes);
        return bytes.array();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setMessageType(buff.get());
        setMessageLength(buff.getShort());
        setMessage(buff.getString());
    }

    @Override
    public String toString() {
        return String.format("信息类型：%1$s,信息长度：%2$s,信息内容：%3$s", getMessageType(), getMessageLength(), getMessage());
    }
}
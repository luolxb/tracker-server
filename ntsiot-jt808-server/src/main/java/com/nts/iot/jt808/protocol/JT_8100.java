package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 终端注册应答
 */
public class JT_8100 implements IMessageBody {

    /**
     * 对应的终端注册消息的流水号
     */
    private short registerResponseMessageSerialNo;

    public final short getRegisterResponseMessageSerialNo() {
        return registerResponseMessageSerialNo;
    }

    public final void setRegisterResponseMessageSerialNo(short value) {
        registerResponseMessageSerialNo = value;
    }

    /**
     * 注册结果,0：成功；1：车辆已被注册；2：数据库中无该车辆；3：终端已被注册；4：数据库中无该终端
     */
    private byte registerResponseResult;

    public final byte getRegisterResponseResult() {
        return registerResponseResult;
    }

    public final void setRegisterResponseResult(byte value) {
        registerResponseResult = value;
    }

    /**
     * 鉴权码,只有在成功后才有该字段
     */
    private String registerNo;

    public final String getRegisterNo() {
        return registerNo;
    }

    public final void setRegisterNo(String value) {
        registerNo = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("注册结果:").append(registerResponseResult).append(",鉴权码:").append(registerNo);
        return sb.toString();
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getRegisterResponseMessageSerialNo());
        buff.put(getRegisterResponseResult());
        if (getRegisterResponseResult() == 0) {
            buff.put(getRegisterNo());
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setRegisterResponseMessageSerialNo(buff.getShort());
        setRegisterResponseResult(buff.get());
        if (getRegisterResponseResult() == 0) {
            setRegisterNo(buff.getString());
        }
    }
}
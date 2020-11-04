package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

import java.util.List;

/**
 * 补传分包请求
 */
public class JT_8003 implements IMessageBody {

    /**
     * 原始消息包的第一个分包的流水号
     */
    private short responseMessageSerialNo;

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("原始消息的第一个分包流水号:" + responseMessageSerialNo).append(",重传包数:" + repassPacketsCount);

        if (this.repassPacketsNo != null && this.repassPacketsNo.size() > 0) {
            sBuilder.append(",重传包Id列表:");
            for (short packetNo : this.repassPacketsNo) {
                sBuilder.append(packetNo).append(",");
            }
        }
        return sBuilder.toString();
    }

    /**
     * 重传包总数
     */
    private byte repassPacketsCount;

    /**
     * 重传包ID列表
     */
    private List<Short> repassPacketsNo;

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        bytes.put(this.responseMessageSerialNo);
        bytes.put(getRepassPacketsCount());
        if (this.repassPacketsNo != null) {
            for (short repassPacketNo : getRepassPacketsNo()) {
                bytes.put((byte) (repassPacketNo >> 8));
                bytes.put((byte) repassPacketNo);
            }
        }
        return bytes.array();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        this.responseMessageSerialNo = (short) (bytes[0] << 8 + bytes[1]);
        setRepassPacketsCount(bytes[4]);
        setRepassPacketsNo(new java.util.ArrayList<>(getRepassPacketsCount()));
        int pos = 5;
        while (pos < bytes.length) {
            getRepassPacketsNo().add((short) ((bytes[pos] << 8) + bytes[1]));
            pos += 2;
        }
    }

    public final byte getRepassPacketsCount() {
        return repassPacketsCount;
    }

    public final void setRepassPacketsCount(byte value) {
        repassPacketsCount = value;
    }

    public final List<Short> getRepassPacketsNo() {
        return repassPacketsNo;
    }

    public final void setRepassPacketsNo(java.util.ArrayList<Short> value) {
        repassPacketsNo = value;
    }

    public short getResponseMessageSerialNo() {
        return responseMessageSerialNo;
    }

    public void setResponseMessageSerialNo(short responseMessageSerialNo) {
        this.responseMessageSerialNo = responseMessageSerialNo;
    }
}
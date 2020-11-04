package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 录音开始命令
 */
public class JT_8804 implements IMessageBody {

    /**
     * 录音命令
     */
    private byte recordCommad;

    public final byte getRecordCommad() {
        return recordCommad;
    }

    public final void setRecordCommad(byte value) {
        recordCommad = value;
    }

    /**
     * 录音时间
     */

    private short recordTimePeriod;

    public final short getRecordTimePeriod() {
        return recordTimePeriod;
    }

    public final void setRecordTimePeriod(short value) {
        recordTimePeriod = value;
    }

    /**
     * 保存标志
     */
    private byte storeFlag;

    public final byte getStoreFlag() {
        return storeFlag;
    }

    public final void setStoreFlag(byte value) {
        storeFlag = value;
    }

    /**
     * 音频采样率
     */
    private byte privateFrequency;

    public final byte getFrequency() {
        return privateFrequency;
    }

    public final void setFrequency(byte value) {
        privateFrequency = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[5];
        bytes[0] = getRecordCommad();
        bytes[1] = (byte) (getRecordTimePeriod() >> 8);
        bytes[2] = (byte) getRecordTimePeriod();
        bytes[3] = getStoreFlag();
        bytes[4] = getFrequency();
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setRecordCommad(bytes[0]);
        setRecordTimePeriod((short) ((bytes[1] << 8) + bytes[2]));
        setStoreFlag(bytes[3]);
        setFrequency(bytes[4]);
    }
}
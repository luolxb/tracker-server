package com.nts.iot.jt808.protocol.jt2012;


import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

public class Recorder_PulseFactor implements IRecorderDataBlock_2012 {

    @Override
    public final byte getCommandWord() {
        return 0x04;
    }

    @Override
    public final short getDataLength() {
        return 8;
    }

    private java.util.Date privateRealTime = new java.util.Date(0);

    public final java.util.Date getRealTime() {
        return privateRealTime;
    }

    public final void setRealTime(java.util.Date value) {
        privateRealTime = value;
    }

    private short privatePulseFactor;

    public final short getPulseFactor() {
        return privatePulseFactor;
    }

    public final void setPulseFactor(short value) {
        privatePulseFactor = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (getPulseFactor() >> 8);
        bytes[1] = (byte) (getPulseFactor());
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setRealTime(new java.util.Date(java.util.Date.parse("20" + String.format("%02X", bytes[0]) + "-" + String.format("%02X", bytes[1]) + "-" + String.format("%02X", bytes[2]) + " " + String.format("%02X", bytes[3]) + ":" + String.format("%02X", bytes[4]) + ":" + String.format("%02X", bytes[5]))));
        this.privatePulseFactor = (short) BitConverter.toUInt16(bytes, 6);
    }
}

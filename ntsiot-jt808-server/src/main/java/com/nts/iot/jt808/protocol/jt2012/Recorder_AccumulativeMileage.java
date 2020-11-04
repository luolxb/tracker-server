package com.nts.iot.jt808.protocol.jt2012;


import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

public class Recorder_AccumulativeMileage implements IRecorderDataBlock_2012 {

    @Override
    public final byte getCommandWord() {
        return 0x03;
    }

    @Override
    public final short getDataLength() {
        return 20;
    }

    private java.util.Date privateRealTime = new java.util.Date(0);

    public final java.util.Date getRealTime() {
        return privateRealTime;
    }

    public final void setRealTime(java.util.Date value) {
        privateRealTime = value;
    }

    private java.util.Date privateSetupDateTime = new java.util.Date(0);

    public final java.util.Date getSetupDateTime() {
        return privateSetupDateTime;
    }

    public final void setSetupDateTime(java.util.Date value) {
        privateSetupDateTime = value;
    }

    private long privateBeginMileage;

    public final long getBeginMileage() {
        return privateBeginMileage;
    }

    public final void setBeginMileage(long value) {
        privateBeginMileage = value;
    }


    private long privateEndMileage;

    public final long getEndMileage() {
        return privateEndMileage;
    }

    public final void setEndMileage(long value) {
        privateEndMileage = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[4];
        String Mileage = (new Long(getBeginMileage())).toString();
        String s1 = Mileage.substring(0, 2);

        int a, b;
        a = Integer.parseInt(s1.substring(0, 1));
        b = Integer.parseInt(s1.substring(1, 2));
        bytes[0] = (byte) ( (a << 4) + b);

        String s2 = Mileage.substring(2, 4);
        a = Integer.parseInt(s2.substring(0, 1));
        b = Integer.parseInt(s2.substring(1, 2));
        bytes[1] = (byte) ((a << 4) + b);

        String s3 = Mileage.substring(4, 6);
        a = Integer.parseInt(s3.substring(0, 1));
        b = Integer.parseInt(s3.substring(1, 2));
        bytes[2] = (byte) ((a << 4) + b);

        String s4 = Mileage.substring(6, 8);
        a = Integer.parseInt(s4.substring(0, 1));
        b = Integer.parseInt(s4.substring(1, 2));
        bytes[3] = (byte) ((a << 4) + b);
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setRealTime(new java.util.Date(java.util.Date.parse("20" + String.format("%02X", bytes[0]) + "-" + String.format("%02X", bytes[1]) + "-" + String.format("%02X", bytes[2]) + " " + String.format("%02X", bytes[3]) + ":" + String.format("%02X", bytes[4]) + ":" + String.format("%02X", bytes[5]))));

        setSetupDateTime(new java.util.Date(java.util.Date.parse("20" + String.format("%02X", bytes[6]) + "-" + String.format("%02X", bytes[7]) + "-" + String.format("%02X", bytes[8]) + " " + String.format("%02X", bytes[9]) + ":" + String.format("%02X", bytes[10]) + ":" + String.format("%02X", bytes[11]))));

        byte[] beginMileage = new byte[4];
        System.arraycopy(bytes, 12, beginMileage, 0, 4);

        setBeginMileage(getMileage(beginMileage));

        byte[] endMileage = new byte[4];
        System.arraycopy(bytes, 16, endMileage, 0, 4);

        setEndMileage(getMileage(endMileage));
    }

    private long getMileage(byte[] mileageBytes) {
        long mile = BitConverter.toUInt32(mileageBytes, 0);
        return mile;
    }
}


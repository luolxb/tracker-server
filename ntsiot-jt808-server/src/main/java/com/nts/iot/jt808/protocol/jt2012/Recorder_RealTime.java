package com.nts.iot.jt808.protocol.jt2012;

import com.nts.iot.jt808.utils.DateUtil;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

import java.util.Calendar;

public class Recorder_RealTime implements IRecorderDataBlock_2012 {

    @Override
    public final byte getCommandWord() {
        return 0x02;
    }

    @Override
    public final short getDataLength() {
        return 6;
    }

    private java.util.Date privateRealTimeClock = new java.util.Date(0);

    public final java.util.Date getRealTimeClock() {
        return privateRealTimeClock;
    }

    public final void setRealTimeClock(java.util.Date value) {
        privateRealTimeClock = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[6];
        Calendar calendar = Calendar.getInstance();
        String strDate = DateUtil.toStringByFormat(getRealTimeClock(), "yyMMddHHmmss");
        bytes[0] = Byte.parseByte(strDate.substring(0, 2), 16);
        bytes[1] = Byte.parseByte(strDate.substring(2, 4), 16);
        bytes[2] = Byte.parseByte(strDate.substring(4, 6), 16);
        bytes[3] = Byte.parseByte(strDate.substring(6, 8), 16);
        bytes[4] = Byte.parseByte(strDate.substring(8, 10), 16);
        bytes[5] = Byte.parseByte(strDate.substring(10, 12), 16);
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setRealTimeClock(new java.util.Date(java.util.Date.parse("20" + String.format("%02X", bytes[0]) + "-" + String.format("%02X", bytes[1]) + "-" + String.format("%02X", bytes[2]) + " " + String.format("%02X", bytes[3]) + ":" + String.format("%02X", bytes[4]) + ":" + String.format("%02X", bytes[5]))));
    }


}




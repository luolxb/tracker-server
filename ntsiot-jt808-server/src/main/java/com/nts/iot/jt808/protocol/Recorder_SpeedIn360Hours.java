/*******************************************************************************
 * @(#)Recorder_SpeedIn360Hours.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IRecorderDataBlock;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:44
 */
public class Recorder_SpeedIn360Hours implements IRecorderDataBlock {

    @Override
    public final byte getCommandWord() {
        return 0x05;
    }

    @Override
    public final short getDataLength() {
        return (short) (65 * getSpeedsIn360Hours().size());
    }

    /**
     * 360小时速度数据
     */
    private java.util.TreeMap<java.util.Date, byte[]> speedsIn360Hours;

    public final java.util.TreeMap<java.util.Date, byte[]> getSpeedsIn360Hours() {
        return speedsIn360Hours;
    }

    public final void setSpeedsIn360Hours(java.util.TreeMap<java.util.Date, byte[]> value) {
        speedsIn360Hours = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        for (java.util.Map.Entry<java.util.Date, byte[]> keyValue :speedsIn360Hours.entrySet()) {
            buff.put(Byte.parseByte((keyValue.getKey().getYear() + "").substring(2, 4), 16));
            buff.put(Byte.parseByte((keyValue.getKey().getMonth() + ""), 16));
            buff.put(Byte.parseByte((keyValue.getKey().getDay() + ""), 16));
            buff.put(Byte.parseByte((keyValue.getKey().getHours() + ""), 16));
            buff.put(Byte.parseByte((keyValue.getKey().getMinutes() + ""), 16));
            buff.put(keyValue.getValue());
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setSpeedsIn360Hours(new java.util.TreeMap<>());
        ByteBuffer buff = new ByteBuffer(bytes);
        while (buff.hasRemain()) {
            byte[] timeBytes = buff.gets(5);
            String strDate = "20"
                    + String.format("%02X", timeBytes[0]) + "-"
                    + String.format("%02X", timeBytes[1]) + "-"
                    + String.format("%02X", timeBytes[2]) + " "
                    + String.format("%02X", timeBytes[3]) + ":"
                    + String.format("%02X", timeBytes[4]) + ":00";
            getSpeedsIn360Hours().put(new Date(), buff.gets(60));
        }

    }
}

/*******************************************************************************
 * @(#)Recorder_SpeedIn2Days.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IRecorderDataBlock;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 20:06
 */
public class Recorder_SpeedIn2Days implements IRecorderDataBlock {

    @Override
    public final byte getCommandWord() {
        return 0x09;
    }

    @Override
    public final short getDataLength() {
        return (short) (getSpeedsIn2Days().size() * 65);
    }

    /**
     * 2天内的速度数据
     */
    private java.util.TreeMap<java.util.Date, byte[]> speedsIn2Days;

    public final java.util.TreeMap<java.util.Date, byte[]> getSpeedsIn2Days() {
        return speedsIn2Days;
    }

    public final void setSpeedsIn2Days(java.util.TreeMap<java.util.Date, byte[]> value) {
        speedsIn2Days = value;
    }

    @Override
    public final String toString() {
        java.util.Collection<java.util.Date> keys = getSpeedsIn2Days().keySet();
        StringBuilder sb = new StringBuilder();
        for (java.util.Date key : keys) {
            byte[] values = getSpeedsIn2Days().get(key);
            for (byte b : values) {
                sb.append(BitConverter.format(key)).append(",");
                sb.append("" + b).append(",");
                sb.append(';');
            }
        }
        return sb.toString();
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        for (java.util.Map.Entry<java.util.Date, byte[]> keyValue : getSpeedsIn2Days().entrySet()) {
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
        setSpeedsIn2Days(new java.util.TreeMap<>());
        try {
            try {
                ByteBuffer buff = new ByteBuffer(bytes);
                try {
                    while (buff.hasRemain()) {
                        byte[] timeBytes = buff.gets(5);
                        getSpeedsIn2Days().put(
                                new java.util.Date(java.util.Date.parse("20"
                                        + String.format("%02X", timeBytes[0])
                                        + "-"
                                        + String.format("%02X", timeBytes[1])
                                        + "-"
                                        + String.format("%02X", timeBytes[2])
                                        + " "
                                        + String.format("%02X", timeBytes[3])
                                        + ":"
                                        + String.format("%02X", timeBytes[4])
                                        + ":00")), buff.gets(60));
                    }
                } finally {
                }
            } finally {
            }
        } catch (RuntimeException ex) {
        }
    }
}

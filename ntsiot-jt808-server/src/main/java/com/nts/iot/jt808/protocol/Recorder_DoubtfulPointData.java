/*******************************************************************************
 * @(#)Recorder_DoubtfulPointData.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 20:00
 */
public class Recorder_DoubtfulPointData implements IRecorderDataBlock {

    @Override
    public final byte getCommandWord() {
        return 0x07;
    }

    @Override
    public final short getDataLength() {
        return (short) (getDoubtfulPointData().size() * 206);
    }

    private java.util.TreeMap<java.util.Date, java.util.ArrayList<Byte[]>> doubtfulPointData;

    public final java.util.TreeMap<java.util.Date, java.util.ArrayList<Byte[]>> getDoubtfulPointData() {
        return doubtfulPointData;
    }

    public final void setDoubtfulPointData(
            java.util.TreeMap<java.util.Date, java.util.ArrayList<Byte[]>> value) {
        doubtfulPointData = value;
    }

    @Override
    public final String toString() {
        java.util.Collection<java.util.Date> keys = getDoubtfulPointData()
                .keySet();
        StringBuilder sb = new StringBuilder();
        for (java.util.Date key : keys) {
            java.util.Collection<Byte[]> values = getDoubtfulPointData().get(
                    key);

            sb.append(BitConverter.format(key)).append(",");
            for (Byte[] b : values) {
                sb.append("" + b[0]).append(',').append("" + b[1]).append(',');
            }
            sb.append(';');
        }
        return sb.toString();
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        for (java.util.Map.Entry<java.util.Date, java.util.ArrayList<Byte[]>> onceDoubtfulPointData : getDoubtfulPointData().entrySet()) {
            byte[] date1 = BitConverter.getBytes(onceDoubtfulPointData.getKey());
            bytes.put(date1);
            for (Byte[] data : onceDoubtfulPointData.getValue()) {
                for (Byte b : data) {
                    bytes.put(b);
                }
            }
        }
        return bytes.array();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setDoubtfulPointData(new java.util.TreeMap<>());
        int pos = 0;
        while (pos < bytes.length) {
            java.util.Date stopTime;
            stopTime = BitConverter.getDate(bytes, 0);
            pos += 6;
            java.util.ArrayList<Byte[]> speedAndSwitches = new java.util.ArrayList<>(100);
            for (int i = 0; i < 100; i++) {
                Byte[] speedAndSwitch = new Byte[2];
                speedAndSwitch[0] = bytes[pos + i * 2];
                speedAndSwitch[1] = bytes[pos + 1 + i * 2];
                speedAndSwitches.add(speedAndSwitch);
            }
            pos += 200;
            getDoubtfulPointData().put(stopTime, speedAndSwitches);
        }
    }
}

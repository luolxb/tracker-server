/*******************************************************************************
 * @(#)Recorder_MileageIn360Hours.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.utils.DateUtil;
import com.nts.iot.jt808.protocol.base.IRecorderDataBlock;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:40
 */
public class Recorder_MileageIn360Hours implements IRecorderDataBlock {

    @Override
    public final byte getCommandWord() {
        return 0x03;
    }

    @Override
    public final short getDataLength() {
        return 8;
    }

    /**
     * 累计行驶里程值（表示单位0.1km）范围00～999999
     */
    private int mileage;

    public final int getMileage() {
        return mileage;
    }

    public final void setMileage(int value) {
        mileage = value;
    }

    /**
     * 读出时刻 YY-MM-DD-hh-mm
     */
    private java.util.Date readingTime = new java.util.Date(0);

    public final java.util.Date getReadingTime() {
        return readingTime;
    }

    public final void setReadingTime(java.util.Date value) {
        readingTime = value;
    }

    @Override
    public final String toString() {
        return getMileage() + "," + getReadingTime();
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (getMileage() >> 16);
        bytes[1] = (byte) (getMileage() >> 8);
        bytes[2] = (byte) (getMileage());
        byte[] date1 = BitConverter.getBytes(getReadingTime());
        System.arraycopy(date1, 0, bytes, 3, 6);
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setMileage(((bytes[0] << 16) + (bytes[1] << 8) + bytes[2]));
        String strDate = "20" + String.format("%02X", bytes[3]) + "-"
                + String.format("%02X", bytes[4]) + "-"
                + String.format("%02X", bytes[5]) + " "
                + String.format("%02X", bytes[6]) + ":"
                + String.format("%02X", bytes[7]) + ":"
                + String.format("%02X", bytes[8]);

        Date d = DateUtil.stringToDatetime(strDate, "yyyy-MM-dd HH:mm:ss");

        setReadingTime(d);
    }
}

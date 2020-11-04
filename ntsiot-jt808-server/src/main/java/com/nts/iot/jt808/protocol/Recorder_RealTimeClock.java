/*******************************************************************************
 * @(#)Recorder_RealTimeClock.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 19:38
 */
public class Recorder_RealTimeClock implements IRecorderDataBlock {

    @Override
    public final byte getCommandWord() {
        return 0x02;
    }

    @Override
    public final short getDataLength() {
        return 6;
    }

    private java.util.Date realTimeClock = new java.util.Date(0);

    public final java.util.Date getRealTimeClock() {
        return realTimeClock;
    }

    public final void setRealTimeClock(java.util.Date value) {
        realTimeClock = value;
    }

    @Override
    public final String toString() {
        return DateUtil.toStringByFormat(getRealTimeClock(),"yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public final byte[] writeToBytes() {
        return BitConverter.getBytes(getRealTimeClock());
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        String strDate = "20" + String.format("%02X", bytes[0]) + "-" + String.format("%02X", bytes[1])
                + "-" + String.format("%02X", bytes[2]) + " " + String.format("%02X", bytes[3])
                + ":" + String.format("%02X", bytes[4]) + ":" + String.format("%02X", bytes[5]);
        Date d = DateUtil.stringToDatetime(strDate, "yyyy-MM-dd HH:mm:ss");
        setRealTimeClock(d);
    }

}

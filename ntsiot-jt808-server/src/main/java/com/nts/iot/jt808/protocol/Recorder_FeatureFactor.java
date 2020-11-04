/*******************************************************************************
 * @(#)Recorder_FeatureFactor.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IRecorderDataBlock;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:42
 */
public class Recorder_FeatureFactor implements IRecorderDataBlock {

    @Override
    public final byte getCommandWord() {
        return 0x04;
    }

    @Override
    public final short getDataLength() {
        return 3;
    }

    /**
     * 设定的车辆特征系数 (高中低字节)
     */
    private int privateFeatureFactor;

    public final int getFeatureFactor() {
        return privateFeatureFactor;
    }

    public final void setFeatureFactor(int value) {
        privateFeatureFactor = value;
    }

    @Override
    public final String toString() {
        return "" + getFeatureFactor();
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[3];
        bytes[0] = (byte) (getFeatureFactor() >> 16);
        bytes[1] = (byte) (getFeatureFactor() >> 8);
        bytes[2] = (byte) (getFeatureFactor());
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setFeatureFactor(((bytes[0] << 16) + (bytes[1] << 8) + bytes[2]));
    }
}

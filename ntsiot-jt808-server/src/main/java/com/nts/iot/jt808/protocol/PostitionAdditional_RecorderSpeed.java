/*******************************************************************************
 * @(#)PostitionAdditional_RecorderSpeed.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;

/**
 * <p>
 *     记录仪速度
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:15
 */
public class PostitionAdditional_RecorderSpeed implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x03;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x02;
    }

    /**
     * 行驶记录功能获取的速度，WORD，1/10km/h
     */
    private short recorderSpeed;

    public final short getRecorderSpeed() {
        return recorderSpeed;
    }

    public final void setRecorderSpeed(short value) {
        recorderSpeed = value;
    }

    @Override
    public final byte[] writeToBytes() {
        return BitConverter.getBytes(getRecorderSpeed());
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setRecorderSpeed((short) BitConverter.toUInt16(bytes, 0));
    }
}

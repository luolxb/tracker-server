/*******************************************************************************
 * @(#)PostitionAdditional_Voltage.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;

/**
 * <p>
 *     电压
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:14
 */
public class PostitionAdditional_Voltage implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0xE1;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x02;
    }

    /**
     * 电压,单位0.01V
     */
    private short privateVoltage;

    public final short getVoltage() {
        return privateVoltage;
    }

    public final void setVoltage(short value) {
        privateVoltage = value;
    }

    @Override
    public final byte[] writeToBytes() {
        return BitConverter.getBytes(getVoltage());
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setVoltage((short) BitConverter.toUInt16(bytes, 0));
    }
}

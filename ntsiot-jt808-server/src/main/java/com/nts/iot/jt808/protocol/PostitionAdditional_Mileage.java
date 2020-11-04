/*******************************************************************************
 * @(#)PostitionAdditional_Mileage.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:47
 */
public class PostitionAdditional_Mileage implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x01;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x04;
    }

    /**
     * 里程，DWORD，1/10km，对应车上里程表读数
     */
    private int mileage;

    public final int getMileage() {
        return mileage;
    }

    public final void setMileage(int value) {
        mileage = value;
    }

    @Override
    public final byte[] writeToBytes() {
        return BitConverter.getBytes(getMileage());
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        this.mileage = BitConverter.toUInt32(bytes, 0);
    }

}

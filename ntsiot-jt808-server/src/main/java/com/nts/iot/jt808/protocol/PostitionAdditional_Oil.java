/*******************************************************************************
 * @(#)PostitionAdditional_Oil.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;

/**
 * <p>
 *     油量
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:03
 */
public class PostitionAdditional_Oil implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x02;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x02;
    }

    /**
     * 油量，WORD，1/10L，对应车上油量表读数
     */
    private short oil;

    public final short getOil() {
        return oil;
    }

    public final void setOil(short value) {
        oil = value;
    }

    @Override
    public final byte[] writeToBytes() {
        return BitConverter.getBytes(getOil());
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setOil((short) BitConverter.toUInt16(bytes, 0));
    }
}
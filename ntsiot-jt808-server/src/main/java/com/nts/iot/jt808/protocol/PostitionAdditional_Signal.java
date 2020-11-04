/*******************************************************************************
 * @(#)PostitionAdditional_Signal.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;

/**
 * <p>
 *     信号量附加协议
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:01
 */
public class PostitionAdditional_Signal implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x48;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x1;
    }

    /**
     * 信息量
     */
    private int signal;

    @Override
    public final byte[] writeToBytes() {
        return BitConverter.getBytes(signal);
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        signal = BitConverter.toUInt32(bytes[0]);
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }
}

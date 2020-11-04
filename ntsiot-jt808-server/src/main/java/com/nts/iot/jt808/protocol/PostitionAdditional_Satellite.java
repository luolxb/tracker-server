/*******************************************************************************
 * @(#)PostitionAdditional_Satellite.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;
import com.nts.iot.jt808.utils.BitConverter;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-02 11:19
 */
public class PostitionAdditional_Satellite implements IPositionAdditionalItem {

    /**
     * 卫星数量
     */
    private int satellite;

    @Override
    public int getAdditionalId() {
        return 0x49;
    }

    @Override
    public byte getAdditionalLength() {
        return 0x1;
    }

    @Override
    public byte[] writeToBytes() {
        return BitConverter.getBytes(satellite);
    }

    @Override
    public void readFromBytes(byte[] bytes) {
        this.satellite = BitConverter.toUInt32(bytes[0]);
    }

    public int getSatellite() {
        return satellite;
    }

    public void setSatellite(int satellite) {
        this.satellite = satellite;
    }
}

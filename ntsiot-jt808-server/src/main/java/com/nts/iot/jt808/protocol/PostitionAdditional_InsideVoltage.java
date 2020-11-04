/*******************************************************************************
 * @(#)PostitionAdditional_InsideVoltage.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 内部电压
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-02 13:32
 */
@Slf4j
public class PostitionAdditional_InsideVoltage implements IPositionAdditionalItem {

    /**
     * 内部电量
     */
    private Integer cellPower;

    /**
     * 内部电压
     */
    private Integer cellVoltage;

    /**
     * 充电电压
     */
    private Integer chargeVoltage;

    @Override
    public int getAdditionalId() {
        return 227;
    }

    @Override
    public byte getAdditionalLength() {
        return 6;
    }

    @Override
    public byte[] writeToBytes() {
        return new byte[0];
    }

    @Override
    public void readFromBytes(byte[] bytes) {
//        cellPower = BitConverter.toUInt16(Arrays.copyOfRange(bytes, 0, 2), 0);
//        cellVoltage = BitConverter.toUInt16(Arrays.copyOfRange(bytes, 2, 4), 0);
//        chargeVoltage = BitConverter.toUInt16(Arrays.copyOfRange(bytes, 4, 6), 0);
        cellPower = BitConverter.toUInt32(bytes[0]);
        cellVoltage = BitConverter.toUInt32(bytes[1]);
        chargeVoltage = 0;
        log.info("======>内部电量 "+cellPower+"  内部电量:"+cellVoltage);

    }

    public Integer getCellPower() {
        return cellPower;
    }

    public void setCellPower(Integer cellPower) {
        this.cellPower = cellPower;
    }

    public Integer getCellVoltage() {
        return cellVoltage;
    }

    public void setCellVoltage(Integer cellVoltage) {
        this.cellVoltage = cellVoltage;
    }

    public Integer getChargeVoltage() {
        return chargeVoltage;
    }

    public void setChargeVoltage(Integer chargeVoltage) {
        this.chargeVoltage = chargeVoltage;
    }
}

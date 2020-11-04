/*******************************************************************************
 * @(#)PostitionAdditional_OutsideVoltage.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-02 13:33
 */
@Slf4j
public class PostitionAdditional_OutsideVoltage implements IPositionAdditionalItem {

    /**
     * 外部电量
     */
    private Integer outCellPower;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 外部电压
     */
    private Integer outCellVoltage;

    @Override
    public int getAdditionalId() {
        return 229;
    }

    @Override
    public byte getAdditionalLength() {
        return 4;
    }

    @Override
    public byte[] writeToBytes() {
        return new byte[0];
    }

    @Override
    public void readFromBytes(byte[] bytes) {
        outCellPower = BitConverter.toUInt32(bytes[0]);
//        status = BitConverter.toUInt32(bytes[1]);
        // TODO 状态已经不在使用
        status = 0;
        outCellVoltage = BitConverter.toUInt16(Arrays.copyOfRange(bytes, 1, 3), 0);
        log.info("======>外部电量 "+outCellPower+"  外部电量:"+outCellVoltage);
    }

    public Integer getOutCellPower() {
        return outCellPower;
    }

    public void setOutCellPower(Integer outCellPower) {
        this.outCellPower = outCellPower;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOutCellVoltage() {
        return outCellVoltage;
    }

    public void setOutCellVoltage(Integer outCellVoltage) {
        this.outCellVoltage = outCellVoltage;
    }
}

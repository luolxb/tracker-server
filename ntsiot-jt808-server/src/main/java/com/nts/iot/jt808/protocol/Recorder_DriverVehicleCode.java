/*******************************************************************************
 * @(#)Recorder_DriverVehicleCode.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IRecorderDataBlock;

/**
 * <p>
 * 设定的驾驶员代码及其对应的机动车驾驶证号码
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:36
 */
public class Recorder_DriverVehicleCode implements IRecorderDataBlock {

    @Override
    public final byte getCommandWord() {
        return 0x01;
    }

    @Override
    public final short getDataLength() {
        return 21;
    }

    /**
     * 驾驶员代码（高）驾驶员代码（中）驾驶员代码（低）
     */
    private String driverCode;

    public final String getDriverCode() {
        return driverCode;
    }

    public final void setDriverCode(String value) {
        driverCode = value;
    }

    /**
     * 机动车驾驶证号,ASCII码
     */
    private String driverLicenseNo;

    public final String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public final void setDriverLicenseNo(String value) {
        driverLicenseNo = value;
    }

    @Override
    public final String toString() {
        return getDriverCode() + "," + getDriverLicenseNo();
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[21];
        int _driverCode = Integer.parseInt(getDriverCode());
        bytes[0] = (byte) (_driverCode >> 16);
        bytes[1] = (byte) (_driverCode >> 8);
        bytes[2] = (byte) (_driverCode);
        byte[] dirverLicenseNoBytes = BitConverter.getBytes(getDriverLicenseNo());
        if (dirverLicenseNoBytes.length >= 17) {
            System.arraycopy(dirverLicenseNoBytes, 0, bytes, 3, 17);
        } else {
            System.arraycopy(dirverLicenseNoBytes, 0, bytes, 3, dirverLicenseNoBytes.length);
            int pos = 3 + dirverLicenseNoBytes.length;
            while (pos < 20) {
                bytes[pos] = 0x00;
                pos++;
            }
        }
        bytes[20] = 0x00;
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setDriverCode("" + ((bytes[0] << 16) + (bytes[1] << 8) + bytes[2]));
        setDriverLicenseNo(BitConverter.getString(bytes, 3, 18));
    }
}
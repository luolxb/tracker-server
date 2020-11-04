/*******************************************************************************
 * @(#)JT_0100.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * <p>
 *     端注册
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:33
 */
public class JT_0100_old implements IMessageBody {

    /**
     * 省域ID
     */
    private short provinceId;

    public final short getProvinceId() {
        return provinceId;
    }

    public final void setProvinceId(short value) {
        provinceId = value;
    }

    /**
     * 市、县域ID
     */
    private short cityId;

    public final short getCityId() {
        return cityId;
    }

    public final void setCityId(short value) {
        cityId = value;
    }

    /**
     * 制造商ID
     */
    private String manufactureId;

    public final String getManufactureId() {
        return manufactureId;
    }

    public final void setManufactureId(String value) {
        manufactureId = value;
    }

    /**
     * 终端型号
     */
    private String terminalModelNo;

    public final String getTerminalModelNo() {
        return terminalModelNo;
    }

    public final void setTerminalModelNo(String value) {
        terminalModelNo = value;
    }

    /**
     * 终端ID
     */
    private String terminalId;

    public final String getTerminalId() {
        return terminalId;
    }

    public final void setTerminalId(String value) {
        terminalId = value;
    }

    /**
     * 车牌颜色
     */
    private byte plateColor = 0;

    public final byte getPlateColor() {
        return plateColor;
    }

    public final void setPlateColor(byte value) {
        plateColor = value;
    }

    /**
     * 车牌号码
     */
    private String plateNo;

    public final String getPlateNo() {
        return plateNo;
    }

    public final void setPlateNo(String value) {
        plateNo = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getProvinceId());
        buff.put(getCityId());
        buff.put(getManufactureId(), 5);
        buff.put(getTerminalModelNo(), 20);
        buff.put(Byte.parseByte(getTerminalId().substring(0, 2), 16));
        buff.put(Byte.parseByte(getTerminalId().substring(2, 4), 16));
        buff.put(Byte.parseByte(getTerminalId().substring(4, 6), 16));
        buff.put(Byte.parseByte(getTerminalId().substring(6, 8), 16));
        buff.put(Byte.parseByte(getTerminalId().substring(8, 10), 16));
        buff.put(Byte.parseByte(getTerminalId().substring(10, 12), 16));
        buff.put((byte) 0xFF);
        buff.put(getPlateColor());
        buff.put(getPlateNo());
        buff.put((byte) 0x00);
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setProvinceId(buff.getShort());
        setCityId(buff.getShort());
        this.manufactureId = (buff.getString(5));
        int dataLen = 37;
        try {
            if (bytes.length < 36) {
                setTerminalModelNo(buff.getString(8));
                dataLen = 25;
            } else {
                this.terminalModelNo = (buff.getString(20));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        this.terminalId = (buff.getString(7));
        this.plateColor = (buff.get());
        this.plateNo = buff.getString(bytes.length - dataLen);

    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("省：%1$s,市：%2$s,制造商：%3$s，型号：%4$s，终端：%5$s，车牌颜色：%6$s，车牌号:%7$s",
                getProvinceId(), getCityId(), getManufactureId(),
                getTerminalModelNo(), getTerminalId(), getPlateColor(),
                getPlateNo()));
        return sBuilder.toString();
    }

}

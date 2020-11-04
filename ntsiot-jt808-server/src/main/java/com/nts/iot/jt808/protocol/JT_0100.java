/*******************************************************************************
 * @(#)JT_0100.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import cn.hutool.core.util.HexUtil;
import com.nts.iot.jt808.utils.BitConverter;
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
public class JT_0100 implements IMessageBody {

    /**
     * 省域ID
     */
    private short provinceId;

    /**
     * 市、县域ID
     */
    private short cityId;


    /**
     * 制造商ID
     */
    private String manufactureId;


    /**
     * 终端型号
     */
    private String terminalModelNo;


    /**
     * 终端ID
     */
    private String terminalId;


    /**
     * 车牌颜色
     * （用作型号标识
     * 0x01表示纯GPRS设备
     * 0x02表示GPRS + BLE 设备
     * ）
     */
    private byte plateColor = 0;

    /**
     * 车辆标识
     * 纯GPRS设备：表示SIM卡上的iccid号 10 字节 BCD码
     * GPRS+BLE设备: 表示蓝牙的物理地址，直接转成字符串的ASCⅡ
     *
     */
    private String plateNo;


    public final short getProvinceId() {
        return provinceId;
    }

    public final void setProvinceId(short value) {
        provinceId = value;
    }



    public final short getCityId() {
        return cityId;
    }

    public final void setCityId(short value) {
        cityId = value;
    }



    public final String getManufactureId() {
        return manufactureId;
    }

    public final void setManufactureId(String value) {
        manufactureId = value;
    }



    public final String getTerminalModelNo() {
        return terminalModelNo;
    }

    public final void setTerminalModelNo(String value) {
        terminalModelNo = value;
    }



    public final String getTerminalId() {
        return terminalId;
    }

    public final void setTerminalId(String value) {
        terminalId = value;
    }



    public final byte getPlateColor() {
        return plateColor;
    }

    public final void setPlateColor(byte value) {
        plateColor = value;
    }



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
        // 省域ID
        setProvinceId(buff.getShort());
        // 市县域ID
        setCityId(buff.getShort());
        // 制造商ID
        byte[] mfId = buff.gets(5);
        setManufactureId(BitConverter.getString(mfId));
        // 终端型号
        setTerminalModelNo(HexUtil.encodeHexStr(buff.gets(20)));
        // 终端ID
        setTerminalId(HexUtil.encodeHexStr(buff.gets(6)));
        buff.gets(1);
        // 车牌颜色(借用此字段表示型号 0x01 表示 gprs 、0x02 表示 gprs + ble)
        byte color =buff.get();
        setPlateColor(color);
        // 车辆标识
        // 纯GPRS设备：表示SIM卡上的iccid号 10 字节 BCD码
        if (color == 0x01){
            String no = buff.getBcdString(10);
            System.out.println(no);
            setPlateNo(no);
            // GPRS+BLE设备: 表示蓝牙的物理地址，直接转成字符串的ASCⅡ
        } else {
            //byte[] noBytes = buff.gets(10);
            //String hex = HexUtil.encodeHexStr(noBytes);
            //System.out.println(hex);
            //setPlateNo(hex);
        }
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

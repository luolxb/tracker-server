/*******************************************************************************
 * @(#)JT_0001.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import cn.hutool.core.util.HexUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *     查询终端属性应答
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:30
 */
@Data
@Slf4j
public class JT_0107 implements IMessageBody {

    /**
     * 终端类型 WORD
     */
    private int terminalType;

    /**
     * 制造商ID BYTE[5]
     */
    private String manufactureId;

    /**
     * 终端型号 BYTE[20]
     */
    private String terminalModelNo;

    /**
     * 终端ID BYTE[7]
     */
    private String terminalId;

    //充电类型 00 为不能充电 01 可充电
    private String chargeType;

    /**
     * sim卡iccid号 BCD[10]
     */
    private String iccid;

    /**
     * 终端硬件版本号长度 n
     */
    @JSONField(serialize=false)
    private int deviceVersionLength;

    /**
     * 终端硬件版本号
     */
    private String deviceVersion;


    /**
     * 终端固件版本号长度 n
     */
    @JSONField(serialize=false)
    private int softVersionLength;

    /**
     * 终端固件版本号
     */
    private String softVersion;


    public int getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(int terminalType) {
        this.terminalType = terminalType;
    }

    public String getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(String manufactureId) {
        this.manufactureId = manufactureId;
    }

    public String getTerminalModelNo() {
        return terminalModelNo;
    }

    public void setTerminalModelNo(String terminalModelNo) {
        this.terminalModelNo = terminalModelNo;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public int getDeviceVersionLength() {
        return deviceVersionLength;
    }

    public void setDeviceVersionLength(int deviceVersionLength) {
        this.deviceVersionLength = deviceVersionLength;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public int getSoftVersionLength() {
        return softVersionLength;
    }

    public void setSoftVersionLength(int softVersionLength) {
        this.softVersionLength = softVersionLength;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        try {
//            buff.put(getResponseMessageSerialNo());
//            buff.put(getResponseMessageId());
//            buff.put(getResponseResult());
        } finally {

        }
        return buff.toByteArrayAndRelease();

    }

    @Override
    public final void readFromBytes(byte[] messageBodyBytes) {
        ByteBuffer buff = new ByteBuffer(messageBodyBytes);
        // 终端类型
        setTerminalType(buff.getShort());
        // 制造商ID
        byte[] mfId = buff.gets(5);
        setManufactureId(HexUtil.encodeHexStr(mfId));
        //终端类型
        byte[] terminalModelNo = buff.gets(20);
        setTerminalModelNo(BitConverter.getString(terminalModelNo));
         //蓝牙地址
        String macString = buff.getBcdString(1)+":"+buff.getBcdString(1)+":"+buff.getBcdString(1)+":"+buff.getBcdString(1)+":"+buff.getBcdString(1)+":"+buff.getBcdString(1);
        setTerminalId(macString);
        //充电类型
        byte[] chargeType = buff.gets(1);
        setChargeType(""+chargeType[0]);
        //终端SIM卡ICCID
        String iccid = buff.getBcdString(10);
        setIccid(iccid);
        //终端硬件版本
        byte[] FW_lenth = buff.gets(1);
        byte[] FW = buff.gets(FW_lenth[0]);
        setDeviceVersion(BitConverter.getString(FW));
        //终端固件版本
        byte[] SW_lenth = buff.gets(1);
        byte[] SW = buff.gets(SW_lenth[0]);
        setSoftVersion(BitConverter.getString(SW));

         log.info("解析:"+this.toString());
    }

}

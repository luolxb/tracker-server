/*******************************************************************************
 * @(#)CanDataItem.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

/**
 * <p>
 *     CAN总线数据项
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 20:34
 */
public class CanDataItem {

    /**
     * canId
     */
    private int canId;
    /**
     * can通道号
     */
    private byte channel;
    /**
     *帧类型
     * */
    private byte frameType;
    /**
     * 数据采集方式
     */
    private byte dataWay;


    private byte[] canData;


    public int getCanId() {
        return canId;
    }


    public void setCanId(int canId) {
        this.canId = canId;
    }


    public byte getChannel() {
        return channel;
    }


    public void setChannel(byte channel) {
        this.channel = channel;
    }


    public byte getFrameType() {
        return frameType;
    }


    public void setFrameType(byte frameType) {
        this.frameType = frameType;
    }


    public byte getDataWay() {
        return dataWay;
    }


    public void setDataWay(byte dataWay) {
        this.dataWay = dataWay;
    }


    public byte[] getCanData() {
        return canData;
    }


    public void setCanData(byte[] canData) {
        this.canData = canData;
    }

}

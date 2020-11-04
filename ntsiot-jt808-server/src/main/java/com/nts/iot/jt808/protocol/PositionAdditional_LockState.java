/*******************************************************************************
 * @(#)PositionAdditionalLockState.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.Tools;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-02 11:52
 */
@Slf4j
public class PositionAdditional_LockState implements IPositionAdditionalItem {

    /**
     * 0表示锁梁闭合、1表示锁梁打开
     */
    private String lock;

    /**
     * 状态上报 0000 定时上报 0001 开锁上报。0010关锁上报
     */
    private String poiupState;

    /**
     * 0 表示运营，1 表示停止运营
     */
    private String operate;

    /**
     * 0 表示放电，1 表示正在充电
     */
    private String charge;

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getPoiupState() {
        return poiupState;
    }

    public void setPoiupState(String poiupState) {
        this.poiupState = poiupState;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    @Override
    public int getAdditionalId() {
        return 226;
    }

    @Override
    public byte getAdditionalLength() {
        return 0x2;
    }

    @Override
    public byte[] writeToBytes() {
        return Tools.HexString2Bytes(charge + operate + poiupState + lock);
    }

    @Override
    public void readFromBytes(byte[] bytes) {

        String hex = Tools.ToHexString(bytes);
        int i = Integer.parseInt(hex, 16);
        String str = Integer.toBinaryString(i);
        int index = 8 - str.length();
        if (index > 0) {
            for (int j = 0; j < index; j++) {
                str = "0" + str;
            }
        }
        lock = str.substring(7, 8);
        poiupState = str.substring(3, 7);
        operate = str.substring(2, 3);
        charge = str.substring(1, 2);
        log.info("锁梁:"+lock+"(1: 锁梁开启，0 锁梁闭合)"+
                "状态上报:"+poiupState+"(状态上报 0000 定时上报 0001 开锁上报。0010关锁上报)"+
                "运营状态:"+operate+"(0 表示运营，1 表示停止运营)"+
                "充电状态"+charge+"(0 表示放电，1 表示正在充电)");
    }
}

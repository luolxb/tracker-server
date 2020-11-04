/*******************************************************************************
 * @(#)SpeedRecorder.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.jt2012;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 22:20
 */
public class SpeedRecorder {

    private int id;

    private VehicleRecorder recorder;

    private double speed;
    /**
     * 开关量
     */
    private int signal;

    /**
     * 序号
     */
    private int sn;

    /**
     * 记录时间
     */
    private Date recorderDate;

    /**
     * 创建时间
     */
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleRecorder getRecorder() {
        return recorder;
    }

    public void setRecorder(VehicleRecorder recorder) {
        this.recorder = recorder;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public Date getRecorderDate() {
        return recorderDate;
    }

    public void setRecorderDate(Date recorderDate) {
        this.recorderDate = recorderDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

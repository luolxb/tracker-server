/*******************************************************************************
 * @(#)VehicleRecorder.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.jt2012;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 22:20
 */
public class VehicleRecorder {

    private Date createDate;

    private long vehicleId;

    private int cmd;

    private String driverLicense;

    private String cmdData;
    /**
     * 数据开始时间
     */
    private Date startTime;

    private Date endTime;

    private double speed;

    /**
     * 信号开关 8位， 代表 8个信号量
     */
    private byte signalState;

    /**
     * 下发的命令Id,可以根据此Id查询结果
     */
    private long commandId;

    /**
     * 数据块 中，每个数据的计时 序号
     */
    private int sortId;

    private double latitude;

    private double longitude;

    private double altitude;

    private List<SpeedRecorder> speedList = new ArrayList<SpeedRecorder>();

    private int entityId;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getCmdData() {
        return cmdData;
    }

    public void setCmdData(String cmdData) {
        this.cmdData = cmdData;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public byte getSignalState() {
        return signalState;
    }

    public void setSignalState(byte signalState) {
        this.signalState = signalState;
    }

    public long getCommandId() {
        return commandId;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public List<SpeedRecorder> getSpeedList() {
        return speedList;
    }

    public void setSpeedList(List<SpeedRecorder> speedList) {
        this.speedList = speedList;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}

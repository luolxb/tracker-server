/*******************************************************************************
 * @(#)CircleAreaItem.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 23:05
 */
public class CircleAreaItem {

    /**
     * 区域ID
     */
    private int circleAreaId;

    public final int getCircleAreaId() {
        return circleAreaId;
    }

    public final void setCircleAreaId(int value) {
        circleAreaId = value;
    }

    /**
     * 区域属性
     */

    private short circleAreaProperty;

    public final short getCircleAreaProperty() {
        return circleAreaProperty;
    }

    public final void setCircleAreaProperty(short value) {
        circleAreaProperty = value;
    }

    /**
     * 中心点纬度,以度为单位的纬度值乘以10的6次方，精确到百万分之一度
     */

    private int centerLatitude;

    public final int getCenterLatitude() {
        return centerLatitude;
    }

    public final void setCenterLatitude(int value) {
        centerLatitude = value;
    }

    /**
     * 中心点经度,以度为单位的经度值乘以10的6次方，精确到百万分之一度
     */

    private int centerLongitude;

    public final int getCenterLongitude() {
        return centerLongitude;
    }

    public final void setCenterLongitude(int value) {
        centerLongitude = value;
    }

    /**
     * 半径,单位为米（m），路段为该拐点到下一拐点
     */

    private int radius;

    public final int getRadius() {
        return radius;
    }

    public final void setRadius(int value) {
        radius = value;
    }

    /**
     * 起始时间,YY-MM-DD-hh-mm-ss，若区域属性0位为0则没有该字段
     */
    private java.util.Date startTime = new java.util.Date(0);

    public final java.util.Date getStartTime() {
        return startTime;
    }

    public final void setStartTime(java.util.Date value) {
        startTime = value;
    }

    /**
     * 结束时间,YY-MM-DD-hh-mm-ss，若区域属性0位为0则没有该字段
     */
    private java.util.Date endTime = new java.util.Date(0);

    public final java.util.Date getEndTime() {
        return endTime;
    }

    public final void setEndTime(java.util.Date value) {
        endTime = value;
    }

    /**
     * 最高速度,Km/h，若区域属性1位为0则没有该字段
     */

    private short maxSpeed;

    public final short getMaxSpeed() {
        return maxSpeed;
    }

    public final void setMaxSpeed(short value) {
        maxSpeed = value;
    }

    /**
     * 超速持续时间,单位秒(s),若区域属性1位为0则没有该字段
     */
    private byte overSpeedLastTime;

    public final byte getOverSpeedLastTime() {
        return overSpeedLastTime;
    }

    public final void setOverSpeedLastTime(byte value) {
        overSpeedLastTime = value;
    }

    /**
     * 拍照启动最高速度,单位为公里每小时(km/h)，当速度降到最高速度以下就启动拍照,若区域属性8位为0则没有该字段
     */
    private byte photoMaxSpeed;

    public final byte getPhotoMaxSpeed() {
        return photoMaxSpeed;
    }

    public final void setPhotoMaxSpeed(byte value) {
        photoMaxSpeed = value;
    }

    /**
     * 速度降到最高速度以下继续时间,单位为秒(S),若区域属性8位为0则没有该字段
     */
    private byte lastTimeBelowPhotoMaxSpeed;

    public final byte getLastTimeBelowPhotoMaxSpeed() {
        return lastTimeBelowPhotoMaxSpeed;
    }

    public final void setLastTimeBelowPhotoMaxSpeed(byte value) {
        lastTimeBelowPhotoMaxSpeed = value;
    }

    /**
     * 点火拍照时间间隔,单位5秒，如果为0关闭点火拍照,若区域属性8位为0则没有该字段
     */
    private byte privateFireOnPhotoInterval;

    public final byte getFireOnPhotoInterval() {
        return privateFireOnPhotoInterval;
    }

    public final void setFireOnPhotoInterval(byte value) {
        privateFireOnPhotoInterval = value;
    }

    /**
     * 熄火拍照延时时间,单位分钟，若区域属性8位为0则没有
     */
    private byte privateFireOffPhotoDelay;

    public final byte getFireOffPhotoDelay() {
        return privateFireOffPhotoDelay;
    }

    public final void setFireOffPhotoDelay(byte value) {
        privateFireOffPhotoDelay = value;
    }

    /**
     * 区域名称长度,若区域属性15位为0 则没有该字段
     */
    private byte circleAreaNameLength;

    public final byte getCircleAreaNameLength() {
        return circleAreaNameLength;
    }

    public final void setCircleAreaNameLength(byte value) {
        circleAreaNameLength = value;
    }

    /**
     * 区域名称,经GBK编码 若区域属性15位为0 则没有改字段
     */
    private String circleAreaName;

    public final String getCircleAreaName() {
        return circleAreaName;
    }

    public final void setCircleAreaName(String value) {
        circleAreaName = value;
    }

}

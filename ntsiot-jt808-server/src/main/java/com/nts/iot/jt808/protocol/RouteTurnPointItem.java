/*******************************************************************************
 * @(#)RouteTurnPointItem.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 23:12
 */
public class RouteTurnPointItem {

    /**
     * 拐点ID
     */
    private int routePointId;

    public final int getRoutePointId() {
        return routePointId;
    }

    public final void setRoutePointId(int value) {
        routePointId = value;
    }

    /**
     * 路段ID
     */
    private int routeSegmentId;

    public final int getRouteSegmentId() {
        return routeSegmentId;
    }

    public final void setRouteSegmentId(int value) {
        routeSegmentId = value;
    }

    /**
     * 拐点纬度,以度为单位纬度值乘以10的6次方，精确到百万分之一度
     */
    private int turnPointLatitude;

    public final int getTurnPointLatitude() {
        return turnPointLatitude;
    }

    public final void setTurnPointLatitude(int value) {
        turnPointLatitude = value;
    }

    /**
     * 拐点经度,以度为单位经度值乘以10的6次方，精确到百万分之一度
     */
    private int turnPointLongitude;

    public final int getTurnPointLongitude() {
        return turnPointLongitude;
    }

    public final void setTurnPointLongitude(int value) {
        turnPointLongitude = value;
    }

    /**
     * 路段宽度,单位为米（m），路段为该拐点到下一拐点
     */
    private byte routeSegmentWidth;

    public final byte getRouteSegmentWidth() {
        return routeSegmentWidth;
    }

    public final void setRouteSegmentWidth(byte value) {
        routeSegmentWidth = value;
    }

    /**
     * 路段属性
     */
    private byte routeSegmentProperty;

    public final byte getRouteSegmentProperty() {
        return routeSegmentProperty;
    }

    public final void setRouteSegmentProperty(byte value) {
        routeSegmentProperty = value;
    }

    /**
     * 路段行驶过长阈值,单位为秒（s），若路段属性0位为0则没有该字段
     */
    private short maxDriveTimeLimited;

    public final short getMaxDriveTimeLimited() {
        return maxDriveTimeLimited;
    }

    public final void setMaxDriveTimeLimited(short value) {
        maxDriveTimeLimited = value;
    }

    /**
     * 路段行驶不足阈值,单位为秒（s），若路段属性0位为0则没有该字段
     */
    private short minDriveTimeLimited;

    public final short getMinDriveTimeLimited() {
        return minDriveTimeLimited;
    }

    public final void setMinDriveTimeLimited(short value) {
        minDriveTimeLimited = value;
    }

    /**
     * 路段最高速度,单位为公里每小时（km/h），若路段属性1位为0则没有该字段
     */
    private short maxSpeedLimited;

    public final short getMaxSpeedLimited() {
        return maxSpeedLimited;
    }

    public final void setMaxSpeedLimited(short value) {
        maxSpeedLimited = value;
    }

    /**
     * 路段超速持续时间,单位为秒（s），若路段属性1位为0则没有该字段
     */
    private byte overMaxSpeedLastTime;

    public final byte getOverMaxSpeedLastTime() {
        return overMaxSpeedLastTime;
    }

    public final void setOverMaxSpeedLastTime(byte value) {
        overMaxSpeedLastTime = value;
    }

    /**
     * 站点纬度,以度为单位纬度值乘以10的6次方，精确到百万分之一度；若路段属性7位为0则没有该字段
     */
    private int routeStationPointLatitude;

    public final int getRouteStationPointLatitude() {
        return routeStationPointLatitude;
    }

    public final void setRouteStationPointLatitude(int value) {
        routeStationPointLatitude = value;
    }

    /**
     * 站点经度,以度为单位经度值乘以10的6次方，精确到百万分之一度；若路段属性7位为0则没有该字段
     */
    private int routeStationPointLongitude;

    public final int getRouteStationPointLongitude() {
        return routeStationPointLongitude;
    }

    public final void setRouteStationPointLongitude(int value) {
        routeStationPointLongitude = value;
    }

    /**
     * 站点名称长度,若路段属性7位为0则没有该字段
     */
    private byte routeStationNameLength;

    public final byte getRouteStationNameLength() {
        return routeStationNameLength;
    }

    public final void setRouteStationNameLength(byte value) {
        routeStationNameLength = value;
    }

    /**
     * 站点名称,经GBK编码, 长度n若路段属性7位为0则没有该字段
     */
    private String routeStationName;

    public final String getRouteStationName() {
        return routeStationName;
    }

    public final void setRouteStationName(String value) {
        routeStationName = value;
    }

}

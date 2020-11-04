/*******************************************************************************
 * @(#)GpsUtil.java 2019-05-14
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.util;

import com.nts.iot.dto.CollectMessage;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-14 10:19
 */
public class GpsUtil {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 根据两个坐标点获得之间的距离
     *
     * @param gpsFrom   起始点
     * @param gpsTo     到达点
     * @param ellipsoid 坐标系方式
     * @return 距离
     */
    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geoCurve.getEllipsoidalDistance();
    }

    public static double getDistanceMeter(CollectMessage from, CollectMessage to, Ellipsoid ellipsoid) {
        GlobalCoordinates gpsFrom = new GlobalCoordinates(Double.parseDouble(from.getLatitude()), Double.parseDouble(from.getLongitude()));
        GlobalCoordinates gpsTo = new GlobalCoordinates(Double.parseDouble(to.getLatitude()), Double.parseDouble(to.getLongitude()));
        return getDistanceMeter(gpsFrom, gpsTo, ellipsoid);
    }

    public static double getDistance(CollectMessage from, CollectMessage to) {
        double radLat1 = rad(Double.parseDouble(from.getLatitude()));
        double radLat2 = rad(Double.parseDouble(to.getLatitude()));
        double a = radLat1 - radLat2;
        double b = rad(Double.parseDouble(from.getLongitude())) - rad(Double.parseDouble(to.getLongitude()));
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10d;
        return s;
    }

}

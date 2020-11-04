/*******************************************************************************
 * @(#)CollectMessage.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.dto;

import cn.hutool.core.date.DateUtil;
import com.nts.iot.jt808.protocol.JT_0200;
import com.nts.iot.jt808.utils.PositionUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 加入kafka的消息体
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-02 09:51
 */
@Slf4j
@Data
public class CollectMessage implements Serializable {

    public CollectMessage() {
    }

    public CollectMessage(final String deviceNo_, final JT_0200 jt0200) {
        deviceNo = deviceNo_;
//        LocateInfo locateInfo = PositionUtil.wgs84_To_Gcj02(Double.valueOf(Double.valueOf(jt0200.getLatitude()) / 1000000), Double.valueOf(Double.valueOf(jt0200.getLongitude()) / 1000000));
        // 纬度
        latitude = String.valueOf((double) jt0200.getLatitude() / 1000000);

        // 经度
        longitude = String.valueOf((double) jt0200.getLongitude() / 1000000);
        systemTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        // 上报时间 由于jt808协议中上报的时间不准确，统一使用服务器时间
        //if (!deviceNo.startsWith("c3")) {//不以c3开头暂定为思科尔特设备,思科尔特设备暂用服务器时间
        //    time = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        //} else {
        /**
         * 使用系统时间不科学，设备端会存在盲点补传机制（设备在无移动网络情况下，设备的定位数据需要等待有移动网络时补传数据）。这样会造成数据点混乱。
         * 使用协议里的本地时间，不需要加8小时。
         */
        time = jt0200.getTime();
//            Date date = new Date(DateUtil.parse(time).getTime() + 1000 * 60 * 60 * 8);
        Date date = new Date(DateUtil.parse(time).getTime());
        time = DateUtil.formatDateTime(date);
        log.info("设备上报时间: " + time);
        // }
        // 无线型号强度
        simSignal = String.valueOf(jt0200.getSignal());
        // 定位卫星数
        satellites = String.valueOf(jt0200.getSatellite());

        if (jt0200.getInsideVoltage() != null) {
            cellPower = String.valueOf(jt0200.getInsideVoltage().getCellPower());// 内部电量
            cellVoltage = String.valueOf(jt0200.getInsideVoltage().getCellVoltage() * 10);// 内部电压
        } else {
            cellPower = "";
            cellVoltage = "";
        }

        // 外部电量
        if (jt0200.getOutsideVoltage() != null) {
            outCellPower = String.valueOf(jt0200.getOutsideVoltage().getOutCellPower());
        } else {
            outCellPower = "";
        }
        // 外部电压
        if (jt0200.getOutsideVoltage() != null) {
            outCellVoltage = String.valueOf(jt0200.getOutsideVoltage().getOutCellVoltage());
        } else {
            outCellVoltage = "";
        }

        if (jt0200.getLockState() != null) {
            // 锁梁状态
            beamState = String.valueOf(jt0200.getLockState().getLock());
        } else {
            beamState = "";
        }

        if (jt0200.getLockState() != null) {
            // 状态上报
            poiupState = jt0200.getLockState().getPoiupState();
        } else {
            poiupState = "";
        }

        // 速度
        speed = String.valueOf(jt0200.getSpeed() / (float) 10);
        course = String.valueOf(jt0200.getCourse());
        log.info(jt0200.toString() + ",经度longitude：" + longitude + ",纬度latitude：" + latitude);
    }


    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 状态
     */
    private String status;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 海拔高度，单位为米（m）
     */
    private String altitude;

    /**
     * 速度,1/10km/h
     */
    private String speed;

    /**
     * 方向,0～359，正北为0，顺时针
     */
    private String course;

    /**
     * 时间,YY-MM-DD-hh-mm-ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）
     */
    private String time;

    private String systemTime;

    /**
     * 无线型号强度
     */
    private String simSignal;

    /**
     * 定位卫星数
     */
    private String satellites;

    /**
     * 内部电量
     */
    private String cellPower;

    /**
     * 内部电压
     */
    private String cellVoltage;

    /**
     * 外部电量
     */
    private String outCellPower;

    /**
     * 外部电压
     */
    private String outCellVoltage;

    /**
     * 状态上报 0000 定时上报 0001 开锁上报。0010关锁上报。
     */
    private String poiupState;

    /**
     * 锁梁状态
     */
    private String beamState;


}

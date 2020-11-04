/*******************************************************************************
 * @(#)CollectMessage.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 加入kafka的消息体
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-02 09:51
 */
@Data
public class CollectMessage implements Serializable, Comparable<CollectMessage> {

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
     * 设备上传时间
     */
    private String time;

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

    /**
     * 服务器时间
     */
    private String systemTime;

    private String alertType;


   /* @Override
    public int compareTo(TrajectoryDto t) {
        Long diff = this.mis - t.getMis();
        return diff.intValue();
    }*/

    @Override
    public int compareTo(CollectMessage o) {
        String time = o.getTime();
        if (time != null && this.getTime() != null) {
            Date after = DateUtil.parse(time, "yyyy-MM-DD HH:mm:ss");
            Date before = DateUtil.parse(this.getTime(), "yyyy-MM-DD HH:mm:ss");
            long stop = before.getTime() - after.getTime();
            if (stop > 0) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }
}

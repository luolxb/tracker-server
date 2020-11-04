/*******************************************************************************
 * @(#)Security.java 2019-05-18
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-18 15:19
 */
public class SecurityDto implements Serializable {

    /**
     * 编号
     */
    private String id;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 实行任务的车辆(key是车辆编号，list是对应的订单编号)
     */
    private Map<String, List<String>> map;

//    list多个订单 List多个轨迹 map key  经度 纬度  value 经度值 value 纬度值
//    List<List<Map<String, Object>>>

    /**
     * 总里程数
     */
    private String distance;

    /**
     * 停留时间
     */
    private String stopTime;

    /**
     * 辖区编号
     */
    private String jurisdiction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<String>> map) {
        this.map = map;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }
}

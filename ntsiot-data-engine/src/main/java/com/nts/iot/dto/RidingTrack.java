package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

/**
 * 订单骑行轨迹
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/21 11:57
 * @Description:
 */
@Data
public class RidingTrack implements Serializable {

    /**
     * 轨迹
     */
    private List<Object> path;
    /**
     * gps时间
     */
    private List<String> gpstime;
    /**
     * 比到点
     */
    private List<Object> points;
    /**
     *订单编号
     */
    private String orderId;
    /**
     *骑行开始时间
     */
    private Long startTime;
    /**
     *骑行结束时间
     */
    private Long endTime;
    /**
     *骑行距离
     */
    private Double distance;
    /**
     *车辆编号
     */
    private String bikeBarcode;

}

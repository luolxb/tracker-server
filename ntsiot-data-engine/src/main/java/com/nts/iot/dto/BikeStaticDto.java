package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 车辆运行统计
 */
@Data
public class BikeStaticDto implements Serializable {
    // 车辆编号
    private String bikeNumber;
    // 总行驶里程
    private Double totalDistance;
    // 平均骑行速度
    private Double averageSpeed;
//    // 总停留时间
//    private Double totalStopTime;
    // 总骑行时间
    private Double totalRunTime;
}

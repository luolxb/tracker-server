package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备的运行统计
 */
@Data
public class DeviceStatisticDto implements Serializable {
    // 车辆编号
    private String deviceNo;
    // 总行驶里程
    private Double totalDistance;

    //停留次数
    private Long totalPauseCount;

    //超速次数
    private Long totalOverSpeedCount;

    /**
     * 围栏里的距离
     */
    private Double totalDistanceInFence;

    /**
     * 围栏外的距离
     */
    private Double totalDistanceOutFence;

    /**
     * 起始点时间
     */
    private String beginTime;
    /**
     * 结束点时间
     */
    private String endTime;

    // 平均骑行速度
    private Double averageSpeed;

    // 总骑行时间
    private Long totalRunTime;

    //总停留时间
    private Long totalPauseTime;
}

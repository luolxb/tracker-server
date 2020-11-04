package com.nts.iot.dto;

import lombok.Data;

@Data
public class RoadStatisitcInfo {

    /**
     * 总距离
     */
    private Double totalDistance;
    /**
     * 停留次数
     */
    private Long totalPauseCount;

    /**
     * 超速次数
     */
    private Long total0verSpeedCount;
    /**
     * 暂停总时间
     */
    private Long totalPauseTime;

    /**
     * 运行总时间
     */
    private Long totalRunTime;
    /**
     * 平均速度
     */
    private Double AvgSpeed;

    /**
     * 围栏里的距离
     */
    private Double totalDistanceFenceIn;

    /**
     * 围栏外的距离
     */
    private Double totalDistanceFenceOut;


}

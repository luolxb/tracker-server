package com.nts.iot.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 车辆运行(订单)统计
 */
@Data
public class OrderBikeStaticDTO implements Serializable {
    // 用户编号
    private String userId;
    // 用户姓名
    private String userName;
    // 总行驶里程
    private Double totalDistance;
    // 总骑行时间
    private Double totalRunTime;
    // 平均骑行速度
    private Double averageSpeed;
}

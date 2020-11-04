/*******************************************************************************
 * @(#)Order.java 2019-05-03
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     订单
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-03 10:02
 */
@Data
public class OrderDto implements Serializable {

    /**
     * 订单编号
     */
    @Id
    private String id;

    /**
     * 设备编号(车辆编号)
     */
    private String deviceNo;

    /**
     * 轨迹情况
     */
    private List<Trajectory> trajectories;

    /**
     * 必到点的情况
     */
    private List<CheckPoint> checkPoints;

    /**
     * 坐标点之间的路
     */
    private List<Road> roads;

    /**
     * 骑行的距离
     */
    private Double distance;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;
}

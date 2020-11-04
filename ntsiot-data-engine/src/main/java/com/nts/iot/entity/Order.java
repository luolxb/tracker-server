/*******************************************************************************
 * @(#)Order.java 2019-05-03
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.entity;

import com.nts.iot.dto.CheckPoint;
import com.nts.iot.dto.Road;
import com.nts.iot.dto.Trajectory;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
@Document(indexName = "trajectory", type = "order")
public class Order implements Serializable, Comparable<Order> {

    /**
     * 订单编号
     */
    @Id
    private String id;

    /**
     * 设备编号(车辆编号)
     */
    @Field
    private String deviceNo;

    /**
     * 智能锁编码
     */
    @Field
    private String lockNo;

    /**
     * 轨迹情况
     */
    @Field
    private List<Trajectory> trajectories;

    /**
     * 必到点的情况
     */
    @Field
    private List<CheckPoint> checkPoints;

    /**
     * 坐标点之间的路
     */
    @Field
    private List<Road> roads;

    /**
     * 骑行的距离
     */
    @Field
    private Double distance;

    /**
     * 开始时间
     */
    @Field
    private Long startTime;

    /**
     * 结束时间
     */
    @Field
    private Long endTime;

    /**
     * 辖区编号
     */
    @Field
    private Long jurisdiction;

    @Override
    public int compareTo(Order o) {
        Long diff = this.startTime - o.getStartTime();
        return diff.intValue();
    }
}

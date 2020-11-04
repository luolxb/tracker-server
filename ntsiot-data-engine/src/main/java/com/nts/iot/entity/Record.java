/*******************************************************************************
 * @(#)Record.java 2019-05-13
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.entity;

import com.nts.iot.dto.Road;
import com.nts.iot.dto.Trajectory;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     记录
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-13 14:15
 */
@Data
@Document(indexName = "trajectory_record", type = "record")
public class Record implements Serializable {

    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 设备编号
     */
    @Field
    private String deviceNo;

    /**
     * 起始点时间
     */
    private String beginTime;

    /**
     * 结束点时间
     */
    private String endTime;

    /**
     * 轨迹情况
     */
    @Field
    private List<Trajectory> trajectories;

    /**
     * 骑行的距离
     */
    @Field
    private Double totalDistance;

    /**
     * 坐标点之间的路
     */
    @Field
    private List<Road> roads;

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
    private Double speed;

    /**
     * 围栏里的距离
     */
    private Double totalDistanceFenceIn;

    /**
     * 围栏外的距离
     */
    private Double totalDistanceFenceOut;

}

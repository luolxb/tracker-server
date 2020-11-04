/*******************************************************************************
 * @(#)Security.java 2019-05-18
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

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
@Data
@Document(indexName = "trajectory_security", type = "security")
public class Security implements Serializable {

    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 开始时间
     */
    @Field
    private String beginTime;

    /**
     * 结束时间
     */
    @Field
    private String endTime;

    /**
     * 实行任务的车辆(key是车辆编号，list是对应的订单编号)
     */
    @Field
    private Map<String, List<String>> map;

//    list多个订单 List多个轨迹 map key  经度 纬度  value 经度值 value 纬度值
//    List<List<Map<String, Object>>>

    /**
     * 总里程数
     */
    @Field
    private String distance;

    /**
     * 停留时间
     */
    @Field
    private String stopTime;

    /**
     * 辖区编号
     */
    @Field
    private String jurisdiction;
}

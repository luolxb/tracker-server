/*******************************************************************************
 * @(#)OrderDto.java 2019-05-15
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.dto;

import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-15 17:50
 */
@Data
public class OrderDto {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态
     */
    private Long status;

    /**
     * 使用用户
     */
    private Long userId;

    /**
     * 车辆编号
     */
    private String bikeBarcode;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 开始经度
     */
    private String startLng;

    /**
     * 开始纬度
     */
    private String startLat;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 结束经度
     */
    private String endLng;

    /**
     * 结束纬度
     */
    private String endLat;

    /**
     * 订单里程
     */
    private Double mile;

    /**
     * 辖区编号
     */
    private Long jurisdiction;

    /**
     * 结束来源 0：正常关闭； 1：后台关闭
     */
    private Long closeSource;

    /**
     * 订单金额
     */
    private Double orderAmount;

    /**
     * 补偿金额
     */
    private Double compensationAmount;

    /**
     * 用户
     */
    private String username;

    /**
     * 电话
     */
    private String phone;

    /**
     * 辖区
     */
    private String deptName;

}

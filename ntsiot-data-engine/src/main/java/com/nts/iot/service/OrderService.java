/*******************************************************************************
 * @(#)OrderService.java 2019-05-03
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.service;

import com.nts.iot.dto.OrderBikeQueryDTO;
import com.nts.iot.dto.OrderBikeStaticDTO;
import com.nts.iot.dto.RidingTrack;
import com.nts.iot.entity.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-03 10:22
 */
public interface OrderService {

    /**
     * 订单保存
     *
     * @param order 订单对象
     */
    void save(Order order);

    /**
     * 根据订单编号将订单相关的所有信息都处理好，然后存储到ES中
     *
     * @param orderNo
     */
    void saveByDeviceNo(String orderNo);

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderNo
     * @return
     */
    RidingTrack findOne(String orderNo);

    List<Order> queryAll(String orderNo, Long startTime, Long endTime);

    /**
     * 根据订单List获得订单轨迹
     *
     * @param ids
     * @return
     */
    List<List<Map<String, Object>>> findAllByIdIn(List<String> ids);

    List<OrderBikeStaticDTO> getBikeStateByIds(List<OrderBikeQueryDTO> orderBikeQuerys);
}

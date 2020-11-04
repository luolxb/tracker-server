/*******************************************************************************
 * @(#)OrderRepository.java 2019-05-03
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.repository;

import com.nts.iot.entity.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-03 10:21
 */
public interface OrderRepository extends ElasticsearchRepository<Order, String> {

    List<Order> findAllByEndTimeBetween(Long startTime, Long endTime);

    List<Order> findAllByDeviceNoAndAndEndTimeBetween(String deviceNo, Long startTime, Long endTime);

    List<Order> findAllByIdIn(List<Long> ids);

    List<Order> findByDeviceNo(String deviceNo);
}

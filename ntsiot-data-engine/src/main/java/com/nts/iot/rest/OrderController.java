/*******************************************************************************
 * @(#)OrderController.java 2019-05-14
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.rest;

import com.nts.iot.dto.OrderBikeQueryDTO;
import com.nts.iot.dto.OrderBikeStaticDTO;
import com.nts.iot.entity.Order;
import com.nts.iot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-14 20:11
 */
@RestController
@RequestMapping("")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单保存
     *
     * @param order
     * @return
     */
    @RequestMapping("/order/save")
    public ResponseEntity save(@RequestBody Order order) {
        orderService.save(order);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 根据订单编号将订单相关的所有信息都处理好，然后存储到ES中
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("/order/save/{orderNo}")
    public ResponseEntity saveByDeviceNo(@PathVariable String orderNo) {
        orderService.saveByDeviceNo(orderNo);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("order/ridingTrack/{orderId}")
    public ResponseEntity findOne(@PathVariable String orderId) {
        return new ResponseEntity(orderService.findOne(orderId), HttpStatus.OK);
    }

    /**
     * 根据车辆编号，查询历史轨迹
     * @param bikeBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("bikes/historicalRoute")
    public ResponseEntity queryAll(@RequestParam("bikeBarcode") String bikeBarcode, @RequestParam("startTime") Long startTime,
                                   @RequestParam("endTime") Long endTime) {
        return new ResponseEntity(orderService.queryAll(bikeBarcode, startTime, endTime), HttpStatus.OK);
    }

    /**
     * 根据订单List获得订单轨迹
     *
     * @param ids
     * @return
     */
    @PostMapping("/order/findAllByIdIn")
    public List<List<Map<String, Object>>> findAllByIdIn(@RequestBody List<String> ids) {
        return orderService.findAllByIdIn(ids);
    }

    /**
     * 根据订单List获得统计信息
     *
     * @param orderBikeQuerys
     * @return
     */
    @PostMapping("/order/getBikeStateByIds")
    public List<OrderBikeStaticDTO> getBikeStateByIds(@RequestBody List<OrderBikeQueryDTO> orderBikeQuerys) {
        return orderService.getBikeStateByIds(orderBikeQuerys);
    }
}

/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.rest;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.Lock;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.LockService;
import com.nts.iot.modules.system.service.OrderManagerService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.nts.iot.constant.RedisKey.*;


/**
 * <p>
 * </p>
 * 小程序接口
 *
 * @author <a href="mailto:zzm@rnstec.com">zzm</a>
 * @version dls-api 1.0 $ 2017-11-08
 */
@Slf4j
@RestController
@RequestMapping("ma")
public class MaOrderAppController {


    @Autowired
    OrderManagerService orderManagerService;

    @Autowired
    MaUserService userService;

    @Autowired
    BikeManagerService bikeManagerService;

    @Autowired
    MaUserService maUserService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LockService lockService;

    /**
     * 创建订单
     *
     * @return Result Result
     */
    @PostMapping("/creatOrder")
    public Map<String, Object> creatOrder(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        if (ToolUtil.isOneEmpty(requestBody.getBikeBarcode(), requestBody.getLockBarcode(), requestBody.getOpenId())) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        } else {
            // 查询此人是否已经有订单
            MaUser maUser = userService.getUserByWxId(requestBody.getOpenId());
            if (maUser == null) {
                result.put("code", 500);
                result.put("message", "该用户未注册");
                return result;
            }
            /* 查询该用户是否有未完成订单 */
            QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
            // 订单进行中
            queryWrapper2.eq("status", 2L);
            // 查询某人
            queryWrapper2.eq("user_id", maUser.getId());
            List<Order> orders1 = orderManagerService.list(queryWrapper2);
            if (orders1 != null && orders1.size() > 0) {
                result.put("code", 500);
                result.put("message", "用户已经存在订单");
                return result;
            }
            /* 查询该车是否有未完成订单 */
            QueryWrapper<Order> bikeQuery = new QueryWrapper<>();
            // 订单进行中
            bikeQuery.eq("status", 2L);
            // 查询某人
            queryWrapper2.eq("bike_barcode", requestBody.getBikeBarcode());
            List<Order> orders2 = orderManagerService.list(queryWrapper2);
            if (orders2 != null && orders2.size() > 0) {
                result.put("code", 500);
                result.put("message", "该车已经存在订单");
                return result;
            }
            /* 创建订单 */
            Order order = orderManagerService.createOrder(requestBody.getBikeBarcode(), requestBody.getLockBarcode(), requestBody.getOpenId());
            if (order == null) {
                result.put("code", 500);
                result.put("message", "创建订单出错！");
                return result;
            }
            log.info("MaOrderAppController.creatOrder创建订单：" + JSON.toJSONString(order));
            result.put("code", 200);
            result.put("message", "创建成功");
        }
        return result;
    }

    @PostMapping("/getOrderByOpenId")
    public Map<String, Object> getOrderByOpenId(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String openId = requestBody.getOpenId();
        MaUser user = maUserService.getUserByWxId(openId);
        if (user == null) {
            return null;
        }
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        queryWrapper.orderByDesc("id");
        List<Order> orders = orderManagerService.list(queryWrapper);
        List<Map<String, Object>> tripsData = new ArrayList<>();
        for (Order order : orders) {
            Map<String, Object> trip = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = order.getStartTime() == null ? "" : formatter.format(new Date(order.getStartTime()));
            String endTime = order.getEndTime() == null ? "" : formatter.format(new Date(order.getEndTime()));
            // 骑行时间
            trip.put("time", startTime + " 至 " + endTime);
            // 车辆编号
            String last7num = order.getBikeBarcode() == null ? "0" : order.getBikeBarcode().substring(order.getBikeBarcode().length() - 7, order.getBikeBarcode().length());
            trip.put("no", last7num);
            // 骑行时间
            trip.put("timeLength", order.getMile() == null ? "0" : order.getMile());
            //
            trip.put("price", order.getOrderAmount() == null ? "0" : order.getOrderAmount());
            tripsData.add(trip);
        }
        result.put("tripsData", tripsData);
        return result;
    }


    /**
     * 完成订单回调
     *
     * @return Result Result
     */
    @PostMapping("/closeOrderByDeviceNo")
    public Map<String, Object> closeOrderByDeviceNo(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String lockBarCode = requestBody.getLockBarcode();
        if (ToolUtil.isOneEmpty(lockBarCode)) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        } else {
            /* 执行关闭订单操作 */
            result = orderManagerService.closeOrderByDeviceNo(lockBarCode);

            log.info("MaOrderAppController.closeOrderByDeviceNo关闭订单:" + JSON.toJSONString(result));
        }
        return result;
    }


    @PostMapping("/getBikeOrderInfo")
    public Map<String, Object> getBikeInfoByUser(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        Order order = orderManagerService.getRunningOrderByUserId(requestBody.getUserId());
        String message;
        String state;
        Bike bike = null;
        Lock lock = null;
        CollectMessage collectMessage = null;
        List<CollectMessage> trajectoryList = null;
        Map<String, Object> runData = new HashMap<>();
        if (order != null) {
            String bikeBarCode = order.getBikeBarcode();
            if (bikeBarCode != null) {
                bike = bikeManagerService.getBikeByBarcode(bikeBarCode);
                if (bike != null) {
                    // 当前状态
                    String currentState = redisUtil.getData(VECHILE_STATE + bike.getLockBarcode());
                    collectMessage = JsonUtil.jsonConvertObject(currentState, CollectMessage.class);
                    // 当前轨迹
                    String currentTrajectory = redisUtil.getData(ORDER_TRAJECTORY_KEY + order.getOrderId() + ":" + bike.getLockBarcode());
                    // 轨迹数据
                    trajectoryList = JSON.parseArray(currentTrajectory, CollectMessage.class);
                    Double distance = orderManagerService.getDistance(order.getOrderId(), bike.getLockBarcode());
                    String last7num = bikeBarCode.substring(bikeBarCode.length() - 7);
                    // 运行时间
                    Long runtime = System.currentTimeMillis() - order.getStartTime();
                    runData.put("bikeNo", last7num);
                    runData.put("distance", distance);
                    runData.put("time", runtime);
                    state = "running";
                    message = "车辆行进中";
                    lock = lockService.findLockByNo(bike.getLockBarcode());
                    if (lock != null) {
                        result.put("bleMacAddress", lock.getMacAddress());
                    } else {
                        result.put("bleMacAddress", null);
                    }
                } else {
                    state = "error";
                    message = "订单车辆没有找到";
                }
            } else {
                state = "error";
                message = "订单没有车辆编号";
            }
        } else {
            state = "error";
            message = "存在多个或者没有查到订单";
        }
//        System.out.println(state);
//        System.out.println(message);
        result.put("bike", bike);
        result.put("runData", runData);
        result.put("collectMessage", collectMessage);
        result.put("trajectoryList", trajectoryList);
        result.put("state", state);
        result.put("message", message);
        return result;
    }


    @PostMapping("/getBikeOrderInfoCheck")
    public Map<String, Object> getBikeInfoByUserCheck(@RequestBody MaRequestBody requestBody) {
        //临时http开锁判断
        String checkOpen = redisUtil.getData(HTTP_OPENLOCK + requestBody.getLockBarcode());
        Map<String, Object> result = new HashMap<>();
        if (null != checkOpen && "false".equals(checkOpen)) {
            result.put("code", 400);
            result.put("message", "开锁失败");
            redisUtil.deleteByKey(HTTP_OPENLOCK + requestBody.getLockBarcode());
            return result;
        }
        Order order = orderManagerService.getRunningOrderByUserId(requestBody.getUserId());
        String message;
        String state;
        Bike bike = null;
        Lock lock = null;
        CollectMessage collectMessage = null;
        List<CollectMessage> trajectoryList = null;
        Map<String, Object> runData = new HashMap<>();
        if (order != null) {
            String bikeBarCode = order.getBikeBarcode();
            if (bikeBarCode != null) {
                bike = bikeManagerService.getBikeByBarcode(bikeBarCode);
                if (bike != null) {
                    // 当前状态
                    String currentState = redisUtil.getData(VECHILE_STATE + bike.getLockBarcode());
                    collectMessage = JsonUtil.jsonConvertObject(currentState, CollectMessage.class);
                    // 当前轨迹
                    String currentTrajectory = redisUtil.getData(ORDER_TRAJECTORY_KEY + order.getOrderId() + ":" + bike.getLockBarcode());
                    // 轨迹数据
                    trajectoryList = JSON.parseArray(currentTrajectory, CollectMessage.class);
                    Double distance = orderManagerService.getDistance(order.getOrderId(), bike.getLockBarcode());
                    String last7num = bikeBarCode.substring(bikeBarCode.length() - 7);
                    // 运行时间
                    Long runtime = System.currentTimeMillis() - order.getStartTime();
                    runData.put("bikeNo", last7num);
                    runData.put("distance", distance);
                    runData.put("time", runtime);
                    state = "running";
                    message = "车辆行进中";
                    lock = lockService.findLockByNo(bike.getLockBarcode());
                    if (lock != null) {
                        result.put("bleMacAddress", lock.getMacAddress());
                    } else {
                        result.put("bleMacAddress", null);
                    }
                } else {
                    state = "error";
                    message = "订单车辆没有找到";
                }
            } else {
                state = "error";
                message = "订单没有车辆编号";
            }
        } else {
            state = "error";
            message = "存在多个或者没有查到订单";
        }
//        System.out.println(state);
//        System.out.println(message);
        result.put("bike", bike);
        result.put("runData", runData);
        result.put("collectMessage", collectMessage);
        result.put("trajectoryList", trajectoryList);
        result.put("state", state);
        result.put("message", message);
        return result;
    }


}

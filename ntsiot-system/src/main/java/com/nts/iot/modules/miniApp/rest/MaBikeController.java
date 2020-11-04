/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.rest;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.dto.CmdPackage;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.miniApp.util.ConversionUtil;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.miniApp.dto.BikeDto;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.JurisdictionConfiguration;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.service.*;

import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * </p>
 * 小程序车辆相关接口
 *
 * @author <a href="mailto:zzm@rnstec.com">zzm</a>
 * @version dls-api 1.0 $ 2017-11-08
 */
@Slf4j
@RestController
@RequestMapping("ma")
public class MaBikeController {

    /* 2019/07/05 开始拆分用户表 */
    @Autowired
    MaUserService userService;

    @Autowired
    DeptService deptService;

    @Autowired
    BikeManagerService bikeManagerService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    BikeLicenseService bikeLicenseService;

    @Autowired
    OrderManagerService orderManagerService;

    @Autowired
    private JurisdictionConfigurationService jurisdictionConfigurationService;


    @Value("${engineServerUrl}")
    private String engineServerUrl;

    @Value("${jt808ServerUrl}")
    private String jt808ServerUrl;

    @Value("${jt808ScottServerUrl}")
    private String jt808ScottServerUrl;

    @Value("${rechargeNotifyUrl}")
    private String rechargeNotifyUrl;

    @Value("${wechat.miniapp.appId}")
    private String maAppId;


    /**
     * 根据code查询车辆信息
     *
     * @param requestBody
     * @return
     */
    @PostMapping("checkCode")
    public Map<String, Object> checkCode(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        result.put("unlock_mode", BikeDto.UnlockMode.Scan_Unlock.value);
        if (ToolUtil.isOneEmpty(requestBody.getCode(), requestBody.getOpenId())) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        // 62码 转 十进制
        Long decodeNumber = ConversionUtil.decode(requestBody.getCode());
        result = cyclingCheckByBikeBarCode(String.valueOf(decodeNumber), requestBody.getOpenId(), result);
       /* Bike bike = bikeManagerService.getBikeByBarcode(String.valueOf(decodeNumber));
        if (bike == null) {
            // 如果查询到多个车 返回错误
            result.put("code", 500);
            result.put("message", "没有查到该车");
        } else {
            if (bike.getLockBarcode() != null) {
                // 查看该车状态,并获取返回数据{lockBarCode，bikeBarCode，power}、状态码、message
                result = bikeManagerService.getBikeStatus(bike, requestBody.getOpenId());
                if (result.get("code").equals(String.valueOf(200)) && bike.getUnlockMode() == BikeDto.UnlockMode.No_Unlock.value) {
                    //无需开锁

                    result.put("unlock_mode", BikeDto.UnlockMode.No_Unlock.value);
                    QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
                    // 订单进行中
                    queryWrapper2.eq("status", 2L);
                    queryWrapper2.eq("bike_barcode", bike.getBikeBarcode());
                    List<Order> orders = orderManagerService.list(queryWrapper2);
                    if (orders != null && orders.size() > 0 && orders.get(0) != null) {
                        result.put("code", 500);
                        result.put("message", "该车辆正在被使用!");
                        return result;
                    } else {
                        //生成订单
                        Order order = orderManagerService.createOrder(bike.getBikeBarcode(), bike.getLockBarcode(), requestBody.getOpenId());
                        result.put("order", JSON.toJSONString(order));
                    }
                }
            } else {
                result.put("code", 500);
                result.put("message", "无该锁条码");
            }
        }*/
        return result;
    }

    /**
     * 根据bikeBarCode 获取bike 信息
     *
     * @return Result Result
     */
    @PostMapping("/getByBikeBarCode")
    public Map<String, Object> getByBikeBarCode(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        result.put("unlock_mode", BikeDto.UnlockMode.Scan_Unlock.value);
        if (ToolUtil.isOneEmpty(requestBody.getBikeBarcode(), requestBody.getOpenId())) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        result = cyclingCheckByBikeBarCode(requestBody.getBikeBarcode(), requestBody.getOpenId(), result);
       /* Bike bike = bikeManagerService.getBikeByBarcode(requestBody.getBikeBarcode());
        if (bike == null) {
            // 如果查询到多个车 返回错误
            result.put("code", 500);
            result.put("message", "没有查到该车");
        } else {
            // 查看该车状态
            result = bikeManagerService.getBikeStatus(bike, requestBody.getOpenId());
        }*/
        return result;
    }

    private Map<String, Object> cyclingCheckByBikeBarCode(String bikeBarCode, String openId, Map<String, Object> result) {
        Bike bike = bikeManagerService.getBikeByBarcode(String.valueOf(bikeBarCode));
        if (bike == null) {
            // 如果查询到多个车 返回错误
            result.put("code", 500);
            result.put("message", "没有查到该车");
        } else {
            if (bike.getLockBarcode() != null) {
                // 查看该车状态,并获取返回数据{lockBarCode，bikeBarCode，power}、状态码、message
                result = bikeManagerService.getBikeStatus(bike, openId);

                if (result.containsKey("code") && result.get("code").toString().equals(String.valueOf(200)) && bike.getUnlockMode() == BikeDto.UnlockMode.No_Unlock.value) {
                    //无需开锁

                    result.put("unlock_mode", BikeDto.UnlockMode.No_Unlock.value);
                    QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
                    // 订单进行中
                    queryWrapper2.eq("status", 2L);
                    queryWrapper2.eq("bike_barcode", bike.getBikeBarcode());
                    List<Order> orders = orderManagerService.list(queryWrapper2);
                    if (orders != null && orders.size() > 0 && orders.get(0) != null) {
                        result.put("code", 500);
                        result.put("message", "该车辆正在被使用!");
                        //return result;
                    } else {
                        //生成订单
                        Order order = orderManagerService.createOrder(bike.getBikeBarcode(), bike.getLockBarcode(), openId);
                        log.info("cyclingCheckByBikeBarCode创建订单：" + JSON.toJSONString(order));
                        result.put("order", JSON.toJSONString(order));
                    }
                }
            } else {
                result.put("code", 500);
                result.put("message", "无该锁条码");
            }
        }
        return result;
    }

    @PostMapping("/checkLock")
    public Map<String, Object> checkLock(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String lockBarCode = requestBody.getLockBarcode();
        //Long decodeNumber = ConversionUtil.decode(requestBody.getCode());
        Bike bike = bikeManagerService.getBikeByDeviceNo(String.valueOf(lockBarCode));
        if (bike == null) {
            result.put("code", 400);
            result.put("message", "车辆信息有误！");
        } else {
            result.put("unlock_mode", BikeDto.UnlockMode.Scan_Unlock.value);
            if (bike.getUnlockMode() == BikeDto.UnlockMode.No_Unlock.value) {
                //不需要关锁，直接处理订单关闭业务
                result.put("unlock_mode", BikeDto.UnlockMode.No_Unlock.value);
                Map<String, Object> res = orderManagerService.closeOrderByDeviceNo(lockBarCode);
                result.put("code", res.get("code"));
                result.put("message", res.get("message"));

                log.info("MaBikeController.checkLock关闭订单:" + JSON.toJSONString(res));

                //不需要关锁的的话，关闭订单后,就不用管后面的实际关锁业务了
                return result;
            }
        }
        // 可以关闭车锁
        if (bikeManagerService.checkCloseLock(lockBarCode)) {
            result.put("code", 200);
            result.put("message", "可以开关锁");
        } else {
            result.put("code", 500);
            result.put("message", "未在指定区域内开关锁！");
        }
        return result;
    }


    @PostMapping("/closeLock")
    public Map<String, Object> closeLock(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String lockBarCode = requestBody.getLockBarcode();
        // 可以关闭车锁
        if (bikeManagerService.checkCloseLock(lockBarCode)) {
            CmdPackage cmdPackage = new CmdPackage();
            cmdPackage.setDeviceNo(lockBarCode);
            // 消息头 反控
            cmdPackage.setHeader(MiniAppConstants.MESSAGE_HEAD_CONTROL);
            // 消息类型 关锁
            cmdPackage.setType(MiniAppConstants.CONTENT_CONTROL_CLOSE_LOCK);
            Long timesteamp = System.currentTimeMillis() / 1000;
            cmdPackage.setTimestamp(String.valueOf(timesteamp));
            String result2;
//            if(!cmdPackage.getDeviceNo().startsWith("c3")){
//                result2 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//            }else {
                result2 = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//            }
            result.put("code", 200);
            result.put("message", "关闭成功");
//            System.out.println(result2);
        } else {
            result.put("code", 500);
            result.put("message", "关锁失败，未在指定区域内关锁！");
        }

        return result;
    }


    /**
     * 车辆列表查询
     *
     * @return
     */
    @PostMapping(value = "/bikes")
    public ResponseEntity getBikes(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> map = new HashMap<>();
        List<String> jurisdictions = new ArrayList<>();
        jurisdictions.add(requestBody.getDeptId());
        JurisdictionConfiguration jurisdictionConfiguration = jurisdictionConfigurationService.getJurisdictionConfiguration(Long.parseLong(requestBody.getDeptId()));
        List<Bike> bikes = bikeManagerService.queryAllApp(jurisdictions);
        map.put("jurisdictionConfiguration", jurisdictionConfiguration);
        map.put("bikes", bikes);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 查询历史轨迹
     *
     * @param bikeBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/history")
    public ResponseEntity historicalTrack(String bikeBarcode, Long startTime, Long endTime) {
        return new ResponseEntity(bikeManagerService.historicalTrack(bikeBarcode, startTime, endTime), HttpStatus.OK);
    }


}

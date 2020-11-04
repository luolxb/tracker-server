/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.system.rest;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.modules.miniApp.dto.Serie;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.AppointmentService;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.system.dto.*;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.OrderManagerService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import com.nts.iot.modules.system.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * <p>
 * 车辆运行统计
 * </p>
 *
 * @author <a href="mailto:zzm@rnstec.com">zzm</a>
 * @version dls-api 1.0 $ 2017-11-08
 */
@RestController
@RequestMapping("api")
public class BikeStaticController extends JwtBaseController {

    private static final String ENTITY_NAME = "bikeStatic";

    @Autowired
    private DataScope dataScope;

    @Autowired
    private DeptService deptService;

    @Autowired
    private MaUserService maUserService;

    @Autowired
    private BikeManagerService bikeManagerService;

    @Autowired
    private OrderManagerService orderManagerService;

    @Autowired
    private AppointmentService appointmentService;

    @Value("${engineServerUrl}")
    private String engineServerUrl;

    /**
     * 查询
     *
     * @param
     * @param
     * @return
     */
    @GetMapping(value = "/getLoginDeptIds")
    @PreAuthorize("hasAnyRole('ADMIN','STATIC_ALL','STATIC_SELECT')")
    public ResponseEntity getLoginDeptIds(@ModelAttribute("user") User user) {
        List<Map<String, Object>> deptList = new ArrayList<>();
        // 数据权限
        List<Long> ids = new ArrayList(dataScope.getDeptIds());
        for (Long id : ids) {
            Dept dept = deptService.getById(id);
            if (dept != null) {
                Map<String, Object> deta = new HashMap<>();
                deta.put("value", dept.getId());
                deta.put("label", dept.getName());
                deptList.add(deta);
            }
        }
        return new ResponseEntity(deptList, HttpStatus.OK);
    }


    @Log("车辆运行统计")
    @GetMapping(value = "/orderBikeStatics")
    @PreAuthorize("hasAnyRole('ADMIN','STATIC_ALL','STATIC_SELECT')")
    public ResponseEntity orderBikeStatics(@RequestParam("startTime") Long startTime, @RequestParam("endTime") Long endTime, @RequestParam("deptId") String deptId) {
        if (ToolUtil.isOneEmpty(startTime, endTime, deptId)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        // 根据辖区查询出所有用户
        QueryWrapper<MaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", Long.valueOf(deptId));
        List<MaUser> users = maUserService.list(queryWrapper);
        List<OrderBikeQueryDTO> orderBikeQuerys = new ArrayList<>();
        for (MaUser user : users) {
            OrderBikeQueryDTO queryDTO = new OrderBikeQueryDTO();
            queryDTO.setUserId(String.valueOf(user.getId()));
            queryDTO.setUserName(user.getName());
            // 根据  用户id 和  开始还有结束时间查出订单编号
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", user.getId());
            // 开始时间 小于 创建时间
            wrapper.ge("create_time", startTime);
            // 结束 大于 创建时间
            wrapper.le("create_time", endTime);
            List<Order> orders = orderManagerService.list(wrapper);
            List<Long> orderIds = new ArrayList<>();
            for (Order order : orders) {
                orderIds.add(Long.valueOf(order.getOrderId()));
            }
            queryDTO.setOrderIds(orderIds);
            orderBikeQuerys.add(queryDTO);
        }
        String result1 = HttpRequest.post(engineServerUrl + "/order/getBikeStateByIds").body(JSON.toJSONString(orderBikeQuerys)).execute().body();
        List<OrderBikeStaticDTO> orderBikeStaticDTOS = JSON.parseArray(result1, OrderBikeStaticDTO.class);
        /* 组装成echarts 类型 */
        Map<String, Object> result = new HashMap<>();
        List<String> xAxis = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        legend.add("总行驶里程");
        legend.add("平均骑行速度");
        legend.add("总骑行时间");
        // 总行驶里程
        List<String> data1 = new ArrayList<>();
        // 平均骑行速度
        List<String> data2 = new ArrayList<>();
        // 总骑行时间
        List<String> data3 = new ArrayList<>();
        for (OrderBikeStaticDTO bikeStaticDTO : orderBikeStaticDTOS) {
            // x轴显示label
            xAxis.add(bikeStaticDTO.getUserName());
            // 总行驶里程
            data1.add(String.valueOf(bikeStaticDTO.getTotalDistance()));
            // 平均骑行速度
            data2.add(String.valueOf(bikeStaticDTO.getAverageSpeed()));
            // 总骑行时间
            data3.add(String.valueOf(bikeStaticDTO.getTotalRunTime()));
        }
        Serie serie1 = new Serie();
        serie1.setType("bar");
        serie1.setName("总行驶里程");
        serie1.setData(data1);

        Serie serie2 = new Serie();
        serie2.setType("bar");
        serie2.setName("平均骑行速度");
        serie2.setData(data2);

        Serie serie3 = new Serie();
        serie3.setType("bar");
        serie3.setName("总骑行时间");
        serie3.setData(data3);

        List<Serie> series = new ArrayList<>();
        series.add(serie1);
        series.add(serie2);
        series.add(serie3);

        result.put("legend", legend);
        result.put("series", series);
        result.put("xAxis", xAxis);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @Log("车辆运行统计")
    @GetMapping(value = "/bikeStatics")
    @PreAuthorize("hasAnyRole('ADMIN','STATIC_ALL','STATIC_SELECT')")
    public ResponseEntity bikeStatics(@RequestParam("startTime") Long startTime, @RequestParam("endTime") Long endTime, @RequestParam("deptId") String deptId) {
        if (ToolUtil.isOneEmpty(startTime, endTime, deptId)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        // 根据辖区查询出辖区下的所有车辆
        QueryWrapper<Bike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jurisdiction_id", Long.valueOf(deptId));
        List<Bike> bikes = bikeManagerService.list(queryWrapper);
        List<String> bikeNumberList = new ArrayList<>();
        bikes.forEach(p -> {
            bikeNumberList.add(p.getBikeBarcode());
        });
        BikeStateQueryDTO query = new BikeStateQueryDTO();
        query.setStartTime(startTime);
        query.setEndTime(endTime);
        query.setIds(bikeNumberList);
        String result1 = HttpRequest.post(engineServerUrl + "/findBikeStaticList").body(JSON.toJSONString(query)).execute().body();
        List<BikeStaticDto> bikeStaticDtos = JSON.parseArray(result1, BikeStaticDto.class);
        /* 组装成echarts 类型 */
        Map<String, Object> result = new HashMap<>();
        List<String> xAxis = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        legend.add("总行驶里程");
        legend.add("平均骑行速度");
        legend.add("总骑行时间");
        // 总行驶里程
        List<String> data1 = new ArrayList<>();
        // 平均骑行速度
        List<String> data2 = new ArrayList<>();
        // 总骑行时间
        List<String> data3 = new ArrayList<>();
        for (BikeStaticDto bikeStaticDto : bikeStaticDtos) {
            // x轴显示label
            xAxis.add(bikeStaticDto.getBikeNumber());
            // 总行驶里程
            data1.add(String.valueOf(bikeStaticDto.getTotalDistance()));
            // 平均骑行速度
            data2.add(String.valueOf(bikeStaticDto.getAverageSpeed()));
            // 总骑行时间
            data3.add(String.valueOf(bikeStaticDto.getTotalRunTime()));
        }

        Serie serie1 = new Serie();
        serie1.setType("bar");
        serie1.setName("总行驶里程");
        serie1.setData(data1);

        Serie serie2 = new Serie();
        serie2.setType("bar");
        serie2.setName("平均骑行速度");
        serie2.setData(data2);

        Serie serie3 = new Serie();
        serie3.setType("bar");
        serie3.setName("总骑行时间");
        serie3.setData(data3);

        List<Serie> series = new ArrayList<>();
        series.add(serie1);
        series.add(serie2);
        series.add(serie3);

        result.put("legend", legend);
        result.put("series", series);
        result.put("xAxis", xAxis);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    /**
     * 预约登记统计功能，根据辖区按天、按月统计具体的人口预约人数等
     *
     * @param startTime
     * @param endTime
     * @param deptId
     * @return
     */
    @Log("预约登记统计")
    @GetMapping(value = "/appointmentStatics")
    @PreAuthorize("hasAnyRole('ADMIN','STATIC_ALL','STATIC_SELECT')")
    public ResponseEntity appointmentStatics(@RequestParam("startTime") Long startTime,
                                             @RequestParam("endTime") Long endTime,
                                             @RequestParam("deptId") String deptId,
                                             @RequestParam("type") String type) {
        if (ToolUtil.isOneEmpty(startTime, endTime, deptId, type)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        List<AppointmentStaticDTO> appointmentStatic = appointmentService.appointmentStatic(startTime, endTime, type, deptId);
        Collections.reverse(appointmentStatic);
        /* 组装成echarts 类型 */
        Map<String, Object> result = new HashMap<>();
        List<String> xAxis = new ArrayList<>();

        List<Integer> data = new ArrayList<>();
        appointmentStatic.forEach(s -> {
            xAxis.add(s.getDt());
            data.add(s.getTotalNum());
        });

        result.put("data", data);
        result.put("xAxis", xAxis);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    private List<String> getDept(User user) {
        List<String> jurisdictions = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (Dept dept : deptList) {
            jurisdictions.add(String.valueOf(dept.getId()));
        }
        return jurisdictions;
    }

}

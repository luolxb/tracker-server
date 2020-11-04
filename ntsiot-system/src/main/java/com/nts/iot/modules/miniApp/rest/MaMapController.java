/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.rest;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import com.nts.iot.modules.miniApp.dto.RidingTrack;
import com.nts.iot.modules.miniApp.dto.SecurityDto;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.CheckPoint;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.VirtualPile;
import com.nts.iot.modules.system.service.*;

import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.nts.iot.constant.RedisKey.CHECK_POINT;


/**
 * <p>
 * </p>
 * 小程序位置、轨迹 相关接口
 *
 * @author <a href="mailto:zzm@rnstec.com">zzm</a>
 * @version dls-api 1.0 $ 2017-11-08
 */
@Slf4j
@RestController
@RequestMapping("ma")
public class MaMapController {

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
    private CheckPointService checkPointService;

    @Autowired
    private VirtualPileService virtualPileService;

    @Autowired
    private MessageService messageService;

    @Value("${engineServerUrl}")
    private String engineServerUrl;


    /**
     * 获取必到点
     *
     * @param requestBody
     * @return
     */
    @PostMapping("/getCheckPointList")
    public List<Map<String, Object>> getCheckPointList(@RequestBody MaRequestBody requestBody) {
        String jurisdiction = requestBody.getDeptId();
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (jurisdiction == null) {
            System.out.println("jurisdiction为NULL");
            return resultList;
        }
        String points = redisUtil.getData(CHECK_POINT + jurisdiction);
        if (points != null && !"".equals(points)) {
            List<CheckPoint> checkpoints = JsonUtil.jsonConvertList(points, CheckPoint.class);
            for (CheckPoint checkPoint : checkpoints) {
                Map<String, Object> point = new HashMap<>();
                // 经度
                String longitude = checkPoint.getLongitude();
                // 纬度
                String latitude = checkPoint.getLatitude();
                if (latitude != null && longitude != null) {
                    point.put("longitude", longitude);
                    point.put("latitude", latitude);
                    resultList.add(point);
                }
            }
        }
        return resultList;
    }


    /***
     * 调取engine 接口 获取附近的车辆
     * @param requestBody
     * @return
     */
    @PostMapping("/getNearbyBikes")
    public List<Map<String, Object>> getMarker(@RequestBody MaRequestBody requestBody) {
        List<Map<String, Object>> markers = new ArrayList<>();
        // 经度
        String longitude = requestBody.getLongitude();
        // 纬度
        String latitude = requestBody.getLatitude();
        // TODO 获取失败
        String result2 = HttpUtil.get(engineServerUrl + "/findBike/" + longitude + "," + latitude);
        List<CollectMessage> collectMessages;
        try {
            collectMessages = JSON.parseArray(result2, CollectMessage.class);
        } catch (Exception ex) {
            log.error("getMarker 解析JSON数据异常： '{}'", result2);
            throw ex;
        }
        for (CollectMessage collectMessage : collectMessages) {
            Map<String, Object> point = new HashMap<>();
            point.put("longitude", collectMessage.getLongitude());
            point.put("latitude", collectMessage.getLatitude());
            markers.add(point);
        }
//        System.out.println(markers);
        return markers;
    }


    /***
     * 调取engine 接口 通过辖区 获取 围栏
     * @param requestBody
     * @return
     */
    @PostMapping("/getElectronicFenc")
    public String getElectronicFenc(@RequestBody MaRequestBody requestBody) {
        List<Map<String, Object>> points = new ArrayList<>();
        // 辖区
        String jurisdiction = requestBody.getDeptId();
        String result2 = HttpUtil.get(engineServerUrl + "/findElectronicFenc/" + jurisdiction);
//        System.out.println(result2);
        return result2;
    }

    // 根据订单List获得订单轨迹
    @PostMapping("/getTrajectory")
    public String getTrajectory(@RequestBody MaRequestBody requestBody) {
        // 辖区
        String ids = requestBody.getOrderIds();
        String result = HttpRequest.post(engineServerUrl + "/order/findAllByIdIn")
                .body(ids)
                .execute().body();
//        System.out.println(result);
        return result;
    }


    /**
     * 根据开始和结束时间和车获得轨迹
     *
     * @param requestBody
     * @return
     */
    @PostMapping("/getTrajectoryByTime")
    public Map<String, Object> getTrajectoryByTime(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String bikeBarcode = requestBody.getBikeBarcode();
        String startTime = requestBody.getStartTime();
        String endTime = requestBody.getEndTime();
        String userId = requestBody.getUserId();
        if (ToolUtil.isOneEmpty(bikeBarcode, startTime, endTime, userId)) {
            result.put("code", 500);
            result.put("message", "查询参数有误");
            return result;
        } else {
            /* 判断该车辆是否存在 */
            Bike bike = bikeManagerService.getBikeByBarcode(bikeBarcode);
            if (bike == null) {
                result.put("code", 500);
                result.put("message", "您所查询的车辆信息不存在");
                return result;
            }
            /* 判断该用户是否存在 */
            MaUser maUser = userService.getById(Long.valueOf(userId));
            if (maUser == null) {
                result.put("code", 500);
                result.put("message", "您当前无权限查看此车辆数据信息");
                return result;
            }
            /* 判断该用户是否在这个辖区 */
            if (bike.getJurisdiction() != null && !bike.getJurisdiction().equals(maUser.getDeptId())) {
                result.put("code", 500);
                result.put("message", "您当前无权限查看此车辆数据信息");
                return result;
            }
            Date startData = DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
            Date endData = DateUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss");
            RidingTrack ridingTrack = bikeManagerService.historicalTrack(bikeBarcode, startData.getTime(), endData.getTime());
            List<Map<String, Object>> trajectoryList = ridingTrack.getTrajectoryList();
            if (trajectoryList == null || trajectoryList.size() == 0) {
                result.put("code", 500);
                result.put("message", "当前时间段无对应行驶轨迹");
            } else {
                if (trajectoryList.size() > 11650) {
                    /* 判断，一旦数量超过 11650（地图可现实坐标最大数量），就每隔一个点减去一个，递归减到达到数量要求为止 */
                    trajectoryList = slimData(trajectoryList);
                }
                result.put("trajectoryList", trajectoryList);
                result.put("code", 200);
                result.put("message", "查询成功");
            }

        }
        return result;
    }


    // 获取前一天的
    @PostMapping("/findSecurity")
    public Map<String, Object> findSecurity(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> map = new HashMap<>();
        // 辖区
        String jurisdiction = requestBody.getDeptId();
        String result = HttpUtil.get(engineServerUrl + "/findSecurity/" + jurisdiction);
        if (result == null) {
            return null;
        }
        SecurityDto securityDto = JSON.parseObject(result, SecurityDto.class);
        if (securityDto == null) {
            return null;
        }
        // 巡逻时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 开始
        if (securityDto.getBeginTime() != null) {
            Date bTime = new Date(Long.valueOf(securityDto.getBeginTime()));
            map.put("startTime", formatter.format(bTime));
        } else {
            map.put("startTime", "");
        }

        // 结束
        if (securityDto.getEndTime() != null) {
            Date eTime = new Date(Long.valueOf(securityDto.getEndTime()));
            map.put("endTime", formatter.format(eTime));
        } else {
            map.put("endTime", "");
        }

        // 巡逻信息
        List<Map<String, Object>> bikeOrderList = new ArrayList<>();
        if (securityDto.getMap() != null && securityDto.getMap().size() > 0) {
            for (Map.Entry<String, List<String>> entry : securityDto.getMap().entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                // 车辆编号
                Map<String, Object> map1 = new HashMap<>();
                String bikeBarcode = entry.getKey();
                //map1.put("no", bikeBarcode);
                Bike bike = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.VECHILE_KEY + bikeBarcode), Bike.class);
                Dept dept = null;
                if (bike != null) {
                    dept = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.JURISDICTION_KEY + bike.getJurisdiction()), Dept.class);
                }
                String deptName = "";
                if (dept != null) {
                    deptName = dept.getName();
                }
                StringBuilder showNo = new StringBuilder();
                showNo.append(bikeBarcode.substring(5));
                if (deptName != null && !"".equals(deptName)) {
                    showNo.append("(");
                    showNo.append(deptName);
                    showNo.append(")");
                }
                map1.put("no", showNo.toString());
                map1.put("ids", JSON.toJSONString(entry.getValue()));
                bikeOrderList.add(map1);
            }
        }
        map.put("list", bikeOrderList);
        // 累计巡逻里程
        map.put("patrolMileage", securityDto.getDistance());
        // 累计停留时长
        map.put("patrolTimeLength", securityDto.getStopTime());
        return map;
    }

    /**
     * 获得指定辖区下的所有必到点
     *
     * @param jurisdiction
     * @return
     */
    @GetMapping("/getCheckPoint")
    public List<CheckPoint> getCheckPoint(@RequestParam(required = false) String jurisdiction) {
        QueryWrapper<CheckPoint> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jurisdiction", jurisdiction);
        return checkPointService.list(queryWrapper);
    }

    /**
     * 获得指定辖区下的所有虚拟装
     *
     * @param jurisdiction
     * @return
     */
    @GetMapping("/getVirtualPile")
    public List<VirtualPile> getVirtualPile(@RequestParam(required = false) String jurisdiction) {
        QueryWrapper<VirtualPile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jurisdiction", jurisdiction);
        return virtualPileService.list(queryWrapper);
    }

    // 一旦数量超过 11650（地图可现实坐标最大数量），就每隔一个点减去一个，递归减到达到数量要求为止
    private static List<Map<String, Object>> slimData(List<Map<String, Object>> dataList) {
        if (dataList.size() > 11650) {
            List<Map<String, Object>> newData = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                if ((i & 1) == 1) {
                    newData.add(dataList.get(i));
                }
            }
            return slimData(newData);
        }
        return dataList;
    }

}

/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.rest;


import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import com.nts.iot.modules.system.dto.MissionDto;
import com.nts.iot.modules.system.model.RealTask;
import com.nts.iot.modules.system.service.*;

import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nts.iot.constant.ConstantClass.ORDER_STATUS_02;
import static com.nts.iot.constant.MiniAppConstants.MISSION_ON;
import static com.nts.iot.constant.MiniAppConstants.MISSION_SUCCESS;


/**
 * <p>
 * </p>
 * 小程序任务列表接口
 *
 * @author <a href="mailto:zzm@rnstec.com">zzm</a>
 * @version cyoubike 1.0 $ 2019-11-08
 */
@RestController
@RequestMapping("ma")
public class MaMissionController {


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RealTaskService realTaskService;

    @Autowired
    RealCheckPointTaskService subTaskService;

    @Autowired
    MaUserService maUserService;

    @Autowired
    OrderManagerService orderService;

    @Autowired
    BikeManagerService bikeService;

    @Autowired
    LockService lockService;

    /**
     * 根据用户id 获取任务列表
     *
     * @return Result Result
     */
    @PostMapping("getMissionList")
    public Map<String, Object> getMissionList(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        if (ToolUtil.isOneEmpty(requestBody.getUserId())) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        List<MissionDto> missionDto = realTaskService.getTaskDtoByUserId(requestBody.getUserId());
        result.put("missions", missionDto);
        result.put("code", 200);
        result.put("message", "获取成功");
        return result;
    }

    @PostMapping("openTask")
    public Map<String, Object> openTask(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        if (ToolUtil.isOneEmpty(requestBody.getUserId(),requestBody.getTaskId())) {
            result.put("code", 500);
            result.put("message", "开启失败");
        }else {
            RealTask realTask = realTaskService.getById(Long.valueOf(requestBody.getTaskId()));
            if (realTask == null) {
                result.put("code", 500);
                result.put("message", "没有找到该任务");
            }else {
                /* 通过user Id 查询车辆 编号 */
                String lockBarcode = lockService.getLockBarcodeByUserId(requestBody.getUserId());
                realTask.setLockBarcode(lockBarcode);
                realTask.setStatus(MISSION_ON);
                realTask.setBeginTime(System.currentTimeMillis());
                realTaskService.updateById(realTask);
                /* 重新查询 */
                List<MissionDto> missionDto = realTaskService.getTaskDtoByUserId(requestBody.getUserId());
                result.put("missions", missionDto);
                result.put("code", 200);
                result.put("message", "开启成功");
            }
        }
        return result;
    }


    @PostMapping("closeTask")
    public Map<String, Object> closeTask(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        if (ToolUtil.isOneEmpty(requestBody.getUserId(),requestBody.getTaskId())) {
            result.put("code", 500);
            result.put("message", "关闭任务失败");
        }else {
            RealTask realTask = realTaskService.getById(Long.valueOf(requestBody.getTaskId()));
            if (realTask == null) {
                result.put("code", 500);
                result.put("message", "找不到该任务");
            }else {
                if (realTask.getLockBarcode() == null) {
                    /* 通过user Id 查询车辆 编号 */
                    String lockBarcode = lockService.getLockBarcodeByUserId(requestBody.getUserId());
                    realTask.setLockBarcode(lockBarcode);
                }
                realTask.setOverTime(System.currentTimeMillis());
                realTask.setStatus(MISSION_SUCCESS);
                realTaskService.updateById(realTask);
                /* 重新查询 */
                List<MissionDto> missionDto = realTaskService.getTaskDtoByUserId(requestBody.getUserId());
                result.put("missions", missionDto);
                result.put("code", 200);
                result.put("message", "任务关闭成功");
            }
        }
        return result;
    }

    @PostMapping("saveTaskInfo")
    public Map<String, Object> saveTaskInfo(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        if (ToolUtil.isOneEmpty(requestBody.getSubTaskId(),requestBody.getSourceData())) {
            result.put("code", 500);
            result.put("message", "保存失败");
        }else {
            if (requestBody.getLongitude() == null || requestBody.getLatitude() == null) {
                result.put("code",500);
                result.put("message","定位获取失败，请重新获取");
            }else {
                // 保存
                result = subTaskService.saveSubTask(requestBody);
                /* 重新查询 */
                List<MissionDto> missionDto = realTaskService.getTaskDtoByUserId(requestBody.getUserId());
                result.put("missions", missionDto);
            }
        }
        return result;
    }

    @PostMapping("toClockOn")
    public Map<String, Object> toClockOn(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        if (ToolUtil.isOneEmpty(requestBody.getSubTaskId())) {
            result.put("code", 500);
            result.put("message", "保存失败");
        }else {
            if (requestBody.getLongitude() == null || requestBody.getLatitude() == null) {
                result.put("code",500);
                result.put("message","定位获取失败，请重新获取");
            }else {
                // 保存
                result = subTaskService.toClockOn(requestBody);
                /* 重新查询 */
                List<MissionDto> missionDto = realTaskService.getTaskDtoByUserId(requestBody.getUserId());
                result.put("missions", missionDto);
            }
        }
        return result;
    }


}

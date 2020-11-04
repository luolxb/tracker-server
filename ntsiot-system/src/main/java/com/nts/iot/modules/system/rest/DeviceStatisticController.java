package com.nts.iot.modules.system.rest;

import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.modules.system.dto.DeviceStateQueryDTO;
import com.nts.iot.modules.system.dto.DeviceStatisticDto;
import com.nts.iot.modules.system.model.vo.DeviceRouteVo;
import com.nts.iot.modules.system.model.vo.OverSpeedAlarmVo;
import com.nts.iot.modules.system.service.DeviceService;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.rest
 * @program: ntsiot
 * @author: ruosen
 * @create: 2020-05-29 17:21
 **/
@Api(tags = "设备报表统计")
@RestController
@RequestMapping("/api")
@Slf4j
public class DeviceStatisticController extends JwtBaseController {

    @Autowired
    private DeviceService deviceService;


    @Log("根据车辆编号，查询历史轨迹")
    @ApiOperation("根据车辆编号，查询历史轨迹")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/historicalRoute")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "搜索开始时间", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "搜索结束时间", required = true, dataType = "Long", paramType = "query")
    })
    public RestResponse deviceHistoricalRoute(@RequestParam("deviceId") Long deviceId,
                                              @RequestParam("startTime") Long startTime,
                                              @RequestParam("endTime") Long endTime) {

        List<DeviceRouteVo> deviceRouteVos = deviceService.deviceHistoricalRoute(deviceId, startTime, endTime);
        return RestResponse.success(deviceRouteVos);
    }


    @Log("轨迹报表")
    @ApiOperation("轨迹报表")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/statistic/route")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "deviceNo", value = "设备编码", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "搜索开始时间", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "搜索结束时间", required = true, dataType = "Long", paramType = "query")
    })
    public RestResponse deviceStatisticRoute(String deviceNo,
                                             @RequestParam("userId") Long userId,
                                             @RequestParam("startTime") Long startTime,
                                             @RequestParam("endTime") Long endTime) {

        List<DeviceRouteVo> deviceRouteVos = deviceService.deviceStatisticRoute(userId, deviceNo, startTime, endTime);
        return RestResponse.success(deviceRouteVos);
    }


    @Log("查询统计信息")
    @ApiOperation("查询统计信息")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/findDeviceStatistic")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceNo", value = "设备编号", dataType = "String", paramType = "query")
    })
    public RestResponse findDeviceStatistic(@RequestParam("userId") Long userId,
                                            @RequestParam("startTime") Long startTime,
                                            @RequestParam("endTime") Long endTime,
                                            String deviceNo) {
        if (userId == null) {
            return RestResponse.error("用户ID不能为空,User ID cannot be empty");
        }

        List<DeviceStatisticDto> deviceStatisticDtoList = deviceService.findDeviceStatistic(userId, startTime, endTime, deviceNo);
        return RestResponse.success(deviceStatisticDtoList);
    }

    @Log("超速报警")
    @ApiOperation("超速报警")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/overSpeedAlarm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceNo", value = "设备编号", dataType = "String", paramType = "query", required = true)
    })
    public RestResponse overSpeedAlarm(@RequestParam("userId") Long userId,
                                       @RequestParam("startTime") Long startTime,
                                       @RequestParam("endTime") Long endTime,
                                       @RequestParam("deviceNo") String deviceNo) {

        List<OverSpeedAlarmVo> overSpeedAlarmVoList = deviceService.overSpeedAlarm(userId, startTime, endTime, deviceNo);
        return RestResponse.success(overSpeedAlarmVoList);
    }


}

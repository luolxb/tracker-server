package com.nts.iot.modules.system.rest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.system.model.DeviceAlert;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceAlertStatisticsVo;
import com.nts.iot.modules.system.model.vo.DeviceRealAlertVo;
import com.nts.iot.modules.system.service.DeviceAlertService;
import com.nts.iot.modules.system.service.DeviceRealAlertService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "设备报警")
@RestController
@RequestMapping("/api")
@Slf4j
public class DeviceAlertController extends JwtBaseController {

    @Autowired
    private DeviceAlertService deviceAlertService;

    @Autowired
    private DeviceRealAlertService deviceRealAlertService;

    @PostMapping("/alert/create")
    @Log("新增报警")
    @ApiOperation("新增报警")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public RestResponse create(@RequestBody List<CollectMessage> collectMessages,
                               @ApiIgnore @ModelAttribute("user") User user) {
        deviceAlertService.create(collectMessages, user);
        return RestResponse.success();
    }

    @GetMapping("/alert/queryPage")
    @Log("报警分页")
    @ApiOperation("报警分页")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceNo", value = "设备编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "String", paramType = "query"),
    })
    public RestResponse queryPage(@RequestParam("userId") Long userId,
                                  String deviceNo,
                                  String startDate,
                                  String endDate,
                                  Pageable pageable) {
        Page<DeviceAlert> deviceAlertPage = deviceAlertService.queryPage(pageable, userId, deviceNo, startDate, endDate, 1);
        return RestResponse.success(deviceAlertPage);
    }

    @GetMapping("/alert/queryPageDetail")
    @Log("报警统计/报警详单")
    @ApiOperation("报警统计/【报警详单】")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceNo", value = "设备编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "String", paramType = "query"),
    })
    public RestResponse queryPageDetail(@RequestParam("userId") Long userId,
                                        String deviceNo,
                                        String startDate,
                                        String endDate,
                                        Pageable pageable) {
        Page<DeviceAlert> deviceAlertPage = deviceAlertService.queryPageDetail(pageable, userId, deviceNo, startDate, endDate);
        return RestResponse.success(deviceAlertPage);
    }

    @PutMapping("/alert/update")
    @Log("修改为已读")
    @ApiOperation("修改为已读")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    @ApiImplicitParam(name = "deviceAlertList", value = "报警数组", dataType = "Array", required = true)
    public RestResponse update(@ApiIgnore @ModelAttribute("user") User user,
                               @RequestBody String deviceAlertList) {
        JSONObject parse = (JSONObject) JSONObject.parse(deviceAlertList);
        if (CollectionUtils.isEmpty((List<DeviceAlert>) parse.get("deviceAlertList"))) {
            throw new BadRequestException("报警ID不能为空");
        }
        List<DeviceAlert> deviceAlerts = JsonUtil.jsonConvertList(JsonUtil.getJson(parse.get("deviceAlertList")), DeviceAlert.class);
        deviceAlertService.updateDeviceAlert(deviceAlerts, user);
        return RestResponse.success();
    }

    @GetMapping("/alert/statistics")
    @Log("报警统计【统计报表】")
    @ApiOperation("报警统计【统计报表】")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceNo", value = "设备号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "String", paramType = "query")
    })
    public RestResponse statistics(@RequestParam Long userId,
                                   String deviceNo,
                                   String startDate,
                                   String endDate) {
        List<DeviceAlertStatisticsVo> statistics = deviceAlertService.statistics(userId, deviceNo, startDate, endDate);
        return RestResponse.success(statistics);
    }


    @GetMapping("/alert/type/list")
    @Log("报警类型列表")
    @ApiOperation("报警类型列表")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", paramType = "query", required = true),
            @ApiImplicitParam(name = "alertType", value = "报警类型", dataType = "String", paramType = "query", required = true)
    })
    public RestResponse alertTypeList(@RequestParam Long userId,
                                      @RequestParam String alertType,
                                      Pageable pageable) {
        Page<DeviceRealAlertVo> statistics = deviceRealAlertService.proportionList(userId, alertType,pageable);
        return RestResponse.success(statistics);
    }


}

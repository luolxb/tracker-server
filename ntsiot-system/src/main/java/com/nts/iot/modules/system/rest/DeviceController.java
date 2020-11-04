package com.nts.iot.modules.system.rest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.*;
import com.nts.iot.modules.system.service.DeviceService;
import com.nts.iot.util.ExcelUtil;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.rest
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:22
 **/
@Api(tags = "设备管理")
@RestController
@RequestMapping("/api")
@Slf4j
public class DeviceController extends JwtBaseController {

    @Autowired
    private DeviceService deviceService;

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("设备列表")
    @PostMapping("/queryPage")
    @ApiOperation("设备列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userExpiresTimeStart", value = "用户过期时间(开始时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userExpiresTimeEnd", value = "用户过期时间(结束时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformExpiresTimeStart", value = "平台过期时间(开始时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformExpiresTimeEnd", value = "平台过期时间(结束时间)", dataType = "String", paramType = "query")
    })
    public RestResponse queryPage(String search,
                                  String userExpiresTimeStart,
                                  String userExpiresTimeEnd,
                                  String platformExpiresTimeStart,
                                  String platformExpiresTimeEnd,
                                  @RequestParam Long userId,
                                  Pageable pageable) {
        Page<DeviceVo> deviceVoPage = deviceService.queryPage(search, userExpiresTimeStart, userExpiresTimeEnd, platformExpiresTimeStart, platformExpiresTimeEnd, pageable, userId);
        return RestResponse.success(deviceVoPage);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("设备列表到期设备")
    @PostMapping("/queryPageExpires")
    @ApiOperation("设备列表到期设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userExpiresTimeStart", value = "用户过期时间(开始时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userExpiresTimeEnd", value = "用户过期时间(结束时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformExpiresTimeStart", value = "平台过期时间(开始时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformExpiresTimeEnd", value = "平台过期时间(结束时间)", dataType = "String", paramType = "query")
    })
    public RestResponse queryPageExpires(String search,
                                  String userExpiresTimeStart,
                                  String userExpiresTimeEnd,
                                  String platformExpiresTimeStart,
                                  String platformExpiresTimeEnd,
                                  @RequestParam Long userId,
                                  Pageable pageable) {
        Page<DeviceVo> deviceVoPage = deviceService.queryPageExpires(search, userExpiresTimeStart, userExpiresTimeEnd, platformExpiresTimeStart, platformExpiresTimeEnd, pageable, userId);
        return RestResponse.success(deviceVoPage);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("设备编号列表")
    @PostMapping("/queryPage/deviceNo")
    @ApiOperation("设备编号列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "搜索条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query")
    })
    public RestResponse queryPage(@RequestParam Long userId,
                                  String search) {
        List<String> deviceVoNoPage = deviceService.queryPageDeviceNo(search,userId);
        return RestResponse.success(deviceVoNoPage);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("账户设备【定位监控】")
    @GetMapping("/queryAll")
    @ApiOperation("账户设备【定位监控】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索条件", dataType = "String", paramType = "query"),
    })
    public RestResponse queryAll(String search,
                                 @RequestParam Long userId) {
        DeviceMonitorVo deviceMonitorVo = deviceService.queryAll(search, userId);
        return RestResponse.success(deviceMonitorVo);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("新增设备")
    @PostMapping("/device")
    @ApiOperation("新增设备")
    public RestResponse create(@Valid @RequestBody DeviceRq deviceRq,
                               BindingResult result,
                               @ApiIgnore @ModelAttribute("user") User user) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getFieldError().getDefaultMessage());
        }
        deviceService.create(deviceRq, user);
        return RestResponse.success();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("设备详情")
    @GetMapping("/device/{id}")
    @ApiImplicitParam(name = "id", dataType = "Long", value = "设备ID", paramType = "path")
    @ApiOperation("设备详情")
    public RestResponse detail(@PathVariable Long id) {
        DeviceVo deviceVo = deviceService.detail(id);
        return RestResponse.success(deviceVo);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("修改设备")
    @PutMapping("/device")
    @ApiOperation("修改设备")
    public RestResponse update(@RequestBody DeviceRq deviceRq,
                               @ApiIgnore @ModelAttribute("user") User user) {
        deviceService.update(deviceRq, user);
        return RestResponse.success();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("销售")
    @PutMapping("/device/sell")
    @ApiOperation("销售")
    public RestResponse sell(@RequestBody List<DeviceRq> deviceRqList,
                             @ApiIgnore @ModelAttribute("user") User user) {
        deviceService.sell(deviceRqList, user);
        return RestResponse.success();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("重置设备密码")
    @PutMapping("/device/resetPass/{deviceId}")
    @ApiOperation("重置设备密码")
    @ApiImplicitParam(name = "deviceId", value = "设备id", dataType = "Long", paramType = "path")
    public RestResponse resetPass(@PathVariable("deviceId") Long id,
                                  @ApiIgnore @ModelAttribute("user") User user) {
        deviceService.resetPass(user, id);
        return RestResponse.success();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("修改型号")
    @PutMapping("/device/updateType")
    @ApiOperation("修改型号")
    public RestResponse updateType(@RequestBody List<DeviceRq> deviceRqList,
                                   @ApiIgnore @ModelAttribute("user") User user) {
        deviceService.updateType(deviceRqList, user);
        return RestResponse.success();
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("删除设备")
    @PostMapping("/device/delete")
    @ApiOperation("删除设备")
    public RestResponse delete(@RequestBody String ids,
                               @ApiIgnore @ModelAttribute("user") User user) {
        if (StringUtils.isBlank(ids)) {
            return RestResponse.error("设备ID不能为空");
        }
        JSONObject parse = (JSONObject) JSONObject.parse(ids);
        List<Integer> dataList = (List<Integer>) parse.get("ids");
        if (CollectionUtils.isEmpty(dataList)) {
            return RestResponse.error("设备ID不能为空");
        }
        List<Long> longs = new ArrayList<>();
        dataList.forEach(data -> {
            Long id = Long.parseLong(String.valueOf(data));
            longs.add(id);
        });

        deviceService.delete(longs, user);
        return RestResponse.success();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("导出设备过期")
    @GetMapping("/device/export")
    @ApiOperation("导出设备过期")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userExpiresTimeStart", value = "用户过期时间(开始时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userExpiresTimeEnd", value = "用户过期时间(结束时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformExpiresTimeStart", value = "平台过期时间(开始时间)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platformExpiresTimeEnd", value = "平台过期时间(结束时间)", dataType = "String", paramType = "query")
    })
    public RestResponse export(String search,
                               String userExpiresTimeStart,
                               String userExpiresTimeEnd,
                               String platformExpiresTimeStart,
                               String platformExpiresTimeEnd,
                               Long userId) {
        return deviceService.export(search, userExpiresTimeStart, userExpiresTimeEnd, platformExpiresTimeStart, platformExpiresTimeEnd, userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("延期")
    @PutMapping("/device/extension")
    @ApiOperation("延期")
    public RestResponse extension(@RequestBody List<DeviceRq> deviceRqList,
                                  @ApiIgnore @ModelAttribute("user") User user) {
        deviceService.extension(deviceRqList, user);
        return RestResponse.success();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("延期用户")
    @PutMapping("/device/userExtension")
    @ApiOperation("延期用户")
    public RestResponse userExtension(@RequestBody List<DeviceRq> deviceRqList,
                                      @ApiIgnore @ModelAttribute("user") User user) {
        deviceService.userExtension(deviceRqList, user);
        return RestResponse.success();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("离线设备列表")
    @PostMapping("/queryPage/offLine")
    @ApiOperation("离线设备列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "Integer", paramType = "query")
    })
    public RestResponse queryPageOffLine(String search,
                                         String startDate,
                                         String endDate,
                                         @RequestParam Long userId,
                                         Pageable pageable) {
        Page<DeviceVo> deviceVoPage = deviceService.queryPageOffLine(search, startDate, endDate, pageable, userId);
        return RestResponse.success(deviceVoPage);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("离线设备导出")
    @PostMapping("/export/offLine")
    @ApiOperation("离线设备导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "Integer", paramType = "query")
    })
    public RestResponse exportOffLine(String search,
                                      String startDate,
                                      String endDate,
                                      @RequestParam Long userId,
                                      Pageable pageable) {
        Page<DeviceVo> deviceVoPage = deviceService.queryPageOffLine(search, startDate, endDate, pageable, userId);
        ExcelUtil<DeviceVo> excelUtil = new ExcelUtil<>(DeviceVo.class);
        return excelUtil.exportExcel(deviceVoPage.getRecords(), "离线设备");
    }


    @Log("下载设备导入模板")
    @ApiOperation("下载设备导入模板")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/downTemplate")
    public RestResponse downTemplate() {
        ExcelUtil<DeviceDownVo> excelUtil = new ExcelUtil<>(DeviceDownVo.class);
        return excelUtil.exportExcel(new ArrayList<DeviceDownVo>(), "设备导入模板");
    }

    @Log("设备导入")
    @ApiOperation("设备导入")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @PostMapping(value = "/device/import")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query")
    public RestResponse importDevice(MultipartFile file,
                                     @RequestParam Long userId,
                                     @ApiIgnore @ModelAttribute("user") User user) throws Exception {
        Integer integer = deviceService.importDevice(file, userId, user);
        return RestResponse.success(integer);
    }

    @Log("导入激活码")
    @ApiOperation("导入激活码")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @PostMapping(value = "/device/import/code")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "query")
    public RestResponse importActivationCode(MultipartFile file,
                                             @RequestParam Long userId,
                                             @ApiIgnore @ModelAttribute("user") User user) throws Exception {
        Integer integer = deviceService.importActivationCode(file, userId, user);
        return RestResponse.success(integer);
    }

    @Log("设备的位置")
    @ApiOperation("设备的位置")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/location/{userId}")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Long", paramType = "path")
    public RestResponse deviceLocation(@PathVariable Long userId,
                                       @ApiIgnore @ModelAttribute("user") User user) {
        List<DeviceLocationInfoVo> deviceLocationInfoVoList = deviceService.deviceLocation(null, userId);
        return RestResponse.success(deviceLocationInfoVoList);
    }




    @Log("更多/监控")
    @ApiOperation("更多/监控")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/monitoring/{deviceId}")
    @ApiImplicitParam(name = "deviceId", value = "设备id", required = true, dataType = "Long", paramType = "path")
    public RestResponse monitoring(@PathVariable Long deviceId,
                                   @ApiIgnore @ModelAttribute("user") User user) {
        List<DeviceLocationInfoVo> deviceLocationInfoVos = deviceService.monitor(deviceId);
        return RestResponse.success(deviceLocationInfoVos);
    }

    @Log("根据用户ID查询所有设备")
    @ApiOperation("根据用户ID查询所有设备")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    @GetMapping(value = "/device/queryAll/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceNo", value = "设备编号",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID",  dataType = "Long", paramType = "path")
    })
    public RestResponse queryAllByUserId(@PathVariable Long userId,
                                         String deviceNo,
                                         @ApiIgnore @ModelAttribute("user") User user) {
        List<DeviceVo> deviceVos = deviceService.queryAllByUserId(userId,user,deviceNo);
        return RestResponse.success(deviceVos);
    }






}

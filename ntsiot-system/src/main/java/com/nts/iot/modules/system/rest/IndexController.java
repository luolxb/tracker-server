package com.nts.iot.modules.system.rest;

import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceAlertProportionVo;
import com.nts.iot.modules.system.service.DeviceAlertService;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.nts.iot.modules.system.rest
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-08 11:10
 **/
@RestController
@RequestMapping("api/index")
@Api(tags = "首页")
public class IndexController extends JwtBaseController {

    @Autowired
    private DeviceAlertService deviceAlertService;

    @GetMapping("/alert/proportion")
    @Log("报警比例分析图/实时监测图")
    @ApiOperation("报警比例分析图/实时监测图")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public RestResponse proportion(@ApiIgnore @ModelAttribute("user") User user) {
        DeviceAlertProportionVo deviceAlertProportionVo = deviceAlertService.proportion(user);
        return RestResponse.success(deviceAlertProportionVo);
    }

    @GetMapping("/alert/monitor")
    @Log("报警实时监测")
    @ApiOperation("报警实时监测")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public RestResponse monitor(@ApiIgnore @ModelAttribute("user") User user) throws ParseException {
        List<Map<String, Object>> mapList = deviceAlertService.monitor(user);
        return RestResponse.success(mapList);
    }

}

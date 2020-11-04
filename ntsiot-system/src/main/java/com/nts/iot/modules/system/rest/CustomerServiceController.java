package com.nts.iot.modules.system.rest;

import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.CustomerServiceRq;
import com.nts.iot.modules.system.model.vo.CustomerServiceVo;
import com.nts.iot.modules.system.service.CustomerServiceService;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "4S店与客服")
@RestController
@RequestMapping("/api")
@Slf4j
public class CustomerServiceController extends JwtBaseController {

    @Autowired
    private CustomerServiceService customerServiceService;

    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("保存4S店与客服")
    @ApiOperation("保存4S店与客服")
    @PostMapping("/customer/service")
    public RestResponse save(@RequestBody CustomerServiceRq customerServiceRq,
                             @ApiIgnore @ModelAttribute("user") User user){
        customerServiceService.insertOrUpdate(customerServiceRq,user);
        return RestResponse.success();
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("获取4S店与客服")
    @ApiOperation("获取4S店与客服")
    @GetMapping("/customer/service/{userId}")
    @ApiImplicitParam(name = "id", value = "用户id",required = true ,dataType = "Long", paramType = "query")
    public RestResponse detail(@PathVariable Long userId){
        CustomerServiceVo customerServiceVo = customerServiceService.detail(userId);
        return RestResponse.success(customerServiceVo);
    }

}

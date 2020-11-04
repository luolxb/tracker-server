package com.nts.iot.modules.system.rest;

import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.GisServiceRq;
import com.nts.iot.modules.system.service.GisServiceService;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api")
@Api(tags = "GIS服务管理")
public class GisServiceController extends JwtBaseController {

    @Autowired
    private GisServiceService gisServiceService;

    @GetMapping("/gis/queryPage")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("gis服务列表")
    @ApiOperation("gis服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "gis服务名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "gis服务类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "所属用户id", dataType = "Long", required = true, paramType = "query")
    })
    public RestResponse queryPage(String name,
                                  String type,
                                  @RequestParam("userId") Long userId,
                                  Pageable pageable,
                                  @ApiIgnore @ModelAttribute("user") User user) {
        Map map = gisServiceService.queryPage(type, name, userId, pageable);
        return RestResponse.success(map);
    }

    @PostMapping("/gis/create")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    @Log("新增gis服务")
    @ApiOperation("新增gis服务")
    public RestResponse create(@Valid @RequestBody GisServiceRq gisServiceRq,
                               BindingResult bindingResult,
                               @ApiIgnore @ModelAttribute("user") User user
                               ) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }

        gisServiceService.create(gisServiceRq, user);
        return RestResponse.success();
    }

    @PostMapping("/gis/delete")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    @Log("删除gis服务")
    @ApiOperation("删除gis服务")
    public RestResponse delete( @RequestBody String ids,
                               @ApiIgnore @ModelAttribute("user") User user){

        gisServiceService.delete(ids,user);
        return RestResponse.success();
    }

}

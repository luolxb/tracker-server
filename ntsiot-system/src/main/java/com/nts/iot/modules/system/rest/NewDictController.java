package com.nts.iot.modules.system.rest;

import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.modules.system.model.NewDict;
import com.nts.iot.modules.system.model.vo.NewDictRq;
import com.nts.iot.modules.system.service.NewDictService;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.rest
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-07 15:13
 **/
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/api")
@Slf4j
public class NewDictController extends JwtBaseController {

    @Autowired
    private NewDictService newDictService;

    @Log("数据字典信息")
    @ApiOperation("数据字典信息")
    @GetMapping("/newDict/{pId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public RestResponse newDict(@PathVariable Long pId){
        List<NewDict> newDictList =  newDictService.newDict(pId);
        return RestResponse.success(newDictList);
    }

    @Log("全部数据字典信息")
    @ApiOperation("全部数据字典信息")
    @GetMapping("/newDict")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public RestResponse newDictAll(){
        List<NewDict> newDictList =  newDictService.newDict(null);
        return RestResponse.success(newDictList);
    }
}

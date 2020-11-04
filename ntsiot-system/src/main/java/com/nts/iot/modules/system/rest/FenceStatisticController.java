package com.nts.iot.modules.system.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.modules.system.model.vo.FenceStatisticRq;
import com.nts.iot.modules.system.model.vo.FenceStatisticVo;
import com.nts.iot.modules.system.service.FenceService;
import com.nts.iot.util.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "围栏报表统计")
@RestController
@RequestMapping("/api")
@Slf4j
public class FenceStatisticController extends JwtBaseController {

    @Autowired
    private FenceService fenceService;

    @PostMapping("/fence/statistic")
    @Log("围栏统计")
    @ApiOperation("围栏统计")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public RestResponse fenceStatistic(@RequestBody FenceStatisticRq fenceStatisticRq, Pageable pageable){
        Page<FenceStatisticVo> fenceStatisticVoList= fenceService.fenceStatistic(fenceStatisticRq,pageable);
        return RestResponse.success(fenceStatisticVoList);
    }


}

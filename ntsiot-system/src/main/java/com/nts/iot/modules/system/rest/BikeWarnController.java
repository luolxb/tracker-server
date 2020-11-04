/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.system.rest;


import com.nts.iot.modules.system.service.BikeWarnService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import com.nts.iot.modules.system.dto.BikeWarnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.nts.iot.constant.MiniAppConstants.USER_TYPE_OUTER;


/**
 * <p>
 * </p>
 * 小程序接口
 *
 * @author <a href="mailto:zzm@rnstec.com">zzm</a>
 * @version dls-api 1.0 $ 2017-11-08
 */
@RestController
@RequestMapping("api")
public class BikeWarnController extends JwtBaseController {

    private static final String ENTITY_NAME = "bikeWarn";

    @Autowired
    BikeWarnService bikeWarnService;

    @Autowired
    DataScope dataScope;



    @Log("车辆警告查询")
    @GetMapping(value = "/bikeWarn")
    @PreAuthorize("hasAnyRole('ADMIN','BIKE_WARN_ALL','BIKE_WARN_SELECT')")
    public ResponseEntity bikeWarns(BikeWarnDTO bikeWarnDTO, Pageable pageable){
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        return new ResponseEntity(bikeWarnService.queryAll(bikeWarnDTO,deptIds,pageable),HttpStatus.OK);
    }


//    @Log("查询车辆")
//    @GetMapping(value = "/bikeWarn/dept")
//    @PreAuthorize("hasAnyRole('ADMIN','MAUSER_ALL','MAUSER_SELECT')")
//    public ResponseEntity getUsersByDeptOd(@ModelAttribute("user") User user){
//        return new ResponseEntity(bikeWarnService.queryByDeptId(user.getDeptId()),HttpStatus.OK);
//    }


}

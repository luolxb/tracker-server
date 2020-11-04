/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.system.rest;


import com.nts.iot.modules.miniApp.dto.MaUserDTO;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
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
public class MaUserController extends JwtBaseController {

    private static final String ENTITY_NAME = "maUser";


    @Autowired
    MaUserService maUserService;

    @Autowired
    DataScope dataScope;

    @Autowired
    DeptService deptService;

    @Log("查询用户")
    @GetMapping(value = "/maUsers")
    @PreAuthorize("hasAnyRole('ADMIN','MAUSER_ALL','MAUSER_SELECT')")
    public ResponseEntity getUsers(MaUserDTO userDTO, Pageable pageable){
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        return new ResponseEntity(maUserService.queryAll(userDTO,deptIds,pageable),HttpStatus.OK);
    }

    @Log("解绑用户")
    @PutMapping(value = "/maUsers/remove/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MAUSER_ALL','MAUSER_EDIT')")
    public ResponseEntity removeUser(@PathVariable Long id){
        MaUser maUser = maUserService.getById(id);
        maUser.setDeptId(null);
        maUser.setName(null);
        maUser.setPhone(null);
        maUser.setUserType(USER_TYPE_OUTER);
        maUserService.updateById(maUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("删除用户")
    @DeleteMapping(value = "/maUsers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MAUSER_ALL','MAUSER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        //删除用户信息
        maUserService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询用户")
    @GetMapping(value = "/maUsers/dept")
    @PreAuthorize("hasAnyRole('ADMIN','MAUSER_ALL','MAUSER_SELECT')")
    public ResponseEntity getUsersByDeptOd(@ModelAttribute("user") User user){
        return new ResponseEntity(maUserService.queryByDeptId(null),HttpStatus.OK);
    }


}

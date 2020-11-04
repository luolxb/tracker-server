package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.service.RealTaskService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实际生成的任务
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:34
 * @Description:
 */
@RestController
@RequestMapping("api")
public class RealTaskController extends JwtBaseController {


    @Autowired
    private RealTaskService realTaskService;

    /**
     * 获取任务信息
     * @param id
     * @return
     */
    @Log("查看任务信息")
    @GetMapping(value = "/realTask/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TASKAPPROVAL_ALL')")
    public ResponseEntity getTaskById(@PathVariable Long id){
        return new ResponseEntity(realTaskService.getTaskById(id), HttpStatus.OK);
    }
}

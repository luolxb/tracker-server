package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.TaskApproval;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.TaskApprovalService;
import com.nts.iot.modules.system.service.TaskTemplateService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:34
 * @Description:
 */
@RestController
@RequestMapping("api")
public class TaskApprovalController extends JwtBaseController {

    @Autowired
    private TaskTemplateService taskTemplateService;

    @Autowired
    private TaskApprovalService taskApprovalService;

    @Autowired
    private DeptService deptService;

    /**
     * 任务审核列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("任务审核列表查询")
    @GetMapping(value = "/taskApprovals")
    @PreAuthorize("hasAnyRole('ADMIN','TASKAPPROVAL_ALL')")
    public ResponseEntity getTaskApprovals(String title, String patrolman, String startTime, String endTime, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (Dept dept : deptList) {
            jurisdictions.add(String.valueOf(dept.getId()));
        }
        return new ResponseEntity(taskTemplateService.queryAll(title, patrolman, startTime, endTime, jurisdictions, pageable), HttpStatus.OK);
    }


    /**
     * 保存审核信息
     * @param taskApproval
     * @param user
     * @return
     */
    @Log("保存审核信息")
    @PostMapping(value = "/taskApproval/add")
    @PreAuthorize("hasAnyRole('ADMIN','TASKAPPROVAL_ALL','TASKAPPROVAL_EDIT')")
    public ResponseEntity saveApproval(@Validated @RequestBody TaskApproval taskApproval, @ModelAttribute("user") User user){
        taskApproval.setCreateTime(System.currentTimeMillis());
        return new ResponseEntity(taskApprovalService.saveApproval(taskApproval), HttpStatus.CREATED);
    }

}

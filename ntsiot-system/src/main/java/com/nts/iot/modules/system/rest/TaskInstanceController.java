package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.TaskInstance;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.TaskInstanceService;
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
public class TaskInstanceController extends JwtBaseController {

    @Autowired
    private TaskInstanceService taskInstanceService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("巡更任务实例查询")
    @GetMapping(value = "/taskInstances")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_DELETE')")
    public ResponseEntity taskInstances(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (Dept dept : deptList) {
            jurisdictions.add(String.valueOf(dept.getId()));
        }
        return new ResponseEntity(taskInstanceService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }

    /**
     * 新增
     * @param taskInstance
     * @param user
     * @return
     */
    @Log("巡更任务实例新增")
    @PostMapping(value = "/taskInstance/add")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_CREATE')")
    public ResponseEntity createTaskInstance(@Validated @RequestBody TaskInstance taskInstance, @ModelAttribute("user") User user){
        taskInstance.setCreateTime(System.currentTimeMillis());
        taskInstanceService.saveTaskInstance(taskInstance);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 编辑
     * @param taskInstance
     * @param user
     * @return
     */
    @Log("巡更任务实例编辑")
    @PutMapping(value = "/taskInstance/edit")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_EDIT')")
    public ResponseEntity updateTaskInstance(@Validated @RequestBody TaskInstance taskInstance, @ModelAttribute("user") User user){
        taskInstance.setUpdateTime(System.currentTimeMillis());
        taskInstanceService.updateTaskInstance(taskInstance);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取任务配置信息
     * @param id
     * @return
     */
    @Log("查看任务配置信息")
    @GetMapping(value = "/taskInstance/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_DELETE')")
    public ResponseEntity getTaskInstanceById(@PathVariable Long id){
        return new ResponseEntity(taskInstanceService.getTaskInstanceById(id), HttpStatus.OK);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("巡更任务实例删除")
    @DeleteMapping(value = "/taskInstance/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        taskInstanceService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 更改任务状态
     * @param id
     * @param status
     * @return
     */
    @Log("更改任务状态")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_EDIT')")
    @PostMapping(value = "/taskInstance/changeStatus")
    public ResponseEntity updataTaskStatus(Long id, String status) {
        taskInstanceService.updateTaskStatus(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

}

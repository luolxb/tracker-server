package com.nts.iot.modules.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.TaskTemplate;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.TaskPointService;
import com.nts.iot.modules.system.service.TaskTemplateService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;

import com.nts.iot.utils.PageUtil;
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
public class TaskTemplateController extends JwtBaseController {

    @Autowired
    private TaskTemplateService taskTemplateService;

    @Autowired
    private TaskPointService taskPointService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("任务模板查询")
    @GetMapping(value = "/taskTemplates")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_DELETE')")
    public ResponseEntity getList(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (Dept dept : deptList) {
            jurisdictions.add(String.valueOf(dept.getId()));
        }
        return new ResponseEntity(taskTemplateService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }

    /**
     * 根据辖区查找模板
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/taskTemplate/dept")
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL')")
    public ResponseEntity getTaskTemplates(Pageable pageable, @ModelAttribute("user") User user) {
        IPage<TaskTemplate> pageResult = taskTemplateService.selectAllByDept(pageable,null);
        return new ResponseEntity(PageUtil.toPage(pageResult), HttpStatus.OK);
    }

    /**
     * 新增
     * @param taskTemplate
     * @param user
     * @return
     */
    @Log("任务模板新增")
    @PostMapping(value = "/taskTemplate/add")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_CREATE')")
    public ResponseEntity createTaskTemplate(@Validated @RequestBody TaskTemplate taskTemplate, @ModelAttribute("user") User user){
        taskTemplate.setCreateTime(System.currentTimeMillis());
//        taskTemplate.setDeptId(user.getDeptId());
        taskTemplateService.saveTaskTemplate(taskTemplate);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 编辑
     * @param taskTemplate
     * @param user
     * @return
     */
    @Log("任务模板编辑")
    @PutMapping(value = "/taskTemplate/edit")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_EDIT')")
    public ResponseEntity updateTaskTemplate(@Validated @RequestBody TaskTemplate taskTemplate, @ModelAttribute("user") User user){
        taskTemplate.setUpdateTime(System.currentTimeMillis());
        taskTemplateService.updateTaskTemplate(taskTemplate);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取任务配置信息
     * @param id
     * @return
     */
    @Log("获取任务配置信息")
    @GetMapping(value = "/taskTemplate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_DELETE')")
    public ResponseEntity getTaskTemplateById(@PathVariable Long id){
        return new ResponseEntity(taskTemplateService.getTaskTemplateById(id), HttpStatus.OK);
    }



    /**
     * 删除
     * @param id
     * @return
     */
    @Log("任务模板删除")
    @DeleteMapping(value = "/taskTemplate/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TASKTEMPLATE_ALL','TASKTEMPLATE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        taskTemplateService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

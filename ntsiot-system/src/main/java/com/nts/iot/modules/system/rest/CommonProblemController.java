package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.CommonProblem;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.CommonProblemService;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 常见问题
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/6 11:22
 * @Description:
 */

@RestController
@RequestMapping("api")
public class CommonProblemController extends JwtBaseController {

    @Autowired
    private CommonProblemService commonProblemService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("常见问题查询")
    @GetMapping(value = "/problems")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_ALL','PROBLEM_DELETE')")
    public ResponseEntity getList(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(commonProblemService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }


    /**
     * 新增常见问题
     * @param commonProblem
     * @param user
     * @return
     */
    @Log("常见问题新增")
    @PostMapping(value = "/problem/add")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_ALL','PROBLEM_CREATE')")
    public ResponseEntity create(@Validated @RequestBody CommonProblem commonProblem, @ModelAttribute("user") User user){
        // 辖区
        commonProblem.setJurisdiction(null);
        // 创建时间
        commonProblem.setCreateTime(System.currentTimeMillis());
        // 是否显示 默认显示
        commonProblem.setIsVisible(commonProblem.getIsVisible());
        commonProblemService.saveProblem(commonProblem);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("常见问题删除")
    @DeleteMapping(value = "/problem/delete/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_ALL','PROBLEM_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        return new ResponseEntity(commonProblemService.removeById(id),HttpStatus.OK);
    }

    /**
     * 获取常见问题内容
     * @param id
     * @return
     */
    @Log("常见问题详情")
    @GetMapping(value = "/problem/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_ALL')")
    public ResponseEntity getProblemById(@PathVariable Long id){
        return new ResponseEntity(commonProblemService.getById(id),HttpStatus.OK);
    }

    /**
     * 修改
     * @param commonProblem
     * @return
     */
    @Log("常见问题修改")
    @PutMapping(value = "/problem/edit")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_ALL','PROBLEM_EDIT')")
    public ResponseEntity update(@Validated @RequestBody CommonProblem commonProblem){
        commonProblemService.updateProblemById(commonProblem);
        return new ResponseEntity(HttpStatus.OK);
    }
}

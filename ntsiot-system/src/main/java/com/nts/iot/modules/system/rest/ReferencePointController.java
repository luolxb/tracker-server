package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.ReferencePoint;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.ReferencePointService;
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
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/5 15:13
 * @Description:
 */
@RestController
@RequestMapping("api")
public class ReferencePointController extends JwtBaseController {

    @Autowired
    private ReferencePointService referencePointService;

    @Autowired
    private DeptService deptService;


    /**
     * 列表查询
     * @return
     */
    @Log("参考点查询")
    @GetMapping(value = "/referencePoints")
    @PreAuthorize("hasAnyRole('ADMIN','REFERENCEPOINT_ALL','REFERENCEPOINT_DELETE')")
    public ResponseEntity list(String name, Long iconType, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }
        return new ResponseEntity(referencePointService.queryAll(name, iconType, jurisdictions, pageable), HttpStatus.OK);
    }

    /**
     * 新增
     * @param referencePoint
     * @param user
     * @return
     */
    @Log("新增参考点")
    @PostMapping(value = "/referencePoint/add")
    @PreAuthorize("hasAnyRole('ADMIN','REFERENCEPOINT_ALL','REFERENCEPOINT_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody ReferencePoint referencePoint, @ModelAttribute("user") User user){
        referencePoint.setCreateTime(System.currentTimeMillis());
        return new ResponseEntity(referencePointService.save(referencePoint), HttpStatus.CREATED);
    }

    /**
     * 修改
     * @param referencePoint
     * @return
     */
    @Log("修改参考点")
    @PutMapping(value = "/referencePoint/edit")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','REFERENCEPOINT_ALL','REFERENCEPOINT_EDIT')")
    public ResponseEntity update(@RequestBody ReferencePoint referencePoint, @ModelAttribute("user") User user){
        referencePoint.setUpdateTime(System.currentTimeMillis());
        return new ResponseEntity(referencePointService.updateById(referencePoint), HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("删除参考点")
    @DeleteMapping(value = "/referencePoint/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','REFERENCEPOINT_ALL','REFERENCEPOINT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        return new ResponseEntity(referencePointService.removeById(id),HttpStatus.OK);
    }

    @Log("查看参考点")
    @GetMapping(value = "/referencePoint/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','REFERENCEPOINT_ALL','REFERENCEPOINT_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity get(@PathVariable Long id){
        return new ResponseEntity(referencePointService.getById(id), HttpStatus.CREATED);
    }
}

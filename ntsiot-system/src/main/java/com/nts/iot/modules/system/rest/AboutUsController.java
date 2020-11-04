package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.AboutUs;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.AboutUsService;
import com.nts.iot.modules.system.service.DeptService;
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
 * 新闻
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:34
 * @Description:
 */
@RestController
@RequestMapping("api")
public class AboutUsController extends JwtBaseController {

    @Autowired
    private AboutUsService aboutUsService;

    @Autowired
    private DeptService deptService;


    /**
     * 列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("关于我们查询")
    @GetMapping(value = "/aboutUs")
    @PreAuthorize("hasAnyRole('ADMIN','ABOUTUS_ALL','ABOUTUS_DELETE')")
    public ResponseEntity getList(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(aboutUsService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }


    /**
     * 新增
     * @param aboutUs
     * @param user
     * @return
     */
    @Log("关于我们新增")
    @PostMapping(value = "/aboutUs/add")
    @PreAuthorize("hasAnyRole('ADMIN','ABOUTUS_ALL','ABOUTUS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody AboutUs aboutUs, @ModelAttribute("user") User user){
        // 辖区
        aboutUs.setDeptId(null);
        // 创建时间
        aboutUs.setCreateTime(System.currentTimeMillis());
        return new ResponseEntity(aboutUsService.saveAboutUs(aboutUs), HttpStatus.CREATED);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("关于我们删除")
    @DeleteMapping(value = "/aboutUs/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ABOUTUS_ALL','ABOUTUS_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        return new ResponseEntity(aboutUsService.deleteById(id), HttpStatus.OK);
    }

    /**
     * 获取内容
     * @param id
     * @return
     */
    @GetMapping(value = "/aboutUs/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ABOUTUS_ALL')")
    public ResponseEntity getAboutUsById(@PathVariable Long id){
        return new ResponseEntity(aboutUsService.getAboutUsById(id),HttpStatus.OK);
    }

    /**
     * 修改
     * @param aboutUs
     * @return
     */
    @Log("关于我们修改")
    @PutMapping(value = "/aboutUs/edit")
    @PreAuthorize("hasAnyRole('ADMIN','ABOUTUS_ALL','ABOUTUS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody AboutUs aboutUs){
        return new ResponseEntity(aboutUsService.updateAboutUs(aboutUs), HttpStatus.OK);
    }
}

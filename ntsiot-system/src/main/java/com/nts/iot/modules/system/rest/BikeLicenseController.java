package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.BikeLicense;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.BikeLicenseService;
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
 * @Author zhc@rnstec.com
 * @Description 车辆授权controller
 * @Date 2019-05-06 14：30
 * @Version: 1.0
 */
@RestController
@RequestMapping("api")
public class BikeLicenseController extends JwtBaseController {

    @Autowired
    BikeLicenseService bikeLicenseService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     * @return
     */
    @Log("车辆授权查询")
    @GetMapping(value = "/licenses")
    @PreAuthorize("hasAnyRole('ADMIN','LICENSE_ALL','LICENSE_DELETE')")
    public ResponseEntity getList(String name, String telephone, String jurisdiction, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        if (jurisdiction != null) {
            jurisdictions.add(jurisdiction);
        } else {
            List<Dept> deptList = deptService.findByUserRole(user);
            for (int i = 0; i < deptList.size(); i++) {
                Dept dept = deptList.get(i);
                jurisdictions.add(String.valueOf(dept.getId()));
            }
        }

        return new ResponseEntity(bikeLicenseService.queryAll(name, telephone, jurisdictions, pageable), HttpStatus.OK);
    }

    /**
     * 新增车辆授权
     * @param bikeLicense
     * @return
     */
    @Log("新增车辆授权")
    @PostMapping(value = "/licenses/add")
    @PreAuthorize("hasAnyRole('ADMIN','LICENSE_ALL','LICENSE_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody BikeLicense bikeLicense, @ModelAttribute("user") User user){
        bikeLicense.setCreateTime(System.currentTimeMillis());
        bikeLicense.setCreator(user.getUsername());
        bikeLicenseService.save(bikeLicense);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 修改授权
     * @param bikeLicense
     * @return
     */
    @Log("新增车辆修改")
    @PutMapping(value = "/licenses/edit")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','LICENSE_ALL','LICENSE_EDIT')")
    public ResponseEntity update(@RequestBody BikeLicense bikeLicense, @ModelAttribute("user") User user){
        bikeLicense.setUpdateTime(System.currentTimeMillis());
        bikeLicense.setUpdater(user.getUsername());
        bikeLicenseService.updateById(bikeLicense);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除授权
     * @param id
     * @return
     */
    @Log("新增车辆删除")
    @DeleteMapping(value = "/licenses/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','LICENSE_ALL','LICENSE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        return new ResponseEntity(bikeLicenseService.removeById(id),HttpStatus.OK);
    }
}

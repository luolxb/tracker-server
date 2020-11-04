package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Icon;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.IconService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/5 13:27
 * @Description:
 */
@RestController
@RequestMapping("api")
public class IconController extends JwtBaseController {

    @Autowired
    private IconService iconService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     * @return
     */
    @Log("图标查询")
    @GetMapping(value = "/icons")
    @PreAuthorize("hasAnyRole('ADMIN','ICON_ALL','ICON_DELETE')")
    public ResponseEntity list(String name, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(iconService.queryAll(name, jurisdictions, pageable), HttpStatus.OK);
    }

    /**
     * 新增图标
     * @param icon
     * @param user
     * @return
     */
    @Log("新增图标")
    @PostMapping(value = "/icon/add")
    @PreAuthorize("hasAnyRole('ADMIN','ICON_ALL','ICON_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody Icon icon, @ModelAttribute("user") User user){
        icon.setCreateTime(System.currentTimeMillis());
        return new ResponseEntity(iconService.save(icon), HttpStatus.CREATED);
    }

    /**
     * 修改
     * @param icon
     * @return
     */
    @Log("修改图标")
    @PutMapping(value = "/icon/edit")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','ICON_ALL','ICON_EDIT')")
    public ResponseEntity update(@RequestBody Icon icon, @ModelAttribute("user") User user){
        icon.setUpdateTime(System.currentTimeMillis());
        return new ResponseEntity(iconService.updateById(icon), HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("删除图标")
    @DeleteMapping(value = "/icon/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','ICON_ALL','ICON_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        return new ResponseEntity(iconService.removeById(id),HttpStatus.OK);
    }

    /**
     * 根据辖区查找
     * @param user
     * @return
     */
    @GetMapping("/icon/list")
    public ResponseEntity getIconsByDeptId(@ModelAttribute("user") User user) {
        Map<String, Object> columnMap = new HashMap<>();
        return new ResponseEntity(iconService.listByMap(columnMap),HttpStatus.OK);
    }
}

package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.InformConfig;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.InformConfigService;
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
 * 租房人义务告知
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:34
 * @Description:
 */
@RestController
@RequestMapping("api")
public class InformConfigController extends JwtBaseController {

    @Autowired
    private InformConfigService informConfigService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("租房人义务告知查询")
    @GetMapping(value = "/informs")
    @PreAuthorize("hasAnyRole('ADMIN','INFORM_ALL','INFORM_DELETE')")
    public ResponseEntity getList(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(informConfigService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }


    /**
     * @param informConfig
     * @param user
     * @return
     */
    @Log("新增公告")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/informs/add")
    @PreAuthorize("hasAnyRole('ADMIN','INFORM_ALL','INFORM_CREATE')")
    public ResponseEntity create(@Validated @RequestBody InformConfig informConfig, @ModelAttribute("user") User user){
        // 辖区
        informConfig.setDeptId(null);
        // 创建时间
        informConfig.setCreateTime(System.currentTimeMillis());
        // 是否显示 默认显示
        informConfigService.save(informConfig);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("删除公告")
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping(value = "/informs/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INFORM_ALL','INFORM_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        informConfigService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取内容
     * @param id
     * @return
     */
    @Log("查看公告")
    @GetMapping(value = "/informs/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INFORM_ALL')")
    public ResponseEntity getNewById(@PathVariable Long id){
        return new ResponseEntity(informConfigService.getById(id),HttpStatus.OK);
    }

    /**
     * 修改
     * @param informConfig
     * @return
     */
    @Log("修改公告")
    @Transactional(rollbackFor = Exception.class)
    @PutMapping(value = "/informs/edit")
    @PreAuthorize("hasAnyRole('ADMIN','INFORM_ALL','INFORM_EDIT')")
    public ResponseEntity update(@Validated @RequestBody InformConfig informConfig){
        informConfigService.updateById(informConfig);
        return new ResponseEntity(HttpStatus.OK);
    }
}

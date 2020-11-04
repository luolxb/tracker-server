package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.RoleDeptRelaService;
import com.nts.iot.modules.system.service.UserService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dto.DeptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author jie
 * @date 2019-03-25
 */
@RestController
@RequestMapping("api")
public class DeptController extends JwtBaseController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private DataScope dataScope;

    @Autowired
    private RoleDeptRelaService roleDeptRelaService;

    @Autowired
    private UserService userService;

    private static final String ENTITY_NAME = "dept";

    @Log("查询部门")
    @GetMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getDepts(DeptDTO resources) {
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        List<DeptDTO> deptDTOS = deptService.queryAll(resources, deptIds);
        return new ResponseEntity(deptService.buildTree(deptDTOS), HttpStatus.OK);
    }

    @Log("新增部门")
    @PostMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Dept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(deptService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改部门")
    @PutMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_EDIT')")
    public ResponseEntity update(@RequestBody Dept resources) {
        deptService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping(value = "/dept/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>();
        if (roleDeptRelaService.findByDept(id) > 0 || userService.findByDept(id) > 0 || deptService.findByPid(id).size() > 0) {
            map.put("isEmpty", false);
            return new ResponseEntity(map, HttpStatus.IM_USED);
        }
        deptService.delete(id);
        map.put("isEmpty", true);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping(value = "/get_dept_list")
    public ResponseEntity getDepts(@ModelAttribute("user") User user) {
        List<Dept> deptList = deptService.findByUserRole(user);
        return new ResponseEntity(deptList, HttpStatus.OK);
    }

    /**
     * 辖区列表
     * @param name
     * @param pageable
     * @param user
     * @return
     */
    @GetMapping(value = "/dept/all")
    public ResponseEntity getList(String name, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size() ; i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(deptService.getDeptList(name, jurisdictions, pageable), HttpStatus.OK);
    }
}
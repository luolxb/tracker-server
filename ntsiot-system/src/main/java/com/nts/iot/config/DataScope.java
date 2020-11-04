package com.nts.iot.config;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Role;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.RoleService;
import com.nts.iot.modules.system.service.UserService;
import com.nts.iot.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据权限配置
 * @author jie
 * @date 2019-4-1
 */
@Component
public class DataScope {

    private final String[] scopeType = {"全部","自定义"};

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    public Set<Long> getDeptIds() {

        User user = userService.findByName(SecurityContextHolder.getUserDetails().getUsername());

        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();

        // 查询用户角色
        List<Role> roleSet = roleService.findByUserId(user.getId());

        for (Role role : roleSet) {

            if (scopeType[0].equals(role.getDataScope())) {
                return new HashSet<>() ;
            }

//            // 存储本级的数据权限
//            if (scopeType[1].equals(role.getDataScope())) {
//                deptIds.add(user.getDept().getId());
//            }

            // 存储自定义的数据权限
            if (scopeType[1].equals(role.getDataScope())) {
                Set<Dept> deptList = role.getDepts();
                for (Dept dept : deptList) {
                    deptIds.add(dept.getId());
                    List<Dept> deptChildren = deptService.findByPid(dept.getId());
                    if (deptChildren != null && deptChildren.size() != 0) {
                        deptIds.addAll(getDeptChildren(deptChildren));
                    }
                }
            }
        }
        return deptIds;
    }


    public List<Long> getDeptChildren(List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
//                    if (dept!=null && dept.getEnabled()){
                    if (dept!=null){
                        List<Dept> depts = deptService.findByPid(dept.getId());
                        if(deptList!=null && deptList.size()!=0){
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }
}

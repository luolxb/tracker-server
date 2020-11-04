package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.exception.EntityExistException;
import com.nts.iot.modules.system.dao.RoleMapper;
import com.nts.iot.modules.system.dto.RoleDTO;

import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RolePermiRelaService rolePermiRelaService;

    @Autowired
    private RoleMenuRelaService roleMenuRelaService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleDeptRelaService roleDeptRelaService;

    @Autowired
    private UserRoleRelaService userRoleRelaService;

    @Override
    public Object queryAll(String name, List<String> depts, Pageable pageable) {
        Page<Role> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Role> pageResult = baseMapper.selectPage(page, name, depts);
        pageResult.getRecords().forEach(role -> {
//            //TODO
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            role.setPermissions(permissionService.findByRoles(roleList));
            role.setMenus(menuService.findByRoles(roleList));
            role.setDepts(deptService.findByRoles(roleList));
        });
        return PageUtil.toPage(pageResult);
    }

    @Override
    public RoleDTO findByOne(long id) {
        RoleDTO roleDTO = new RoleDTO();
//        Optional<Role> role = roleRepository.findById(id);
        Role role = baseMapper.selectById(id);
//        ValidationUtil.isNull(role,"Role","id",id);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        role.setPermissions(permissionService.findByRoles(roleList));
        role.setMenus(menuService.findByRoles(roleList));
        role.setDepts(deptService.findByRoles(roleList));
        BeanUtil.copyProperties(role, roleDTO);
        return roleDTO;
    }

    @Override
    public RoleDTO findById(long id) {
        RoleDTO roleDTO = new RoleDTO();
//        Optional<Role> role = roleRepository.findById(id);
        Role role = baseMapper.selectById(id);
//        ValidationUtil.isNull(role,"Role","id",id);
        BeanUtil.copyProperties(role, roleDTO);
        return roleDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Role resources) {
        if (Role.RoleDataScope.All.text.equals(resources.getDataScope()) ||
                Role.RoleDataScope.CurrentLevel.text.equals(resources.getDataScope())) {
            //暂不支持全部、本级类型的角色
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "暂不支持全部、本级类型的角色！");
        }
        List<Role> roles = baseMapper.findByName(resources.getName());
        if (roles != null && roles.size() > 0 && roles.get(0) != null) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
        resources.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if (baseMapper.insert(resources) > 0) {
            if (Role.RoleDataScope.Custom.text.equals(resources.getDataScope())) {
                this.saveRoleDeptRelation(resources);
            }
        }
    }

    /**
     * 保存角色、部门信息
     *
     * @param resources
     */
    void saveRoleDeptRelation(Role resources) {
        List<RoleDeptRelation> rdList = new ArrayList<>();
        resources.getDepts().forEach(it -> {
            RoleDeptRelation rd = new RoleDeptRelation();
            rd.setDeptId(it.getId());
            rd.setRoleId(resources.getId());
            rdList.add(rd);
        });
        roleDeptRelaService.saveBatch(rdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {

//        Optional<Role> optionalRole = roleRepository.findById(resources.getId());
        Role role = baseMapper.selectById(resources.getId());
//        ValidationUtil.isNull(optionalRole,"Role","id",resources.getId());
//
//        Role role = optionalRole.get();

        List<Role> roles = baseMapper.findByName(resources.getName());

        if (roles != null && roles.size() > 0 && roles.get(0) != null
                && !roles.get(0).getId().equals(role.getId())) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
        //清除角色、部门信息
        Map<String, Object> delMap = new HashMap<>();
        delMap.put("role_id", resources.getId());
        roleDeptRelaService.removeByMap(delMap);
        //数据范围是自定义
        if (Role.RoleDataScope.Custom.text.equals(resources.getDataScope())) {
            //更新角色、部门信息
            this.saveRoleDeptRelation(resources);
        }
        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setDataScope(resources.getDataScope());
        role.setDepts(resources.getDepts());
        baseMapper.updateById(role);
    }

    @Override
    public void updatePermission(Role resources, RoleDTO roleDTO) {
        //先清除角色、权限信息
        Map<String, Object> delMap = new HashMap<>();
        delMap.put("role_id", resources.getId());
        rolePermiRelaService.removeByMap(delMap);

        //增加角色、权限信息
        List<RolePermiRelation> rolePerList = new ArrayList<>();
        resources.getPermissions().forEach(it -> {
            RolePermiRelation rp = new RolePermiRelation();
            rp.setPermissionId(it.getId());
            rp.setRoleId(resources.getId());
            rolePerList.add(rp);
        });
        rolePermiRelaService.saveBatch(rolePerList);
    }

    @Override
    public void updateMenu(Role resources, RoleDTO roleDTO) {
        //先清除角色、菜单信息
        Map<String, Object> delMap = new HashMap<>();
        delMap.put("role_id", resources.getId());
        roleMenuRelaService.removeByMap(delMap);

        //增加角色、菜单信息
        List<RoleMenuRelation> rmList = new ArrayList<>();
        resources.getMenus().forEach(it -> {
            RoleMenuRelation rm = new RoleMenuRelation();
            rm.setMenuId(it.getId());
            rm.setRoleId(resources.getId());
            rmList.add(rm);
        });
        roleMenuRelaService.saveBatch(rmList);
    }

//    @Override
//    public void untiedMenu(Menu menu) {
//        Set<Role> roles = roleRepository.findByMenus_Id(menu.getId());
//        for (Role role : roles) {
//            menu.getRoles().remove(role);
//            role.getMenus().remove(menu);
//            roleRepository.save(role);
//        }
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Map<String, Object> delMap = new HashMap<>();
        delMap.put("role_id", id);
        List<UserRoleRelation> userRoleRelations = userRoleRelaService.findByRoleId(id);
        if (userRoleRelations != null && userRoleRelations.size() > 0 && userRoleRelations.get(0) != null) {
            throw new BadRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "请先删除用户信息！");
        }
        //清除角色、菜单信息
        roleMenuRelaService.removeByMap(delMap);
        //清除角色、权限信息
        rolePermiRelaService.removeByMap(delMap);
        //清除角色、部门信息
        roleDeptRelaService.removeByMap(delMap);
        // 清除角色、用户信息
        userRoleRelaService.removeByMap(delMap);
        //清除角色信息
        this.removeById(id);
    }

    @Override
    public List<Role> findByUserId(Long id) {

        List<Role> roles = baseMapper.findByUserId(id);
        roles.forEach(role -> {
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            role.setPermissions(permissionService.findByRoles(roleList));
            role.setMenus(menuService.findByRoles(roleList));
            role.setDepts(deptService.findByRoles(roleList));
        });

        return roles;
    }
}

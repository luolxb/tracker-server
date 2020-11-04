package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Menu;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.MenuService;
import com.nts.iot.modules.system.service.RoleMenuRelaService;
import com.nts.iot.modules.system.service.RoleService;
import com.nts.iot.modules.system.service.UserService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dto.MenuDTO;
import com.nts.iot.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuRelaService roleMenuRelaService;


    private static final String ENTITY_NAME = "menu";

    /**
     * 构建前端路由所需要的菜单
     *
     * @return
     */
    @GetMapping(value = "/menus/build")
    public ResponseEntity buildMenus() {
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        User user = userService.findUserByName(userDetails.getUsername());
        List<MenuDTO> menuDTOList = menuService.findMenusByRoles(roleService.findByUserId(user.getId()));
        //去重
        HashMap<String,MenuDTO> hashMap = new HashMap<String,MenuDTO>();
        for(MenuDTO menuDTO:menuDTOList){
            String path= menuDTO.getPath();
            hashMap.put(path,menuDTO);
        }
        menuDTOList = new ArrayList<MenuDTO>();
        for(String key:hashMap.keySet())
        {
            menuDTOList.add(hashMap.get(key));
        }
        List<MenuDTO> menuDTOTree = (List<MenuDTO>) menuService.buildTree(menuDTOList).get("content");
        return new ResponseEntity(menuService.buildMenus(menuDTOTree), HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     *
     * @return
     */
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity getMenuTree() {
        return new ResponseEntity(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(@RequestParam(required = false) String name) {
        List<MenuDTO> menuDTOList = menuService.queryAll(name);
        return new ResponseEntity(menuService.buildTree(menuDTOList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Menu resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(menuService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity update(@RequestBody Menu resources) {
        menuService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //删除当前菜单的子菜单关联的菜单、角色数据
        List<Menu> menuList = menuService.findByPid(id);
        menuList.forEach(it -> {
            Map<String, Object> delMap = new HashMap<>();
            delMap.put("menu_id", it.getId());
            roleMenuRelaService.removeByMap(delMap);
            menuService.delete(it.getId());
        });
        //删除当前菜单的菜单、角色数据
        Map<String, Object> map = new HashMap<>();
        map.put("menu_id", id);
        roleMenuRelaService.removeByMap(map);
        menuService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

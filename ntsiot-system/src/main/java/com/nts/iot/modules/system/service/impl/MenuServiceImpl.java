package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.exception.EntityExistException;
import com.nts.iot.modules.system.dao.MenuMapper;
import com.nts.iot.modules.system.domain.vo.MenuMetaVo;
import com.nts.iot.modules.system.domain.vo.MenuVo;
import com.nts.iot.modules.system.dto.MenuDTO;
import com.nts.iot.modules.system.model.Menu;
import com.nts.iot.modules.system.model.Role;
import com.nts.iot.modules.system.service.MenuService;
import com.nts.iot.utils.ValidationUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public MenuDTO findById(long id) {
        MenuDTO menuDTO = new MenuDTO();
        Menu menu = baseMapper.selectById(id);
        BeanUtil.copyProperties(menu, menuDTO);
        return menuDTO;
    }

    @Override
    public Set<Menu> findByRoles(List<Role> roles) {
        Set<Menu> menus = new HashSet<>(baseMapper.findByRoles(roles));
        return menus;
    }

    @Override
    public Integer create(Menu resources) {
        if (this.findByName(resources.getName()) != null) {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }
        if (resources.getIFrame()) {
            if (!(resources.getPath().toLowerCase().startsWith("http://") || resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        resources.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return baseMapper.insert(resources);
    }


    Menu findByName(String name) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(name)) {
            wrapper.eq("name", name);
        }
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void update(Menu resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Optional<Menu> optionalPermission = Optional.of(baseMapper.selectById(resources.getId()));
        ValidationUtil.isNull(optionalPermission, "Permission", "id", resources.getId());

        if (resources.getIFrame()) {
            if (!(resources.getPath().toLowerCase().startsWith("http://") || resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        Menu menu = optionalPermission.get();
        Menu menu1 = this.findByName(resources.getName());

        if (menu1 != null && !menu1.getId().equals(menu.getId())) {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }
        menu.setName(resources.getName());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setSort(resources.getSort());
        baseMapper.updateById(menu);
    }

    @Override
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public Object getMenuTree(List<Menu> menus) {
        List<Map<String, Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                    if (menu != null) {
                        Map<String, Object> menuMap = new HashMap();
                        menuMap.put("pid", menu.getId());
                        List<Menu> menuList = baseMapper.selectByMap(menuMap);
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", menu.getId());
                        map.put("label", menu.getName());
                        if (menuList != null && menuList.size() != 0) {
                            map.put("children", getMenuTree(menuList));
                        }
                        if ("1".equals(menu.getHidden())) {
                            map.put("hidden", true);
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Menu> findByPid(long pid) {
        Map<String, Object> menuMap = new HashMap();
        menuMap.put("pid", pid);
        return baseMapper.selectByMap(menuMap);
    }


    @Override
    public Map buildTree(List<MenuDTO> menuDTOS) {
        List<MenuDTO> trees = new ArrayList<>();

        for (MenuDTO menuDTO : menuDTOS) {

            if ("0".equals(menuDTO.getPid().toString())) {
                trees.add(menuDTO);
            }

            for (MenuDTO it : menuDTOS) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                }
            }
        }
        Map map = new HashMap();
        map.put("content", trees.size() == 0 ? menuDTOS : trees);
        map.put("totalElements", menuDTOS != null ? menuDTOS.size() : 0);
        return map;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();
        menuDTOS.forEach(menuDTO -> {
                    if (menuDTO != null) {
                        List<MenuDTO> menuDTOList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(menuDTO.getName());
                        menuVo.setPath(menuDTO.getPath());
                        if ("1".equals(menuDTO.getHidden())) {
                            menuVo.setHidden(true);
                        } else {
                            menuVo.setHidden(false);
                        }
                        // 如果不是外链
                        if (!menuDTO.getIFrame()) {
                            if (menuDTO.getPid().equals(0L)) {
                                //一级目录需要加斜杠，不然访问 会跳转404页面
                                menuVo.setPath("/" + menuDTO.getPath());

                                menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                            } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getName(), menuDTO.getIcon()));
                        if (menuDTOList != null && menuDTOList.size() != 0) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDTOList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (menuDTO.getPid().equals(0L)) {
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (!menuDTO.getIFrame()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    @Override
    public Menu findOne(Long id) {
        Menu menu = baseMapper.selectById(id);
        return menu;
    }

    @Override
    public List<MenuDTO> findMenusByRoles(List<Role> roles) {
        List<MenuDTO> menus = new ArrayList<>();
        for (Role role : roles) {
            List<MenuDTO> menus1 = baseMapper.findMenusByRoleId(role.getId());
            menus.addAll(menus1);
        }
        return menus;
    }

    @Override
    public List<MenuDTO> queryAll(String name) {
        return baseMapper.queryAll(name);
    }
}

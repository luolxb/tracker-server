package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.MenuDTO;
import com.nts.iot.modules.system.model.Menu;
import com.nts.iot.modules.system.model.Role;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jie
 * @date 2018-12-17
 */
@CacheConfig(cacheNames = "menu")
public interface MenuService extends IService<Menu> {

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MenuDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    Integer create(Menu resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Menu resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * permission tree
     * @return
     */
    @Cacheable(key = "'tree'")
    Object getMenuTree(List<Menu> menus);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable(key = "'pid:'+#p0")
    List<Menu> findByPid(long pid);

    /**
     * build Tree
     * @param menuDTOS
     * @return
     */
    Map buildTree(List<MenuDTO> menuDTOS);

    /**
     * findByRoles
     * @param roles
     * @return
     */
    Set<Menu> findByRoles(List<Role> roles);

    /**
     * buildMenus
     * @param byRoles
     * @return
     */
    Object buildMenus(List<MenuDTO> byRoles);

    Menu findOne(Long id);

    List<MenuDTO> findMenusByRoles(List<Role> roles);

    List<MenuDTO> queryAll(String name);

}

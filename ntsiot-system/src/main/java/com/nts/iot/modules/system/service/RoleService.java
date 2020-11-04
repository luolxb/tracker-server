package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.RoleDTO;
import com.nts.iot.modules.system.model.Role;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@CacheConfig(cacheNames = "role")
public interface RoleService extends IService<Role> {

    Object queryAll(String name, List<String> depts, Pageable pageable);

    /**
     * get
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    RoleDTO findByOne(long id);

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    RoleDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    void create(Role resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Role resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * findByUsers_Id
     * @param id
     * @return
     */
//    @Cacheable(keyGenerator = "keyGenerator")
    List<Role> findByUserId(Long id);

    /**
     * updatePermission
     * @param resources
     * @param roleDTO
     */
    @CacheEvict(allEntries = true)
    void updatePermission(Role resources, RoleDTO roleDTO);

    /**
     * updateMenu
     * @param resources
     * @param roleDTO
     */
    @CacheEvict(allEntries = true)
    void updateMenu(Role resources, RoleDTO roleDTO);
//
//    @CacheEvict(allEntries = true)
//    void untiedMenu(Menu menu);
}

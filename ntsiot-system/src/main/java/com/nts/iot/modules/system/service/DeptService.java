package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.DeptDTO;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Role;
import com.nts.iot.modules.system.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jie
 * @date 2019-03-25
 */
public interface DeptService extends IService<Dept> {

    List<DeptDTO> queryAll(DeptDTO dept, Set<Long> deptIds);

    /**
     * findById
     *
     * @param id
     * @return
     */
    DeptDTO findById(Long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    Integer create(Dept resources);

    /**
     * update
     *
     * @param resources
     */
    void update(Dept resources);

    /**
     * delete
     *
     * @param id
     */
    void delete(Long id);

    /**
     * buildTree
     *
     * @param deptDTOS
     * @return
     */
    Map buildTree(List<DeptDTO> deptDTOS);

    /**
     * findByPid
     *
     * @param pid
     * @return
     */
    List<Dept> findByPid(long pid);

    Set<Dept> findByRoles(List<Role> roles);

    List<Dept> findByCode(String code);

    List<Dept> findByUserRole(User user);

    void getDeptAll();

    List<Long> getSubDepts(long deptId);

    List<Dept> getPidDepts(long deptId);

    /**
     * 列表查询
     * @param name
     * @param jurisdictions
     * @param pageable
     * @return
     */
    Map getDeptList(String name, List<String> jurisdictions, Pageable pageable);

    List<Dept> findAllDepts();

    List<Dept> getDeptAllAndJurisdictionConfig(List<Long> deptIds);
}
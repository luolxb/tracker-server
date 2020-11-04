package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.dto.DeptDTO;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhc
 * @since 2019-04-16
 */
@Repository
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    List<DeptDTO> queryAll(@Param("dept") DeptDTO dept, @Param("deptIds") Set<Long> deptIds);

    Set<Dept> findByRoles(@Param("roles") List<Role> roles);

    List<Dept> findByCode(@Param("code") String code);

    List<Dept> getDeptAll();

    /**
     * 列表
     * @param page
     * @param name
     * @param jurisdictions
     * @return
     */
    Page<Dept> selectByPage(Page<Dept> page, @Param("name") String name, @Param("jurisdictions") List<String> jurisdictions);

    //需求变更后，辖区的phone不存储在dept表中，而是存在辖区配置-jurisdiction_config表中。所以要关联查询phone
    List<Dept> getDeptAllAndJurisdictionConfig(@Param("deptIds") List<Long> deptIds);
}

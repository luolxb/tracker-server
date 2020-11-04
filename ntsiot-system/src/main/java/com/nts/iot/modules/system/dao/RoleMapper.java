package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzm
 * @since 2019-04-16
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> queryAll(@Param("name") String name, @Param("depts") List<String> depts);

    Page<Role> selectPage(Page<Role> page, @Param("name") String name, @Param("depts") List<String> depts);

    Role selectOne(@Param("id") Long id, @Param("depts") List<String> depts);


    List<Role> findByName(@Param("name") String name);


    List<Role> findByUserId(@Param("id")Long id);

}

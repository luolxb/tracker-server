package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.dto.PermissionDTO;
import com.nts.iot.modules.system.model.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    List<PermissionDTO> queryAll(@Param("name") String name);

    List<Permission> findByRoles(@Param("roles") List<Role> roles);

}

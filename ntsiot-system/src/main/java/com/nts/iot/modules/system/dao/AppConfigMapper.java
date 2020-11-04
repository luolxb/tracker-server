package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.AppConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


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
public interface AppConfigMapper extends BaseMapper<AppConfig> {

    /**
     * 根据辖区获取小程序配置信息
     * @param deptId
     * @return
     */
    AppConfig getAppConfigByDeptId(@Param("deptId") Long deptId);

    Integer getCountByDeptId(@Param("deptId") Long deptId);

    Integer getCountByDeptIdAndAppId(@Param("appId") String appId, @Param("deptId") Long deptId);
}

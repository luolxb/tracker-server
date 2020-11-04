package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.JurisdictionConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface JurisdictionConfigurationMapper extends BaseMapper<JurisdictionConfiguration> {

    /**
     * 根据辖区编号 获得辖区名称
     *
     * @param jurisdiction
     * @return
     */
    JurisdictionConfiguration getJurisdictionName(@Param("jurisdiction") Long jurisdiction);

    /**
     * 获得辖区配置对象
     *
     * @param jurisdiction
     * @return
     */
    JurisdictionConfiguration getJurisdictionConfiguration(@Param("jurisdiction") Long jurisdiction);
}

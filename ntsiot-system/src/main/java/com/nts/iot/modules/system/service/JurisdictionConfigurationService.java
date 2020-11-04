package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.DeptDto;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.JurisdictionConfiguration;
import org.springframework.cache.annotation.CacheConfig;


@CacheConfig(cacheNames = "JurisdictionConfiguration")
public interface JurisdictionConfigurationService  extends IService<JurisdictionConfiguration> {

    /**
     * 获得辖区配置对象
     *
     * @param jurisdiction
     * @return
     */
    JurisdictionConfiguration getJurisdictionConfiguration(Long jurisdiction);

    DeptDto getDeptInfoById(Dept dept);
}

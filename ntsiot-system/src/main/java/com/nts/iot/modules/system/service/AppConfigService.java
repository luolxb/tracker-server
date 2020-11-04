package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.AppConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jie
 * @date 2019-03-25
 */
public interface AppConfigService extends IService<AppConfig> {
    /**
     * 获取默认小程序配置信息
     * @return
     */
    AppConfig getDefaultAppConfig();

    /**
     * 根据辖区获取小程序配置信息
     * @param deptId
     * @return
     */
    AppConfig getAppConfigByDeptId(Long deptId);

    /**
     * 保存
     * @param appConfig
     */
    @Transactional(rollbackFor = Exception.class)
    void saveAppConfig(AppConfig appConfig, Long deptId);

    /**
     * 编辑
     * @param appConfig
     */
    @Transactional(rollbackFor = Exception.class)
    void updateAppConfig(AppConfig appConfig);

    Integer getCountByDeptId(Long deptId);

    Integer getCountByDeptIdAndAppId(String appId, Long deptId);

    void initAppConfig();

}
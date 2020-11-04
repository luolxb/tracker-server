package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.AppModuleDto;
import com.nts.iot.modules.system.model.AppModule;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jie
 * @date 2019-03-25
 */
public interface AppModuleService extends IService<AppModule> {

    /**
     * 保存
     * @param appModule
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer saveAppModule(AppModule appModule);

    List<AppModule> getListByDeptId(Long deptId);

    List<AppModuleDto> getModuleDtosByDeptId(Long deptId);
}
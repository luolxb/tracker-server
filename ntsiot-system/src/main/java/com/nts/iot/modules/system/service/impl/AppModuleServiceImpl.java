package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.AppModuleMapper;
import com.nts.iot.modules.system.dto.AppModuleDto;
import com.nts.iot.modules.system.model.AppModule;
import com.nts.iot.modules.system.model.DeptAppRela;
import com.nts.iot.modules.system.service.AppModuleService;
import com.nts.iot.modules.system.service.DeptAppRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2019-03-25
 */
@Service
public class AppModuleServiceImpl extends ServiceImpl<AppModuleMapper, AppModule> implements AppModuleService {


    @Autowired
    DeptAppRelaService deptAppRelaService;

    @Override
    public Integer saveAppModule(AppModule appModule) {
        appModule.setCreateTime(System.currentTimeMillis());
        return baseMapper.insert(appModule);
    }

    @Override
    public List<AppModule> getListByDeptId(Long deptId) {
        return baseMapper.getListByDeptId(deptId);
    }

    @Override
    public List<AppModuleDto> getModuleDtosByDeptId(Long deptId) {
        List<AppModuleDto> appModuleDtos = new ArrayList<>();
        // 根据辖区id 查询app 权限 appPermission
        QueryWrapper<DeptAppRela> relaWrapper = new QueryWrapper<>();
        relaWrapper.in("dept_id",deptId);
        List<DeptAppRela> deptAppRelas = deptAppRelaService.list(relaWrapper);
        if (deptAppRelas!=null && deptAppRelas.size() > 0){
            deptAppRelas.forEach(p->{
                AppModule appModule = this.getById(p.getAppModuleId());
                if (appModule!=null){
                    // 加入权限code
                    AppModuleDto appModuleDto = new AppModuleDto();
                    appModuleDto.setCode(appModule.getName());
                    appModuleDto.setTitle(appModule.getAlias());
                    appModuleDtos.add(appModuleDto);
                }
            });
        }
        return appModuleDtos;
    }
}
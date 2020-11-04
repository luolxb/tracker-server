package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.system.dao.AppConfigMapper;
import com.nts.iot.modules.system.model.AppConfig;
import com.nts.iot.modules.system.model.AppDeptRela;
import com.nts.iot.modules.system.service.AppConfigService;
import com.nts.iot.modules.system.service.AppDeptRelaService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2019-03-25
 */
@Service
public class AppConfigerviceImpl extends ServiceImpl<AppConfigMapper, AppConfig> implements AppConfigService {

    @Autowired
    private AppDeptRelaService appDeptRelaService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public AppConfig getDefaultAppConfig() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("is_default", "0");
        List<AppConfig> list = baseMapper.selectByMap(columnMap);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public AppConfig getAppConfigByDeptId(Long deptId) {
        int count = appDeptRelaService.getByDeptId(deptId);
        if (count > 0) {
            // 存在则根据辖区查找
            return baseMapper.getAppConfigByDeptId(deptId);
        } else {
            // 不存在，查默认
            return this.getDefaultAppConfig();
        }
    }

    @Override
    public void updateAppConfig(AppConfig appConfig) {
        baseMapper.updateById(appConfig);
    }

    @Override
    public void saveAppConfig(AppConfig appConfig, Long deptId) {
        // 默认情况
        if ("0".equals(appConfig.getIsDefault())) {
            // 小程序、部门关系表
            appDeptRelaService.deleteByDeptId(deptId);
            this.saveAppDeptRela(appConfig.getId(), deptId);
        } else {
            // 判断选择否的时候，appid和默认一样的情况 TODO
            if (!appConfig.getAppId().equals(this.getDefaultAppConfig().getAppId())) {
                // 小程序配置表
                this.deleteAppConfigById(appConfig.getId());
                baseMapper.insert(appConfig);
            }
            // 小程序、部门关系表
            appDeptRelaService.deleteByDeptId(deptId);
            this.saveAppDeptRela(appConfig.getId(), deptId);
        }
    }

    /**
     * 小程序、部门关系表
     * @param appId
     * @param deptId
     */
    private void saveAppDeptRela(Long appId, Long deptId) {
        AppDeptRela appDeptRela = new AppDeptRela();
        appDeptRela.setDeptId(deptId);
        appDeptRela.setAppId(appId);
        appDeptRelaService.saveOrUpdate(appDeptRela);
    }

    @Override
    public Integer getCountByDeptId(Long deptId) {
        return baseMapper.getCountByDeptId(deptId);
    }

    @Override
    public Integer getCountByDeptIdAndAppId(String appId, Long deptId) {
        return baseMapper.getCountByDeptIdAndAppId(appId, deptId);
    }

    /**
     * 根据主键删除 （不包括默认）
     * @param id
     */
    private void deleteAppConfigById(Long id) {
        QueryWrapper<AppConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.ne("is_default", "0");
        this.remove(queryWrapper);
    }

    @Override
    public void initAppConfig() {
        QueryWrapper<AppConfig> query = new QueryWrapper<>();
        List<AppConfig> appConfigs = this.list(query);
        if (appConfigs != null && appConfigs.size() > 0) {
            for (AppConfig appConfig:appConfigs) {
                if (appConfig.getAppId()!=null){
                    Map<String,Object> wxMaMap = new HashMap<>();
                    wxMaMap.put("appId",appConfig.getAppId());
                    wxMaMap.put("secret",appConfig.getSecret());
                    wxMaMap.put("token",appConfig.getToken());
                    wxMaMap.put("aesKey",appConfig.getAesKey());
                    redisUtil.addRedis(RedisKey.WX_MA_KEY + appConfig.getAppId(), JsonUtil.getJson(wxMaMap));
                }
            }
        }
    }
}
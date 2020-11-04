package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.AppConfig;
import com.nts.iot.modules.system.model.AppModule;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.AppConfigService;
import com.nts.iot.modules.system.service.AppModuleService;
import com.nts.iot.modules.system.service.DeptAppRelaService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2019-03-25
 */
@RestController
@RequestMapping("api")
public class AppConfigController extends JwtBaseController {


    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AppModuleService appModuleService;

    @Autowired
    private DeptAppRelaService deptAppRelaService;


    /**
     * 获取默认小程序配置
     * @param user
     * @return
     */
    @GetMapping(value = "/appConfig")
    public ResponseEntity getDefaultAppConfig(@ModelAttribute("user") User user) {
        return new ResponseEntity(appConfigService.getDefaultAppConfig(), HttpStatus.OK);
    }

    /**
     * 根据辖区获取小程序配置
     * @param user
     * @return
     */
    @GetMapping(value = "/appConfig/deptId")
    public ResponseEntity getAppConfigByDeptId(@ModelAttribute("user") User user) {
        return new ResponseEntity(appConfigService.getAppConfigByDeptId(null), HttpStatus.OK);
    }

    /**
     * 编辑小程序配置
     * @param appConfig
     * @return
     */
    @Log("编辑小程序配置")
    @PutMapping(value = "/appConfig/edit")
    public ResponseEntity updateAppConfig(@RequestBody AppConfig appConfig) {
        // 编辑时默认否
        appConfig.setIsDefault("1");
        appConfigService.updateAppConfig(appConfig);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 保存小程序配置
     * @param appConfig
     * @return
     */
    @Log("保存小程序配置")
    @PutMapping(value = "/appConfig/save")
    public ResponseEntity saveAppConfig(@RequestBody AppConfig appConfig, @ModelAttribute("user") User user) {
        appConfigService.saveAppConfig(appConfig, null);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 校验小程序是否配置
     * @param user
     * @return
     */
    @PutMapping(value = "/appConfig/checkAppConfig")
    public ResponseEntity checkAppConfig(@RequestBody AppConfig appConfig, @ModelAttribute("user") User user) {
        Map<String, String> map = new HashMap<>();
        if ("0".equals(appConfig.getIsDefault())) {
            // “是”的情况
            int cnt = appConfigService.getCountByDeptId(null);
            if (cnt > 0) {
                map .put("errorMsg", "不可修改默认配置");
            }
        } else {
            // “否”的情况
            AppConfig defaulConfig = appConfigService.getDefaultAppConfig();
            String defaultAppId = "";
            if (defaulConfig != null) {
                // 默认appid
                defaultAppId = defaulConfig.getAppId();
            }
            // “否”的情况下，appid和默认appid一样的情况
            if (appConfig.getAppId().equals(defaultAppId)) {
                int cnt = appConfigService.getCountByDeptId(null);
                if (cnt > 0) {
                    map.put("errorMsg", "不可修改默认配置");
                }
            } else {
                int cnt = appConfigService.getCountByDeptIdAndAppId(appConfig.getAppId(), null);
                if (cnt > 0) {
                    map.put("errorMsg", "该APPID已存在！");
                }
            }
        }
//        return new ResponseEntity(appConfigService.getCountByDeptId(appId, user.getDeptId()), HttpStatus.OK);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 小程序模块新增
     * @param appModule
     * @param user
     * @return
     */
    @Log("小程序模块新增")
    @PostMapping(value = "/appModule/add")
    @PreAuthorize("hasAnyRole('ADMIN','APPMODULE_ALL','APPMODULE_CREATE')")
    public ResponseEntity saveAppModule(@RequestBody AppModule appModule, @ModelAttribute("user") User user) {
        return new ResponseEntity(appModuleService.saveAppModule(appModule),HttpStatus.OK);
    }

    /**
     * 获取小程序模块列表
     * @return
     */
    @Log("获取小程序模块查询")
    @GetMapping(value = "/appModules")
    @PreAuthorize("hasAnyRole('ADMIN','APPMODULE_ALL','APPMODULE_DELETE')")
    public ResponseEntity getAppModuleList() {
        List<Map<String,Object>> list = new LinkedList<>();
        List<AppModule> modules = appModuleService.list();
        modules.forEach(it -> {
            if (it != null) {
                Map<String, Object> pMap = new HashMap<>();
                pMap.put("id", it.getId());
                pMap.put("label", it.getAlias());
                pMap.put("value", it.getName());
                list.add(pMap);
            }
        });
        return new ResponseEntity(list,HttpStatus.OK);
    }

    @Log("修改角色权限")
    @PutMapping(value = "/appModule/edit")
    @PreAuthorize("hasAnyRole('ADMIN','APPMODULE_ALL','APPMODULE_EDIT')")
    public ResponseEntity updateAppModule(@RequestBody Dept dept) {
        deptAppRelaService.updateDeptAppRela(dept);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("小程序模块删除")
    @DeleteMapping("/appModule/{id}")
    public ResponseEntity deleteModule(@PathVariable Long id) {
        return new ResponseEntity(appModuleService.removeById(id), HttpStatus.OK);
    }
    @Log("编辑小程序模块")
    @PutMapping("/editAppModule")
    public ResponseEntity editModule(@RequestBody AppModule appModule) {
        return new ResponseEntity(appModuleService.updateById(appModule), HttpStatus.OK);
    }

}
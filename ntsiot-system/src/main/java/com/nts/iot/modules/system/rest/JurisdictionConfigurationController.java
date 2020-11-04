package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.JurisdictionConfiguration;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.JurisdictionConfigurationService;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cej@rnstec.com
 * @Description 辖区配置controller
 * @Date 2019-05-06 14：30
 * @Version: 1.0
 */
@RestController
@RequestMapping("api")
public class JurisdictionConfigurationController extends JwtBaseController {

    @Autowired
    private DataScope dataScope;

    @Autowired
    private JurisdictionConfigurationService jurisdictionConfigurationService;

    /**
     * 回显
     *
     * @return
     */
    @GetMapping(value = "/getJurisdictionConfiguration")
    @PreAuthorize("hasAnyRole('ADMIN','JURISDICTION_CONFIGURATION_ALL','JURISDICTION_CONFIGURATION_SELECT')")
    public ResponseEntity getJurisdictionConfiguration(String deptId) {
//        Long jurisdiction = 1L;
        // 获得辖区编号
//        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        JurisdictionConfiguration jurisdictionConfiguration = new JurisdictionConfiguration();
        if (deptId!=null){
            jurisdictionConfiguration = jurisdictionConfigurationService.getJurisdictionConfiguration(Long.valueOf(deptId));
            if (jurisdictionConfiguration==null){
                jurisdictionConfiguration =  new JurisdictionConfiguration();
            }
        }
        return  new ResponseEntity(jurisdictionConfiguration, HttpStatus.OK);
    }

    /**
     * 新增 or 修改
     *
     * @param jurisdictionConfiguration
     * @return
     */
    @PostMapping(value = "/updateJurisdictionConfiguration")
    @PreAuthorize("hasAnyRole('ADMIN','JURISDICTION_CONFIGURATION_ALL','JURISDICTION_CONFIGURATION_OPERATION')")
    public ResponseEntity create(@Validated @RequestBody JurisdictionConfiguration jurisdictionConfiguration) {
        // 获得辖区编号
//        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdictionConfiguration.setJurisdiction(list.get(0));
//        }else{
//            jurisdictionConfiguration.setJurisdiction(1L);
//        }
        if (jurisdictionConfiguration != null && jurisdictionConfiguration.getId() != null) {
            jurisdictionConfigurationService.updateById(jurisdictionConfiguration);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(jurisdictionConfigurationService.save(jurisdictionConfiguration), HttpStatus.CREATED);
        }
    }

    /**
     * 获取辖区信息
     * @param user
     * @return
     */
    @GetMapping(value = "/dept/get")
//    @PreAuthorize("hasAnyRole('ADMIN','JURISDICTION_CONFIGURATION_ALL','JURISDICTION_CONFIGURATION_SELECT')")
    public ResponseEntity getDeptInfo(@ModelAttribute("user") User user) {
        return  new ResponseEntity(jurisdictionConfigurationService.getJurisdictionConfiguration(null), HttpStatus.OK);
    }

}

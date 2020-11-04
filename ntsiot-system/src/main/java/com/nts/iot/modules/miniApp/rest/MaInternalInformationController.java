package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.miniApp.model.InternalInformation;
import com.nts.iot.modules.miniApp.service.InternalInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内部信息上传
 */

@RestController
@RequestMapping("ma")
public class MaInternalInformationController {

    @Autowired
    private InternalInformationService internalInformationService;


    /**
     * 添加内部信息上传
     * @param internalInformation
     * @return
     */
    @PostMapping(value = "/internalInformation/save")
    public String save(@RequestBody InternalInformation internalInformation) {
        String result;
        try {
            internalInformationService.saveInternalInformation(internalInformation);
            result = "SUCCESS";
        } catch (Exception e) {
            result = "ERROR";
            e.printStackTrace();
        }
        return result;
    }
}

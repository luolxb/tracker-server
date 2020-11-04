package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.miniApp.dto.HoseOwnerInfoDto;
import com.nts.iot.modules.system.service.HouseRecordSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 14:29
 * @Description:
 */
@RestController
@RequestMapping("ma")
public class MaHouseController {

    @Autowired
    HouseRecordSerivice houseRecordSerivice;

    /**
     * 获取列表
     * @param  formData
     * @return
     */
    @PostMapping("/house/add")
    public ResponseEntity queryAll(@RequestBody HoseOwnerInfoDto formData){
        houseRecordSerivice.saveForm(formData);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}

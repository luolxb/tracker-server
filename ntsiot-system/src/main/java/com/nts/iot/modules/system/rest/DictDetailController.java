package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.service.DictDetailService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dto.DictDetailDTO;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author jie
* @date 2019-04-10
*/
@RestController
@RequestMapping("api")
public class DictDetailController {

    @Autowired
    private DictDetailService dictDetailService;

    private static final String ENTITY_NAME = "dictDetail";

    @Log("查询字典详情")
    @GetMapping(value = "/dictDetail")
    public ResponseEntity getDictDetails(DictDetailDTO resources, Pageable pageable){
        return new ResponseEntity(dictDetailService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增字典详情")
    @PostMapping(value = "/dictDetail")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody DictDetailDTO resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(dictDetailService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改字典详情")
    @PutMapping(value = "/dictDetail")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_EDIT')")
    public ResponseEntity update( @RequestBody DictDetailDTO resources){
        dictDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典详情")
    @DeleteMapping(value = "/dictDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        dictDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }



}
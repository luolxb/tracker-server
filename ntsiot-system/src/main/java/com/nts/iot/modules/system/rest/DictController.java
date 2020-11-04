package com.nts.iot.modules.system.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.modules.system.model.Dict;
import com.nts.iot.modules.system.service.DictService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dto.DictDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author jie
* @date 2019-04-10
*/
@RestController
@RequestMapping("api")
public class DictController {

    @Autowired
    private DictService dictService;

    private static final String ENTITY_NAME = "dict";

    @Log("查询字典")
    @GetMapping(value = "/dict")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_SELECT')")
    public ResponseEntity getDicts(DictDTO resources, Pageable pageable){
        return new ResponseEntity(dictService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增字典")
    @PostMapping(value = "/dict")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_CREATE')")
    public ResponseEntity create(@RequestBody Dict resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(dictService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改字典")
    @PutMapping(value = "/dict")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_EDIT')")
    public ResponseEntity update(@RequestBody DictDTO resources){
        Dict dict = dictService.getById(resources.getId());
        if (dict!=null){
            dict.setName(resources.getName());
            dict.setRemark(resources.getRemark());
            dictService.update(dict);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典")
    @DeleteMapping(value = "/dict/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        dictService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    // 根据字典名查询字典字段
    private Dict findDict(String dictName){
        QueryWrapper<Dict> dictWrapper = new QueryWrapper<>();
        dictWrapper.eq("name",dictName);
        List<Dict> dictList = dictService.list(dictWrapper);
        if (dictList ==null || dictList.size() == 0){
            return null;
        }else {
            return dictList.get(0);
        }
    }
}
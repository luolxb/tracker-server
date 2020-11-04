package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.ArticleNumber;
import com.nts.iot.modules.system.service.ArticleNumberService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.config.DataScope;
import com.nts.iot.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ArticleNumberController {
    @Autowired
    private ArticleNumberService articleNumberService;
    @Autowired
    private DataScope dataScope;

    private static final String ENTITY_NAME = "articleNumber";

    @Log("物品数量查询")
    @GetMapping(value = "/articleNumber")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_NUMBER_ALL','ARTICLE_NUMBER_SELECT')")
    public ResponseEntity getCheckPoint(Pageable pageable, @RequestParam(required = false) String name) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(articleNumberService.queryAll(pageable, name, list), HttpStatus.OK);
    }

    /**
     * 新增
     *
     * @param articleNumber
     * @return
     */
    @Log("物品数量新增")
    @PostMapping(value = "/articleNumber/add")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_NUMBER_ALL','ARTICLE_NUMBER_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody ArticleNumber articleNumber) {
        if (articleNumber.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        articleNumber.setCreateTime(System.currentTimeMillis());
        return new ResponseEntity(articleNumberService.save(articleNumber), HttpStatus.CREATED);
    }

    /**
     * 修改
     *
     * @param articleNumber
     * @return
     */
    @Log("物品数量修改")
    @PutMapping(value = "/articleNumber/edit")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_NUMBER_ALL','ARTICLE_NUMBER_EDIT')")
    public ResponseEntity update(@RequestBody ArticleNumber articleNumber) {
        articleNumberService.updateById(articleNumber);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Log("物品数量删除")
    @DeleteMapping(value = "/articleNumber/del/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_NUMBER_ALL','ARTICLE_NUMBER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        articleNumberService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
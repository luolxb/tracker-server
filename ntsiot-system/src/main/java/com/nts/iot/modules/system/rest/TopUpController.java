package com.nts.iot.modules.system.rest;


import com.nts.iot.modules.system.model.TopUp;
import com.nts.iot.modules.system.service.TopUpService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
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
import java.util.Set;

/**
 * 充值中心
 */
@RestController
@RequestMapping("api")
public class TopUpController extends JwtBaseController {

    @Autowired
    private DataScope dataScope;

    @Autowired
    private TopUpService topUpService;


    private static final String ENTITY_NAME = "topUp";

    /**
     * 查询
     *
     * @param money
     * @param pageable
     * @return
     */
    @Log("充值列表查询")
    @GetMapping(value = "/topUp")
    @PreAuthorize("hasAnyRole('ADMIN','TOP_UP_ALL','TOP_UP_SELECT')")
    public ResponseEntity getCheckPoint(Pageable pageable, @RequestParam(required = false) String money, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        Long jurisdiction = null;
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        List<Long> list = new ArrayList(deptIds);
        if (list != null && list.size() > 0) {
            jurisdiction = list.get(0);
        }
        return new ResponseEntity(topUpService.queryAll(pageable, money, startTime, endTime, jurisdiction), HttpStatus.OK);
    }

    /**
     * 新增
     *
     * @param topUp
     * @return
     */
    @Log("新增充值")
    @PostMapping(value = "/topUp/add")
    @PreAuthorize("hasAnyRole('ADMIN','TOP_UP_ALL','TOP_UP_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody TopUp topUp) {
        if (topUp.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        topUp.setCreateTime(System.currentTimeMillis());
        return new ResponseEntity(topUpService.save(topUp), HttpStatus.CREATED);
    }

    /**
     * 修改
     *
     * @param topUp
     * @return
     */
    @Log("修改充值")
    @PutMapping(value = "/topUp/edit")
    @PreAuthorize("hasAnyRole('ADMIN','TOP_UP_ALL','TOP_UP_EDIT')")
    public ResponseEntity update(@RequestBody TopUp topUp) {
        topUpService.updateById(topUp);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Log("删除充值")
    @DeleteMapping(value = "/topUp/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TOP_UP_ALL','TOP_UP_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        topUpService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

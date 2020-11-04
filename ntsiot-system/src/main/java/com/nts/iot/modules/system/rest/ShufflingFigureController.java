package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.ShufflingFigure;
import com.nts.iot.modules.system.service.ShufflingFigureService;
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


@RestController
@RequestMapping("api")
public class ShufflingFigureController extends JwtBaseController {

    @Autowired
    private ShufflingFigureService shufflingFigureService;

    @Autowired
    private DataScope dataScope;

    private static final String ENTITY_NAME = "shufflingFigure";


    /**
     * 查询
     *
     * @param name
     * @param pageable
     * @return
     */
    @Log("查询轮播图")
    @GetMapping(value = "/shufflingFigure")
    @PreAuthorize("hasAnyRole('ADMIN','SHUFFLING_FIGURE_ALL','SHUFFLING_FIGURE_SELECT')")
    public ResponseEntity getCheckPoint(Pageable pageable, @RequestParam(required = false) String name, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(shufflingFigureService.queryAll(pageable, name, startTime, endTime, list), HttpStatus.OK);
    }

    /**
     * 新增
     *
     * @param shufflingFigure
     * @return
     */
    @Log("新增轮播图")
    @PostMapping(value = "/shufflingFigure/add")
    @PreAuthorize("hasAnyRole('ADMIN','SHUFFLING_FIGURE_ALL','SHUFFLING_FIGURE_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody ShufflingFigure shufflingFigure) {
        if (shufflingFigure.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        shufflingFigure.setJurisdiction(shufflingFigure.getJurisdiction());
        shufflingFigure.setCreateTime(System.currentTimeMillis());
        return new ResponseEntity(shufflingFigureService.save(shufflingFigure), HttpStatus.CREATED);
    }

    /**
     * 修改
     *
     * @param shufflingFigure
     * @return
     */
    @Log("修改轮播图")
    @PutMapping(value = "/shufflingFigure/edit")
    @PreAuthorize("hasAnyRole('ADMIN','SHUFFLING_FIGURE_ALL','SHUFFLING_FIGURE_EDIT')")
    public ResponseEntity update(@RequestBody ShufflingFigure shufflingFigure) {
        shufflingFigure.setJurisdiction(shufflingFigure.getJurisdiction());
        shufflingFigureService.updateById(shufflingFigure);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Log("删除轮播图")
    @DeleteMapping(value = "/shufflingFigure/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SHUFFLING_FIGURE_ALL','SHUFFLING_FIGURE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        shufflingFigureService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}

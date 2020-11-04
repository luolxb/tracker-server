package com.nts.iot.modules.system.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dto.IconDto;
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
public class CheckPointController extends JwtBaseController {

    @Autowired
    private CheckPointService checkPointService;
    @Autowired
    private CheckPointTaskService checkPointTaskService;
    @Autowired
    private DataScope dataScope;
    @Autowired
    private DictService dictService;
    @Autowired
    private TaskPointService taskPointService;
    @Autowired
    private RealCheckPointService realCheckPointService;

    private static final String ENTITY_NAME = "checkPoint";

    /**
     * 查询所有必到点分页
     *
     * @param name
     * @param pageable
     * @return
     */
    @Log("必到点查询")
    @GetMapping(value = "/checkPoint")
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL','POINT_SELECT')")
    public ResponseEntity getCheckPoint(@RequestParam(required = false) String name, Pageable pageable) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(checkPointService.queryAll(name, pageable, list), HttpStatus.OK);
    }

    /**
     * 新增
     *
     * @param checkPoint
     * @return
     */
    @Log("必到点新增")
    @PostMapping(value = "/checkPoint")
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL','POINT_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody CheckPoint checkPoint, @ModelAttribute("user") User user) {
        if (checkPoint.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(checkPointService.create(checkPoint, user), HttpStatus.CREATED);
    }

    /**
     * 修改必到点
     *
     * @return
     */
    @Log("必到点修改")
    @GetMapping(value = "/checkPoint/get/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL','POINT_EDIT')")
    public ResponseEntity getById(@PathVariable Long id) {
        CheckPoint checkPoint = checkPointService.getById(id);
        checkPoint.setPointTaskList(checkPointTaskService.selectTask(id));
        return new ResponseEntity(checkPoint, HttpStatus.OK);
    }


    /**
     * 修改
     *
     * @param checkPoint
     * @return
     */
    @PutMapping(value = "/checkPoint")
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL','POINT_EDIT')")
    public ResponseEntity update(@RequestBody CheckPoint checkPoint, @ModelAttribute("user") User user) {
        checkPointService.update(checkPoint, user);

        //同时更新巡逻模版中的必到点坐标
        QueryWrapper<TaskPoint> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("point_id",checkPoint.getId());
        TaskPoint taskPoint=new TaskPoint();
        taskPoint.setLatitude(checkPoint.getLatitude());
        taskPoint.setLongitude(checkPoint.getLongitude());
        taskPointService.update(taskPoint,queryWrapper);
        //同时更新巡逻实例中的必到点坐标
        QueryWrapper<RealCheckPoint> realCheckPointQueryWrapper=new QueryWrapper<>();
        realCheckPointQueryWrapper.eq("point_id",checkPoint.getId());
        RealCheckPoint realCheckPoint=new RealCheckPoint();
        realCheckPoint.setLatitude(checkPoint.getLatitude());
        realCheckPoint.setLongitude(checkPoint.getLongitude());
        realCheckPoint.setScope(checkPoint.getScope());
        realCheckPointService.update(realCheckPoint,realCheckPointQueryWrapper);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Log("必到点删除")
    @DeleteMapping(value = "/checkPoint/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL','POINT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        checkPointService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获得任务List
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/checkPointTask")
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL')")
    public List<CheckPointTask> getCheckPointTask(@RequestParam(required = false) Long id) {
        return checkPointTaskService.selectTask(id);
    }

    /**
     * 根据辖区查找必到点
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/checkPoints/dept")
    @PreAuthorize("hasAnyRole('ADMIN','POINT_ALL')")
    public ResponseEntity getCheckPoints(@ModelAttribute("user") User user) {
//        return new ResponseEntity(checkPointService.getCheckPointsByDept(user.getDeptId()), HttpStatus.OK);
        return null;
    }

    /**
     * 查图标
     *
     * @return
     */
    @GetMapping(value = "/getIcon")
    public ResponseEntity getIcon() {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
        if (list != null && list.size() > 0) {
            jurisdiction = list.get(0);
        }
        List<IconDto> iconDtoList = checkPointService.selectIcon(jurisdiction);
        return new ResponseEntity(iconDtoList, HttpStatus.OK);
    }


    /**
     * 查字典
     *
     * @return
     */
    @GetMapping(value = "/getTaskType")
    public ResponseEntity getTaskType() {
        List<DictDetail> dictDetailList = checkPointService.selectTasKType();
        return new ResponseEntity(dictDetailList, HttpStatus.OK);
    }


}

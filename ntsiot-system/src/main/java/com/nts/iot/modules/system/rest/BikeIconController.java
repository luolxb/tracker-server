package com.nts.iot.modules.system.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.miniApp.dto.BikeDto;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.BikeIcon;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.BikeIconService;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
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
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/30 15:24
 * @Description:
 */
@RequestMapping("api")
@RestController
public class BikeIconController extends JwtBaseController {

    @Autowired
    private BikeIconService bikeIconService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private BikeManagerService bikeManagerService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 列表查询
     * @return
     */
    @Log("车辆图标查询")
    @GetMapping(value = "/bikeIcons")
    @PreAuthorize("hasAnyRole('ADMIN','BIKEICON_ALL','BIKEICON_DELETE')")
    public ResponseEntity list(String name, Pageable pageable, @ModelAttribute("user") User user){
        List<String> depts = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            depts.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(bikeIconService.queryAll(name, depts, pageable), HttpStatus.OK);
    }

    /**
     * 新增图标
     * @param bikeIcon
     * @param user
     * @return
     */
    @Log("车辆图标新增")
    @PostMapping(value = "/bikeIcon/add")
    @PreAuthorize("hasAnyRole('ADMIN','BIKEICON_ALL','BIKEICON_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody BikeIcon bikeIcon, @ModelAttribute("user") User user){
//        bikeIcon.setDept(user.getDeptId());
        return new ResponseEntity(bikeIconService.save(bikeIcon), HttpStatus.CREATED);
    }

    /**
     * 修改
     * @param bikeIcon
     * @return
     */
    @Log("车辆图标修改")
    @PutMapping(value = "/bikeIcon/edit")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','BIKEICON_ALL','BIKEICON_EDIT')")
    public ResponseEntity update(@RequestBody BikeIcon bikeIcon, @ModelAttribute("user") User user){
        //更新bike表图标
        Bike bike=new Bike();
        bike.setBikeIcon(bikeIcon.getBikeIcon());
        UpdateWrapper<Bike> bikeUpdateWrapper=new UpdateWrapper<>();
        bikeUpdateWrapper.eq("type",bikeIcon.getId());
        bikeManagerService.update(bike,bikeUpdateWrapper);

        //更新缓存图标
        Set<String> keys = redisUtil.keys(RedisKey.VECHILE_KEY);
        List<BikeDto> bikes = JsonUtil.jsonConvertList(new ArrayList<>(redisUtil.getObjectsByKeys(keys)).toString(), BikeDto.class);
        for (BikeDto bikeDto : bikes) {
            // 根据车类型过滤
            if (Long.parseLong(bikeDto.getType()) != bikeIcon.getId()) {
                continue;
            }
            bikeDto.setBikeIcon(bikeIcon.getBikeIcon());
            redisUtil.updateRedis(RedisKey.VECHILE_KEY + bikeDto.getBikeBarcode(), JSON.toJSONString(bikeDto));
        }
        return new ResponseEntity(bikeIconService.updateById(bikeIcon), HttpStatus.NO_CONTENT);
    }

//    /**
//     * 删除
//     * @param id
//     * @return
//     */
//    @DeleteMapping(value = "/bikeIcon/{id}")
//    @Transactional(rollbackFor = Exception.class)
//    @PreAuthorize("hasAnyRole('ADMIN','BIKEICON_ALL','BIKEICON_DELETE')")
//    public ResponseEntity delete(@PathVariable Long id){
//        return new ResponseEntity(bikeIconService.removeById(id),HttpStatus.OK);
//    }
}

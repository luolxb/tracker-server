package com.nts.iot.modules.system.rest;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.ConstantClass;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.miniApp.dto.BikeDto;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.service.*;
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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.nts.iot.constant.RedisKey.LIST_VECHILE_KEY;


/**
 * @Author zhc@rnstec.com
 * @Description 车辆管理
 * @Date 2019-05-05 10：11
 * @Version: 1.0
 */
@RestController
@RequestMapping("api")
public class BikeManagerController extends JwtBaseController {

    @Autowired
    private BikeManagerService bikeManagerService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeptService deptService;

    @Autowired
    private BikeLockService bikeLockService;

    @Autowired
    private LockService lockService;

    @Autowired
    private BikeIconService bikeIconService;

    @Autowired
    private OrderManagerService orderManagerService;

    @Autowired
    private MaUserService maUserService;

    @Autowired
    private JurisdictionConfigurationService jurisdictionConfigurationService;

    /**
     * 车辆列表查询
     *
     * @return
     */
    @Log("车辆列表查询")
    @GetMapping(value = "/bikes/all")
    @PreAuthorize("hasAnyRole('ADMIN','BIKE_ALL','BIKE_DELETE')")
    public ResponseEntity getList(@ModelAttribute("user") User user) {
        List<BikeDto> result = new ArrayList<>();
        Set<String> keys = redisUtil.keys(RedisKey.VECHILE_KEY);
        List<BikeDto> bikes = JsonUtil.jsonConvertList(new ArrayList<>(redisUtil.getObjectsByKeys(keys)).toString(), BikeDto.class);
        sortBikeBySeq(bikes);
        for (BikeDto bike : bikes) {
            // 根据辖区过滤
//            if (bike.getJurisdiction() != user.getDeptId()) {
//                continue;
//            }
            String bikeCode = bike.getBikeBarcode();
            if (bike.getBikeBarcode().length() > 7) {
                // 截取后7位
                bikeCode = bike.getBikeBarcode().substring(bike.getBikeBarcode().length() - 7, bike.getBikeBarcode().length());
            }
            //如果车辆绑定用户，则前端显示成 “车辆编号-用户名”
            if (StrUtil.isNotEmpty(bike.getUser())) {
                bike.setUsername(bikeCode + "-" + bike.getUser());
            } else {
                bike.setUsername(bikeCode);
            }
            result.add(bike);
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 车辆列表查询
     *
     * @return
     */
    @Log("车辆列表查询")
    @GetMapping(value = "/bikes")
    @PreAuthorize("hasAnyRole('ADMIN','BIKE_ALL','BIKE_DELETE')")
    public ResponseEntity getBikes(Bike bike, Pageable pageable, @ModelAttribute("user") User user) {
        List<String> jurisdictions = new ArrayList<>();
        if (bike.getJurisdiction() != null) {
            jurisdictions.add(String.valueOf(bike.getJurisdiction()));
        } else {
            List<Dept> deptList = deptService.findByUserRole(user);
            for (int i = 0; i < deptList.size(); i++) {
                Dept dept = deptList.get(i);
                jurisdictions.add(String.valueOf(dept.getId()));
            }
        }

        return new ResponseEntity(bikeManagerService.queryAll(pageable, bike, jurisdictions), HttpStatus.OK);
    }

    /**
     * 新增车辆信息
     *
     * @param bike
     * @return
     */
    @Log("新增车辆信息")
    @PostMapping(value = "/bikes/add")
    @PreAuthorize("hasAnyRole('ADMIN','BIKE_ALL','BIKE_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody Bike bike) {
        // 获取车辆keys
        List<String> bikeList = new ArrayList<>();
        bikeList = JsonUtil.jsonConvertList(redisUtil.getData(LIST_VECHILE_KEY), String.class);
        if (bikeList == null) {
            bikeList = new ArrayList<>();
        }
        bike.setCreateTime(System.currentTimeMillis());
        //正常使用
        bike.setStatus(ConstantClass.BIKE_STATUS_00);
        BikeIcon bikeIcon = bikeIconService.findById(Long.valueOf(bike.getType()));
        if (bikeIcon != null) {
            bike.setBikeIcon(bikeIcon.getBikeIcon());
        }
        if (bikeManagerService.save(bike)) {
            //保存车、锁关联信息
            bikeLockService.create(bike.getBikeBarcode(), bike.getLockBarcode());
        }
        redisUtil.addRedis(RedisKey.VECHILE_KEY + bike.getBikeBarcode(), JSON.toJSONString(bike));
        /* 根据车锁存bike */
        redisUtil.addRedis(RedisKey.TRACKER_KEY + bike.getLockBarcode(), JSON.toJSONString(bike));
        bikeList.add(bike.getBikeBarcode());
        // 保存车辆编号list
        redisUtil.addRedis(LIST_VECHILE_KEY, JSON.toJSONString(bikeList));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 编辑车辆信息
     *
     * @param bike
     * @return
     */
    @Log("编辑车辆信息")
    @PutMapping(value = "/bikes/edit")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','BIKE_ALL','BIKE_EDIT')")
    public ResponseEntity update(@RequestBody Bike bike) {
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("bike_barcode",bike.getBikeBarcode());
        orderQueryWrapper.eq("status",2L);
        List<Order> order=orderManagerService.list(orderQueryWrapper);
        if(order!=null&&!order.isEmpty()){
            return new ResponseEntity(HttpStatus.LOCKED);
        }
        //把之前的智能锁状态改为未使用
        Bike oldBike=bikeManagerService.getById(bike.getId());
        lockService.updateStatusLock("0", oldBike.getLockBarcode());

        bike.setUpdateTime(System.currentTimeMillis());
        BikeIcon bikeIcon = bikeIconService.findById(Long.valueOf(bike.getType()));
        if (bikeIcon != null) {
            bike.setBikeIcon(bikeIcon.getBikeIcon());
        }
        if (bikeManagerService.updateById(bike)) {
            //修改关联表
            BikeLock bl = new BikeLock();
            bl = this.getBikeLock(bike.getBikeBarcode());
            bl.setLockBarcode(bike.getLockBarcode());
            bikeLockService.updateById(bl);
            lockService.updateStatusLock("1", bike.getLockBarcode());
        }
        redisUtil.updateRedis(RedisKey.VECHILE_KEY + bike.getBikeBarcode(), JSON.toJSONString(bike));
        /* 根据车锁修改bike */
        redisUtil.updateRedis(RedisKey.TRACKER_KEY + bike.getLockBarcode(), JSON.toJSONString(bike));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除车辆信息
     *
     * @param id
     * @return
     */
    @Log("删除车辆信息")
    @DeleteMapping(value = "/bikes/{id}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','BIKE_ALL','BIKE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //车辆信息
        Bike bike = bikeManagerService.getById(id);
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("bike_barcode",bike.getBikeBarcode());
        orderQueryWrapper.eq("status",2L);
        List<Order> order=orderManagerService.list(orderQueryWrapper);
        if(order!=null&&!order.isEmpty()){
            return new ResponseEntity(HttpStatus.LOCKED);
        }
        //关系表
        BikeLock bl = this.getBikeLock(bike.getBikeBarcode());
        //删除关系表
        if (bikeLockService.removeById(bl.getId())) {
            //更新车锁状态
            lockService.updateStatusLock("0", bl.getLockBarcode());
            //删除主表
            bikeManagerService.removeById(id);
        }
        redisUtil.deleteByKey(RedisKey.VECHILE_KEY + bike.getBikeBarcode());
        /* 根据车锁删除bike */
        redisUtil.deleteByKey(RedisKey.TRACKER_KEY + bike.getLockBarcode());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取监控车辆实时信息
     *
     * @return
     */
    @Log("监控车辆实时信息")
    @GetMapping(value = "/bikes/monitor")
    @PreAuthorize("hasAnyRole('ADMIN','BIKE_ALL','BIKE_MONITOR')")
    public ResponseEntity getBike(@ModelAttribute("user") User user) {
        List<BikeDto> result = new ArrayList<>();
        //获取车辆
        Set<String> keys = redisUtil.keys(RedisKey.VECHILE_KEY);
//        QueryWrapper<JurisdictionConfiguration> jurisdictionConfigurationQueryWrapper=new QueryWrapper<>();
//        jurisdictionConfigurationQueryWrapper.eq("jurisdiction",user.getDeptId());
//        JurisdictionConfiguration jurisdictionConfiguration=jurisdictionConfigurationService.getOne(jurisdictionConfigurationQueryWrapper);
        List<BikeDto> bikes = JsonUtil.jsonConvertList(new ArrayList<>(redisUtil.getObjectsByKeys(keys)).toString(), BikeDto.class);
        //车辆排序
        sortBikeBySeq(bikes);
        for (BikeDto bike : bikes) {
            // 根据辖区过滤
//            if (bike.getJurisdiction() != user.getDeptId()) {
//                continue;
//            }
            String bikeCode = bike.getBikeBarcode();
            bike.setBikeCode(bikeCode);
            if (bike.getBikeBarcode().length() > 7) {
                // 截取后7位
                bikeCode = bike.getBikeBarcode().substring(bike.getBikeBarcode().length() - 7, bike.getBikeBarcode().length());
            }
            QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("bike_barcode",bike.getBikeBarcode());
            queryWrapper.eq("status",2L);
            Order order=orderManagerService.getOne(queryWrapper);

//            //查询车辆是否指定单独显示小尾巴
//            QueryWrapper<Bike> bikeQueryWrapper=new QueryWrapper<>();
//            queryWrapper.eq("bike_barcode",bike.getBikeBarcode());
//            queryWrapper.eq("show_real_line","true");
//            Bike showBike=bikeManagerService.getOne(bikeQueryWrapper);
            //如果车辆有正在进行的订单，则前端显示成 “车辆编号-用户名；如果没订单但是车辆绑定用户，则前端显示成 “车辆编号-用户名”
            if(order!=null){
                MaUser maUser=maUserService.getById(order.getUserId());
                bike.setBikeBarcode(bikeCode + "-" + maUser.getName());
                bike.setPhone(maUser.getPhone());
            }else if (StrUtil.isNotEmpty(bike.getUser())) {
                bike.setBikeBarcode(bikeCode + "-" + bike.getUser());
            } else {
                bike.setBikeBarcode(bikeCode);
            }
//            if(showBike!=null){
//                bike.setShowRealLine(bike.getShowRealLine());
//                bike.setColor(bike.getColor());
//            }else{
//                bike.setShowRealLine(jurisdictionConfiguration.getShowRealLine());
//                bike.setColor(jurisdictionConfiguration.getColor());
//            }
            //获取车辆实时信息
            String lockKey = RedisKey.VECHILE_STATE + bike.getLockBarcode();
            CollectMessage collectMessage = JSON.parseObject(redisUtil.getData(lockKey), CollectMessage.class);
            bike.setCollectMessage(collectMessage);
            result.add(bike);
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    private void sortBikeBySeq(List<BikeDto> bikes) {
        if (CollectionUtils.isEmpty(bikes)) {
            return;
        }
        Collections.sort(bikes, (b1, b2) -> {
            long b1Seq = Objects.isNull(b1.getBikeSeq()) ? 0L : b1.getBikeSeq();
            long b2Seq = Objects.isNull(b2.getBikeSeq()) ? 0L : b2.getBikeSeq();
            return (int) (b2Seq - b1Seq);
        });
    }

    /**
     * 获取锁列表
     *
     * @return
     */
    @Log("获取锁列表")
    @GetMapping(value = "/locks")
    public ResponseEntity getLocks() {
        return new ResponseEntity(lockService.selectLock(), HttpStatus.OK);
    }

    /**
     * 获取车、锁关系信息
     *
     * @param bikeBarcode
     * @return
     */
    @GetMapping("/bikes/get/{bikeBarcode}")
    public BikeLock getBikeLock(@PathVariable String bikeBarcode) {
        QueryWrapper<BikeLock> query = new QueryWrapper<>();
        query.eq("bike_barcode", bikeBarcode);
        return bikeLockService.getOne(query);
    }

    /**
     * 查询历史轨迹
     *
     * @param bikeBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    @Log("查询历史轨迹")
    @GetMapping("/bikes/history")
    public ResponseEntity historicalTrack(String bikeBarcode, Long startTime, Long endTime) {
        return new ResponseEntity(bikeManagerService.historicalTrack(bikeBarcode, startTime, endTime), HttpStatus.OK);
    }

}

package com.nts.iot.modules.system.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.OrderManagerService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;


/**
 * @Author zhc@rnstec.com
 * @Description 订单管理
 * @Date 2019-05-05 10：11
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("api")
public class OrderManagerController extends JwtBaseController {

    @Autowired
    OrderManagerService orderManagerService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private DeptService deptService;


    @Log("订单查询")
    @GetMapping(value = "/orders")
    @PreAuthorize("hasAnyRole('ADMIN','ORDER_ALL','ORDER_DELETE')")
    public ResponseEntity getOrders(Order order, Pageable pageable, @ModelAttribute("user") User user) {
        List<String> jurisdictions = new ArrayList<>();
        if (order.getJurisdiction() != null) {
            jurisdictions.add(String.valueOf(order.getJurisdiction()));
        } else {
            List<Dept> deptList = deptService.findByUserRole(user);
            for (int i = 0; i < deptList.size(); i++) {
                Dept dept = deptList.get(i);
                jurisdictions.add(String.valueOf(dept.getId()));
            }
        }

        return new ResponseEntity(orderManagerService.queryAll(pageable, order, jurisdictions), HttpStatus.OK);
    }

    /**
     * 订单补偿
     *
     * @param
     * @return
     */
    @Log("订单补偿")
    @PutMapping(value = "/order/edit")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAnyRole('ADMIN','ORDER_ALL','ORDER_EDIT')")
    public ResponseEntity update(@RequestBody Order order) {
        orderManagerService.updateById(order);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 订单骑行轨迹
     *
     * @param orderId
     * @return
     */
    @Log("订单骑行轨迹")
    @GetMapping("/order/ridingTrack/{orderId}")
    public ResponseEntity getRidingTrack(@PathVariable String orderId) {
        return new ResponseEntity(orderManagerService.getRidingTrack(orderId), HttpStatus.OK);
    }

    /**
     * 后台关闭订单
     *
     * @param bikeBarcode
     * @param lockBarcode
     * @return
     */
    @Log("后台关闭订单")
    @GetMapping("/order/close")
    public Map<String, Object> closeOrder(String bikeBarcode, String lockBarcode) {
        Map<String, Object> result = new HashMap<>();
        result = orderManagerService.closeOrder(bikeBarcode, lockBarcode, 3L);
        log.info("OrderManagerController.closeOrder:" + JSON.toJSONString(result));
        return result;
    }


    /**
     * 人员排行榜pc
     *
     * @return
     */
    @GetMapping("ranking")
    public ResponseEntity ranking(@RequestParam String deptId,
                                  @RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam Long startTime,
                                  @RequestParam Long endTime,
                                  @RequestParam Integer count) throws ParseException {
//        Date date=new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String startTime=simpleDateFormat.format(date)+" 00:00:00";
//        String endTime=simpleDateFormat.format(date)+" 23:59:59";
//        Long startDate=s.parse(startTime).getTime();
//        Long endDate=s.parse(endTime).getTime();
        Map<String, Object> map = new HashMap<>();
        List<Long> jurisdictions = deptService.getSubDepts(Long.parseLong(deptId));
        Map rankByUsers = orderManagerService.rankByJurisdictionPc(new Page<>(pageNum, pageSize), jurisdictions, startTime, endTime,count);
        return new ResponseEntity(rankByUsers, HttpStatus.OK);
    }

    /**
     * 车辆排行榜pc
     *
     * @return
     */
    @GetMapping("/bikes/ranking")
    public ResponseEntity bikesRanking(@RequestParam String deptId,
                                       @RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam Long startTime,
                                       @RequestParam Long endTime) throws ParseException {
//        Date date=new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String startTime=simpleDateFormat.format(date)+" 00:00:00";
//        String endTime=simpleDateFormat.format(date)+" 23:59:59";
//        Long startDate=s.parse(startTime).getTime();
//        Long endDate=s.parse(endTime).getTime();
        Map<String, Object> map = new HashMap<>();
        List<Long> jurisdictions = deptService.getSubDepts(Long.parseLong(deptId));
        Map rankByBikes = orderManagerService.rankByBikeJurisdictionPc(new Page<>(pageNum, pageSize), jurisdictions, startTime, endTime);
        return new ResponseEntity(rankByBikes, HttpStatus.OK);
    }

}

package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.HouseRecordSerivice;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/9/16 15:03
 * @Description:
 */
@RestController
@RequestMapping("api")
public class HouseRecordController extends JwtBaseController {

    @Autowired
    private HouseRecordSerivice houseRecordSerivice;

    @Autowired
    private DeptService deptService;

    @Log("房屋出租列表查询")
    @RequestMapping("houseRental")
    @PreAuthorize("hasAnyRole('ADMIN','HOUSING_RENTAL_ALL')")
    public ResponseEntity getList(String owner, String phone, Pageable pageable, @ModelAttribute("user") User user) {
        List<String> depts = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            depts.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(houseRecordSerivice.queryAll(owner, phone, depts, pageable), HttpStatus.OK);
    }

    @Log("房屋出租统计")
    @GetMapping("/houseRentalCharts")
    public ResponseEntity getIllegalReport(@RequestParam(required = false) Long startTime,
                                           @RequestParam(required = false) Long endTime,
                                           @RequestParam(required = false) String dateType,
                                           @ModelAttribute("user") User user) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> depts = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            depts.add(String.valueOf(dept.getId()));
        }
        Map<String, Object> map = houseRecordSerivice.getStatisticsByType(startTime, endTime, depts, dateType);
        resultMap.put("map", map);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }
}

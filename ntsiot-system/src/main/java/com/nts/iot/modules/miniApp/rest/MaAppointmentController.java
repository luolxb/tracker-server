package com.nts.iot.modules.miniApp.rest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.modules.miniApp.model.Appointment;
import com.nts.iot.modules.miniApp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约登记
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/29 10:17
 * @Description:
 */
@RestController
@RequestMapping("ma")
public class MaAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

//    @GetMapping("/appointment/list")
//    public ResponseEntity queryAll(String username, Pageable pageable){
//        return new ResponseEntity(appointmentService.queryAll(pageable, username), HttpStatus.OK);
//    }

    /**
     * 新增预约信息
     * @param appointment
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/appointment/add")
    public Map<String, Object> saveAppointment(@RequestBody Appointment appointment){
        Map<String, Object> result = new HashMap<>();
        // 0: 待受理 1: 已受理
        appointment.setStatus("0");
        appointment.setCreateTime(System.currentTimeMillis());
        if (appointmentService.save(appointment)){
            result.put("code", 200);
            result.put("message", "预约成功");
        } else {
            result.put("code", 500);
            result.put("message", "预约失败");
        }
        return result;
    }

    /**
     * 获取最近一次预约信息
     * @param openId
     * @return
     */
    @GetMapping(value = "/appointment/get")
    public Appointment getLatestAppointment(String openId){
        return appointmentService.getLatestAppointment(openId);
    }

    /**
     * 获取预约历史
     * @param openId
     * @return
     */
    @GetMapping(value = "/appointment/getHistory")
    public ResponseEntity getHistory(String openId){
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ma_open_id",openId);
        List<Appointment> appointments = appointmentService.list(queryWrapper);
        return new ResponseEntity(appointments, HttpStatus.OK);
    }
}

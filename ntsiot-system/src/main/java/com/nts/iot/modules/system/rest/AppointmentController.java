package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.miniApp.model.Appointment;
import com.nts.iot.modules.miniApp.model.AppointmentManager;
import com.nts.iot.modules.miniApp.service.AppointmentManagerService;
import com.nts.iot.modules.miniApp.service.AppointmentService;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约登记
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/29 10:17
 * @Description:
 */
@RestController
@RequestMapping("api")
public class AppointmentController extends JwtBaseController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentManagerService appointmentManagerService;

    @Autowired
    private DeptService deptService;

    @Log("预约查询")
    @GetMapping("/appointment/list")
    public ResponseEntity queryAll(String username, String startTime, String endTime, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }
        return new ResponseEntity(appointmentService.queryAll(pageable, username, startTime, endTime, jurisdictions), HttpStatus.OK);
    }

    @Log("查询管理人")
    @GetMapping("/appointmentManager/list")
    public ResponseEntity queryAll(String username,Long jurisdiction, Pageable pageable){
        return new ResponseEntity(appointmentManagerService.queryAll(pageable, username, jurisdiction), HttpStatus.OK);
    }

    @Log("添加管理人")
    @PostMapping("/appointmentManager/add")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity addAppointmentManager(@Validated @RequestBody AppointmentManager appointmentManager){
        appointmentManagerService.add(appointmentManager);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Log("修改管理人信息")
    @PutMapping(value = "/appointmentManager/edit")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity editAppointmentManager(@RequestBody AppointmentManager appointmentManager){
        appointmentManagerService.update(appointmentManager);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除管理人信息")
    @DeleteMapping(value = "/appointmentManager/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity delete(@PathVariable Long id) {
        appointmentManagerService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改受理状态
     * @param id
     * @return
     */
    @Log("更新预约信息")
    @PostMapping("/appointment/edit/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity changeStatus(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        // 已受理
        appointment.setStatus("1");
        appointment.setUpdateTime(System.currentTimeMillis());
        // 更新预约信息
        appointmentService.updateById(appointment);
        return new ResponseEntity(HttpStatus.OK);
    }
}

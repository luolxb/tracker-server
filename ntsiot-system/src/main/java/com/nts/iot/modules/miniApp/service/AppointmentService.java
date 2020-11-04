package com.nts.iot.modules.miniApp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.Appointment;
import com.nts.iot.modules.system.dto.AppointmentStaticDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约登记
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/29 10:19
 * @Description:
 */
public interface AppointmentService extends IService<Appointment> {

    /**
     * 列表查询
     * @param pageable
     * @return
     */
    Map queryAll(Pageable pageable, String username, String startTime, String endTime, List<String> jurisdictions);

    /**
     * 获取最近一次预约信息
     * @param openId
     * @return
     */
    Appointment getLatestAppointment(String openId);


    void exportAppointment(HttpServletResponse res, String path, String startTime, String endTime,String  username,Long jurisdiction);

    List<AppointmentStaticDTO> appointmentStatic(Long startTime, Long endTime, String type, String deptId);

    void exportAppointmentStatics(HttpServletResponse res,String path, Long startTime, Long endTime, String type, String deptId);

    HashMap<String,Long> countStatusByjurisdictions(long jurisdiction);

}

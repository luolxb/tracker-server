package com.nts.iot.modules.miniApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.model.Appointment;
import com.nts.iot.modules.system.dto.AppointmentStaticDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/29 10:22
 * @Description:
 */
@Mapper
@Repository
public interface AppointmentMapper extends BaseMapper<Appointment> {
    /**
     * 获取最近一次预约信息
     *
     * @param openId
     * @return
     */
    Appointment getLatestAppointment(@Param("openId") String openId);

    Page<Appointment> selectByPage(Page page, @Param("username") String username, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("jurisdictions") List<String> jurisdictions);

    /**
     * 按照天统计
     *
     * @param times
     * @param startTime
     * @param endTime
     * @param jurisdictions
     * @return
     */
    List<AppointmentStaticDTO> appointmentStaticByDay(@Param("times") int times,
                                                      @Param("startTime") Long startTime,
                                                      @Param("endTime") Long endTime,
                                                      @Param("jurisdictions") List<Long> jurisdictions);

    /**
     * 按照月统计
     *
     * @param times
     * @param startTime
     * @param endTime
     * @param jurisdictions
     * @return
     */
    List<AppointmentStaticDTO> appointmentStaticByMonth(@Param("times") int times,
                                                        @Param("startTime") Long startTime,
                                                        @Param("endTime") Long endTime,
                                                        @Param("jurisdictions") List<Long> jurisdictions);

    /**
     * 根据辖区，统计目前为止受理和未受理人数
     * @param jurisdictions
     * @return
     */
    HashMap<String,Long> countStatusByjurisdictions(@Param("jurisdictions") List<Long> jurisdictions);
}

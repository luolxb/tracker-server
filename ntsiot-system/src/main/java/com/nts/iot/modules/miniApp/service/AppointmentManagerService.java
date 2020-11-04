package com.nts.iot.modules.miniApp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.AppointmentManager;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: hamsun
 * @Date: 2019/5/29 10:19
 * @Description:
 */
public interface AppointmentManagerService extends IService<AppointmentManager> {
    /**
     * 列表查询
     * @param pageable
     * @return
     */
    Map queryAll(Pageable pageable, String username, Long jurisdiction);

    List<AppointmentManager> queryByPeriod(Integer period);

    void add(AppointmentManager appointmentManager);

    void update(AppointmentManager appointmentManager);

    void delete(Long id);
}

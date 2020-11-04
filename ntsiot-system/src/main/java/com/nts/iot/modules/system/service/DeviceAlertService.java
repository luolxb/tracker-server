package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.system.model.DeviceAlert;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceAlertProportionVo;
import com.nts.iot.modules.system.model.vo.DeviceAlertStatisticsVo;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.nts.iot.modules.system.service
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-08 13:14
 **/
public interface DeviceAlertService extends IService<DeviceAlert> {

    DeviceAlertProportionVo proportion(User user);

    List<Map<String, Object>> monitor(User user) throws ParseException;

    void create(List<CollectMessage> collectMessages, User user);

    Page<DeviceAlert> queryPage(Pageable pageable,
                                Long userId,
                                String deviceNo,
                                String startDate,
                                String endDate,
                                Integer status);

    void updateDeviceAlert(List<DeviceAlert> deviceAlertList, User user);

    List<DeviceAlertStatisticsVo> statistics(Long userId, String deviceNo, String startDate,
                                             String endDate);

    Page<DeviceAlert> queryPageDetail(Pageable pageable, Long userId, String deviceNo, String startDate, String endDate);
}

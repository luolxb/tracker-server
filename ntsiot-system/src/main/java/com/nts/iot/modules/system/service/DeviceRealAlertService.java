package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.system.model.DeviceRealAlert;
import com.nts.iot.modules.system.model.vo.DeviceRealAlertVo;
import org.springframework.data.domain.Pageable;

/**
 * @PackageName: com.nts.iot.modules.system.service
 * @program: ntsiot
 * @author: ruosen
 * @create: 2020-06-22 18:34
 **/
public interface DeviceRealAlertService extends IService<DeviceRealAlert> {
    Page<DeviceRealAlertVo> proportionList(Long userId, String alertType, Pageable pageable);

    void delDeviceRealAlert(CollectMessage message);

    void addDeviceRealAlert(CollectMessage message);
}

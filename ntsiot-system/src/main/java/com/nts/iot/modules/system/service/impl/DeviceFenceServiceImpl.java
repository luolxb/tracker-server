package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.DeviceFenceMapper;
import com.nts.iot.modules.system.model.DeviceFence;
import com.nts.iot.modules.system.service.DeviceFenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceFenceServiceImpl extends ServiceImpl<DeviceFenceMapper, DeviceFence> implements DeviceFenceService {
}

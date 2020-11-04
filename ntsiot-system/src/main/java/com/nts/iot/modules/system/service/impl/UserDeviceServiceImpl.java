package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.UserDeviceMapper;
import com.nts.iot.modules.system.model.UserDevice;
import com.nts.iot.modules.system.service.UserDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @PackageName: com.nts.iot.modules.system.service.impl
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:20
 **/
@Service
@Slf4j
public class UserDeviceServiceImpl extends ServiceImpl<UserDeviceMapper, UserDevice> implements UserDeviceService {
}

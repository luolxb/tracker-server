package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.CustomerService;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.CustomerServiceRq;
import com.nts.iot.modules.system.model.vo.CustomerServiceVo;

public interface CustomerServiceService extends IService<CustomerService> {

    void insertOrUpdate(CustomerServiceRq customerServiceRq,User user);

    CustomerServiceVo detail(Long userId);
}

package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.CustomerServiceMapper;
import com.nts.iot.modules.system.model.CustomerService;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.CustomerServiceRq;
import com.nts.iot.modules.system.model.vo.CustomerServiceVo;
import com.nts.iot.modules.system.service.CustomerServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class CustomerServiceServiceImpl extends ServiceImpl<CustomerServiceMapper, CustomerService> implements CustomerServiceService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdate(CustomerServiceRq customerServiceRq, User user) {
        CustomerService customerService = new CustomerService();
        BeanUtils.copyProperties(customerServiceRq, customerService);
        // 如果ID是null就是新增，防止id为0
        if (customerServiceRq.getId() == null || customerServiceRq.getId() == 0) {
            customerService.setCreateTime(new Date());
            customerService.setCreateBy(user.getUsername());
            int insert = this.baseMapper.insert(customerService);
            if (insert <= 0) {
                throw new BadRequestException("操作失败,operation failed");
            }
        } else {
            // 如果ID不是是null就是，就是修改
            customerService.setUpdateBy(user.getUsername());
            customerService.setUpdateTime(new Date());
            int update = this.baseMapper.updateById(customerService);
            if (update <= 0) {
                throw new BadRequestException("操作失败,operation failed");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerServiceVo detail(Long userId) {
        // 根据用户id获取客服信息
        CustomerService customerService = this.getOne(new QueryWrapper<CustomerService>().eq("user_id", userId));
        // 如果是null 返回空对象
        if (customerService == null) {
            return null;
        }
        CustomerServiceVo customerServiceVo = new CustomerServiceVo();
        BeanUtils.copyProperties(customerService,customerServiceVo);
        return customerServiceVo;
    }
}

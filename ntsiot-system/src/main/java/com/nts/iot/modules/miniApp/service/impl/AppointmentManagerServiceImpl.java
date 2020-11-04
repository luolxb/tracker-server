package com.nts.iot.modules.miniApp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dao.AppointmentManagerMapper;
import com.nts.iot.modules.miniApp.model.AppointmentManager;
import com.nts.iot.modules.miniApp.service.AppointmentManagerService;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppointmentManagerServiceImpl extends ServiceImpl<AppointmentManagerMapper, AppointmentManager> implements AppointmentManagerService{
    @Autowired
    DeptService deptService;

    @Override
    public Map queryAll(Pageable pageable, String username,Long jurisdiction) {
        Page<AppointmentManager> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<AppointmentManager> pageResult = baseMapper.selectByPage(page, username, jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public List<AppointmentManager> queryByPeriod(Integer period) {
        return baseMapper.selectByPeriod(period);
    }

    @Override
    public void add(AppointmentManager appointmentManager) {
        long createTime = System.currentTimeMillis();
        baseMapper.add(appointmentManager.getUsername(),appointmentManager.getPhone(),appointmentManager.getJurisdiction(),
                appointmentManager.getSwitchFlag(),appointmentManager.getPeriod(),createTime);
    }

    @Override
    public void update(AppointmentManager appointmentManager) {
        long updateTime = System.currentTimeMillis();
        baseMapper.update(appointmentManager.getId(),appointmentManager.getUsername(),appointmentManager.getPhone(),appointmentManager.getJurisdiction(),
                appointmentManager.getSwitchFlag(),appointmentManager.getPeriod(),updateTime);
    }

    @Override
    public void delete(Long id) {
        baseMapper.delete(id);
    }

}

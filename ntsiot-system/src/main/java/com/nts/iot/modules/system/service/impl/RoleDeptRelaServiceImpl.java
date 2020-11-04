package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.RoleDeptRelaMapper;
import com.nts.iot.modules.system.model.RoleDeptRelation;
import com.nts.iot.modules.system.service.RoleDeptRelaService;
import org.springframework.stereotype.Service;

@Service
public class RoleDeptRelaServiceImpl extends ServiceImpl<RoleDeptRelaMapper, RoleDeptRelation> implements RoleDeptRelaService {

    @Override
    public Integer findByDept(long deptId) {
        QueryWrapper<RoleDeptRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", deptId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer findByRole(long roleId) {
        QueryWrapper<RoleDeptRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return baseMapper.selectCount(queryWrapper);
    }
}

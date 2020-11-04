package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.RoleMenuRelationMapper;
import com.nts.iot.modules.system.model.RoleMenuRelation;
import com.nts.iot.modules.system.service.RoleMenuRelaService;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuRelaServiceImpl extends ServiceImpl<RoleMenuRelationMapper, RoleMenuRelation> implements RoleMenuRelaService {

    @Override
    public Integer findByRole(long roleId) {
        QueryWrapper<RoleMenuRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return baseMapper.selectCount(queryWrapper);
    }
}

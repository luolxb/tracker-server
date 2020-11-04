package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.RolePermiRelaMapper;
import com.nts.iot.modules.system.model.RolePermiRelation;
import com.nts.iot.modules.system.service.RolePermiRelaService;
import org.springframework.stereotype.Service;

@Service
public class RolePermiRelaServiceImpl extends ServiceImpl<RolePermiRelaMapper, RolePermiRelation> implements RolePermiRelaService {


    @Override
    public Integer findByRole(long roleId) {
        QueryWrapper<RolePermiRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return baseMapper.selectCount(queryWrapper);
    }
}

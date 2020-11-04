package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.UserRoleRelaMapper;
import com.nts.iot.modules.system.model.UserRoleRelation;
import com.nts.iot.modules.system.service.UserRoleRelaService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class UserRoleRelaServiceImpl extends ServiceImpl<UserRoleRelaMapper, UserRoleRelation> implements UserRoleRelaService {

    @Override
    public Integer deleteByUser(Long userId) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", userId);
        return baseMapper.deleteByMap(columnMap);
    }

    @Override
    public List<UserRoleRelation> findByRoleId(Long roleId) {
        return baseMapper.findByRoleId(roleId);
    }

}

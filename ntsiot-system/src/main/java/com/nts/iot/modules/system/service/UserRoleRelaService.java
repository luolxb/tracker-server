package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.UserRoleRelation;

import java.util.List;

public interface UserRoleRelaService extends IService<UserRoleRelation> {
    Integer deleteByUser(Long userId);

    List<UserRoleRelation> findByRoleId(Long roleId);

}

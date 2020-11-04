package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.RoleMenuRelation;

public interface RoleMenuRelaService extends IService<RoleMenuRelation> {

    Integer findByRole(long roleId);
}

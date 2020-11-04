package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.RolePermiRelation;

public interface RolePermiRelaService extends IService<RolePermiRelation> {

    Integer findByRole(long roleId);
}

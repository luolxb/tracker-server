package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.RoleDeptRelation;

public interface RoleDeptRelaService extends IService<RoleDeptRelation> {
    Integer findByDept(long deptId);

    Integer findByRole(long roleId);
}

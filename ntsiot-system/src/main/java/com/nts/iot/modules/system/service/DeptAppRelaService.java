package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.DeptAppRela;

/**
 * @author jie
 * @date 2019-03-25
 */
public interface DeptAppRelaService extends IService<DeptAppRela> {

    /**
     * 更新辖区、小程序模块关系表
     * @param dept
     */
    void updateDeptAppRela(Dept dept);

}
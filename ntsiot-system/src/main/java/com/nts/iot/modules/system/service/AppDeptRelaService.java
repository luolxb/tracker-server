package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.AppDeptRela;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jie
 * @date 2019-03-25
 */
public interface AppDeptRelaService extends IService<AppDeptRela> {

    /**
     * 删除关系数据
     * @param deptId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByDeptId(Long deptId);

    /**
     * 根据辖区查找是否存在
     * @param deptId
     * @return
     */
    Integer getByDeptId(Long deptId);
}
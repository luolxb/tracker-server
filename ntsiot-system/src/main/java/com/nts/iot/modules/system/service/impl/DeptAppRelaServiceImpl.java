package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.DeptAppRelaMapper;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.DeptAppRela;
import com.nts.iot.modules.system.service.DeptAppRelaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2019-03-25
 */
@Service
public class DeptAppRelaServiceImpl extends ServiceImpl<DeptAppRelaMapper, DeptAppRela> implements DeptAppRelaService {

    @Override
    public void updateDeptAppRela(Dept dept) {
        // 根据辖区清除辖区、小程序关联数据
        this.deleteByDeptId(dept.getId());
        // 再保存关系数据
        List<DeptAppRela> list = new ArrayList<>();
        dept.getAppModules().forEach(it -> {
            DeptAppRela deptAppRela = new DeptAppRela();
            deptAppRela.setDeptId(dept.getId());
            deptAppRela.setAppModuleId(it.getId());
            list.add(deptAppRela);
        });
        this.saveBatch(list);
    }

    /**
     * 根据辖区删除关系数据
     * @param deptId
     * @return
     */
    private Integer deleteByDeptId(Long deptId) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("dept_id", deptId);
        return baseMapper.deleteByMap(columnMap);
    }
}
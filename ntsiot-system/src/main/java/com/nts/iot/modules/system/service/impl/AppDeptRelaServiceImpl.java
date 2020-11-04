package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.AppDeptRelaMapper;
import com.nts.iot.modules.system.model.AppDeptRela;
import com.nts.iot.modules.system.service.AppDeptRelaService;
import com.nts.iot.utils.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author jie
 * @date 2019-03-25
 */
@Service
public class AppDeptRelaServiceImpl extends ServiceImpl<AppDeptRelaMapper, AppDeptRela> implements AppDeptRelaService {

    @Override
    public Integer deleteByDeptId(Long deptId) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("dept_id", deptId);
        return baseMapper.deleteByMap(columnMap);
    }

    @Override
    public Integer getByDeptId(Long deptId) {
        QueryWrapper<AppDeptRela> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", deptId);
        return baseMapper.selectCount(queryWrapper);
    }
}
package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.TaskPatrolmanMapper;
import com.nts.iot.modules.system.model.TaskPatrolman;
import com.nts.iot.modules.system.service.TaskPatrolmanService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
@Service
public class TaskPatrolmanServiceImpl extends ServiceImpl<TaskPatrolmanMapper, TaskPatrolman> implements TaskPatrolmanService {


    @Override
    public List<TaskPatrolman> getByTaskId(Long taskId) {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("task_id", taskId);
        return baseMapper.selectByMap(columnMap);
    }

    @Override
    public Integer deleteByTaskId(Long taskId) {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("task_id", taskId);
        return baseMapper.deleteByMap(columnMap);
    }
}

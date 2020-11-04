package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.TaskPointMapper;
import com.nts.iot.modules.system.model.TaskPoint;
import com.nts.iot.modules.system.service.TaskPointService;
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
public class TaskPointServiceImpl extends ServiceImpl<TaskPointMapper, TaskPoint> implements TaskPointService {

    @Override
    public List<TaskPoint> getPointsByTaskId(Long taskId) {
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

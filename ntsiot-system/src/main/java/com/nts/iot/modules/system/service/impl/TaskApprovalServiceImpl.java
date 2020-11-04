package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.TaskApprovalMapper;
import com.nts.iot.modules.system.model.TaskApproval;
import com.nts.iot.modules.system.service.RealTaskService;
import com.nts.iot.modules.system.service.TaskApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TaskApprovalServiceImpl extends ServiceImpl<TaskApprovalMapper, TaskApproval> implements TaskApprovalService {

    @Autowired
    private RealTaskService realTaskService;

    @Override
    public Integer saveApproval(TaskApproval taskApproval) {
        // 更改审核状态
        realTaskService.changeApprovalStatus(taskApproval.getTaskId());
        return baseMapper.insert(taskApproval);
    }

    @Override
    public TaskApproval getApprovalByTaskId(Long taskId) {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("task_id", taskId);
        List<TaskApproval> list = baseMapper.selectByMap(columnMap);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}

package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.CheckPointTaskMapper;
import com.nts.iot.modules.system.model.CheckPointTask;
import com.nts.iot.modules.system.service.CheckPointTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckPointTaskServiceImpl extends ServiceImpl<CheckPointTaskMapper, CheckPointTask> implements CheckPointTaskService {

    /**
     * 删除任务
     *
     * @param id 必到点id
     */
    @Override
    public void deleteTaskById(Long id) {
        baseMapper.deleteTaskById(id);
    }

    /**
     * 必到点id
     *
     * @param id 必到点id
     * @return
     */
    @Override
    public List<CheckPointTask> selectTask(Long id) {
        return baseMapper.queryAllById(id);
    }
}

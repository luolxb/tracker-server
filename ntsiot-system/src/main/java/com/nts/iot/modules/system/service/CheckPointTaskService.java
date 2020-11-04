package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.CheckPointTask;

import java.util.List;

public interface CheckPointTaskService extends IService<CheckPointTask> {

    /**
     * 删除任务
     *
     * @param id 必到点id
     */
    void deleteTaskById(Long id);

    /**
     * 必到点id
     *
     * @param id 必到点id
     * @return
     */
    List<CheckPointTask> selectTask(Long id);
}

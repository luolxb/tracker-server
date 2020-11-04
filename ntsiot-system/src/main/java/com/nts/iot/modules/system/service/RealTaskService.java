package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.MissionDto;
import com.nts.iot.modules.system.model.RealTask;
import com.nts.iot.modules.system.model.TaskInstance;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RealTaskService extends IService<RealTask> {

    /**
     * 根据任务实例创建任务
     * @param taskInstance
     * @param toString
     */
    void create(TaskInstance taskInstance, String toString);

    /**
     * 获取任务信息
     * @param taskId
     * @return
     */
    RealTask getTaskById(Long taskId);

    List<MissionDto> getTaskDtoByUserId(String userId);


    /**
     * 更新审核状态
     * @param taskId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    void changeApprovalStatus(Long taskId);


    /**
     * 获取未完成、进行中的任务
     * @return
     */
    List<RealTask> getTask();

    /**
     * 更新任务状态
     */
    void changeStatus(RealTask task);

}

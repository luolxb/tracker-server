package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.TaskPatrolman;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface TaskPatrolmanService extends IService<TaskPatrolman> {

    /**
     * 根据任务id查找巡更人
     * @param taskId
     * @return
     */
    List<TaskPatrolman> getByTaskId(Long taskId);

    /**
     * 删除巡更人
     * @param taskId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByTaskId(Long taskId);

}

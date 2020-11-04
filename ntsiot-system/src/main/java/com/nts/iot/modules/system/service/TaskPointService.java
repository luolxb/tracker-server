package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.TaskPoint;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface TaskPointService extends IService<TaskPoint> {

    /**
     * 根据任务id查找必到点
     * @param taskId
     * @return
     */
    List<TaskPoint> getPointsByTaskId(Long taskId);


    /**
     * 删除必到点
     * @param taskId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByTaskId(Long taskId);
}

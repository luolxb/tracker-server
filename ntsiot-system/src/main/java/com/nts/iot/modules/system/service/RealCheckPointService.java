package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.TaskDetailDto;
import com.nts.iot.modules.system.model.RealCheckPoint;

import java.util.List;

public interface RealCheckPointService extends IService<RealCheckPoint> {

    /**
     * 根据任务id查找必到点
     * @param taskId
     * @return
     */
    List<RealCheckPoint> getPointsByTaskId(long taskId);

    /**
     * 查询任务详细
     * @param id
     * @return
     */
    List<TaskDetailDto> selectTaskDetail(Long id);

}

package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import com.nts.iot.modules.system.model.RealCheckPointTask;

import java.util.List;
import java.util.Map;

public interface RealCheckPointTaskService extends IService<RealCheckPointTask> {
    Map<String, Object> saveSubTask(MaRequestBody requestBody);

    /**
     * 获得任务停留时间 （毫秒）
     *
     * @param pointId   必到点id
     * @param startTime 开始时间戳
     * @param endTime   结束时间戳
     * @param lockNo    锁编号
     * @return
     */
    Long getTaskTime(Long pointId, Long startTime, Long endTime, String lockNo);

    Map<String, Object> toClockOn(MaRequestBody requestBody);

    List<RealCheckPointTask> queryAll();
}

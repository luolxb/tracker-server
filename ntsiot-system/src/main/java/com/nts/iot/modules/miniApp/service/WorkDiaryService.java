package com.nts.iot.modules.miniApp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.WorkDiary;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface WorkDiaryService extends IService<WorkDiary> {

    /**
     * 添加工作日志上报
     *
     * @param workDiary
     */
    void saveWorkDiary(WorkDiary workDiary);

    /**
     * 列表查询
     *
     * @param pageable
     * @return
     */
    Map queryAll(Pageable pageable, String startTime, String endTime, List<Long> jurisdictions);
}

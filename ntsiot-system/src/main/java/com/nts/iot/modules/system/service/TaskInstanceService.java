package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.TaskInstance;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface TaskInstanceService extends IService<TaskInstance> {

    /**
     * 列表查询
     * @param title
     * @param jurisdictions
     * @param pageable
     * @return
     */
    Map queryAll(String title, List<String> jurisdictions, Pageable pageable);

    /**
     * 新增
     * @param taskInstance
     */
    @Transactional(rollbackFor = Exception.class)
    void saveTaskInstance(TaskInstance taskInstance);

    TaskInstance getTaskInstanceById(Long id);

    /**
     * 新增
     * @param taskInstance
     */
    @Transactional(rollbackFor = Exception.class)
    void updateTaskInstance(TaskInstance taskInstance);

    /**
     * 删除
     * @param taskId
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteById(Long taskId);

    /**
     * 更改任务状态
     * @param taskId
     * @param status
     */
    @Transactional(rollbackFor = Exception.class)
    void updateTaskStatus(Long taskId, String status);

    /***
     * 获取非单次 任务实例
     * @return
     */
    List<TaskInstance> getTimeInList();

}

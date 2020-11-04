package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.TaskTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface TaskTemplateService extends IService<TaskTemplate> {

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
     * @param taskTemplate
     */
    @Transactional(rollbackFor = Exception.class)
    void saveTaskTemplate(TaskTemplate taskTemplate);

    TaskTemplate getTaskTemplateById(Long id);

    /**
     * 新增
     * @param taskTemplate
     */
    @Transactional(rollbackFor = Exception.class)
    void updateTaskTemplate(TaskTemplate taskTemplate);

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

    /**
     * 列表查询
     * @param title
     * @param jurisdictions
     * @param pageable
     * @return
     */
    Map queryAll(String title, String patrolman, String startTime, String endTime, List<String> jurisdictions, Pageable pageable);

    IPage<TaskTemplate> selectAllByDept(Pageable pageable,Long deptId);
}

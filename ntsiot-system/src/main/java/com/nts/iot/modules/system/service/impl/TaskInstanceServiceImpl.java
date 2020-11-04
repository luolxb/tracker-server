package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.TaskInstanceMapper;
import com.nts.iot.modules.system.model.TaskInstance;
import com.nts.iot.modules.system.model.TaskPoint;
import com.nts.iot.modules.system.service.TaskInstanceService;
import com.nts.iot.modules.system.service.TaskPatrolmanService;
import com.nts.iot.modules.system.service.TaskPointService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.nts.iot.constant.ConstantClass.TASK_REPEAT_SINGLE;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
@Service
public class TaskInstanceServiceImpl extends ServiceImpl<TaskInstanceMapper, TaskInstance> implements TaskInstanceService {

    @Autowired
    private TaskPatrolmanService taskPatrolmanService;

    @Autowired
    private TaskPointService taskPointService;


    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<TaskInstance> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<TaskInstance> pageResult = baseMapper.selectByPage(page, title, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTaskInstance(TaskInstance taskInstance) {
        // 保存任务实例信息
        if (this.save(taskInstance)) {
            //  保存巡更人信息
            taskInstance.getTaskPatrolmen().forEach(it -> {
                it.setTaskId(taskInstance.getId());
            });
            taskPatrolmanService.saveBatch(taskInstance.getTaskPatrolmen());
        }
        /* 判断如果任务实例的类型为立刻执行，则直接创建真实的下发任务 */
//        if (TASK_REPEAT_SINGLE.equals(taskInstance.getRepeatTime())){
//            // 当前日期
//            LocalDate localDate = LocalDate.now();
//            realTaskService.create(taskInstance, localDate.toString());
//        }
    }

    @Override
    public TaskInstance getTaskInstanceById(Long id) {
        TaskInstance taskInstance = baseMapper.selectById(id);
        if (taskInstance != null) {
            // 巡更人
            taskInstance.setTaskPatrolmen(taskPatrolmanService.getByTaskId(id));
            /* 查询模板关联 必到点的相关信息 */
            QueryWrapper<TaskPoint> query1 = new QueryWrapper<>();
            query1.eq("task_id", taskInstance.getTemplateId());
            List<TaskPoint> points = taskPointService.list(query1);
            taskInstance.setTaskPoints(points);
        }
        return taskInstance;
    }

    @Override
    public void updateTaskInstance(TaskInstance taskInstance) {
        int result = baseMapper.updateById(taskInstance);
        if (result > 0) {
            // 更新巡更人信息
            taskPatrolmanService.deleteByTaskId(taskInstance.getId());
            taskInstance.getTaskPatrolmen().forEach(it -> {
                it.setTaskId(taskInstance.getId());
            });
            taskPatrolmanService.saveBatch(taskInstance.getTaskPatrolmen());
        }
    }

    @Override
    public void deleteById(Long taskId) {
        // 删除巡更人信息
        taskPatrolmanService.deleteByTaskId(taskId);
        // 删除必到点
        taskPointService.deleteByTaskId(taskId);
        // 删除任务配置
        baseMapper.deleteById(taskId);
    }

    @Override
    public void updateTaskStatus(Long taskId, String status) {
        baseMapper.updateTaskStatus(taskId, status);
    }

    /***
     * 获取非单次 任务实例
     * @return
     */
    @Override
    public List<TaskInstance> getTimeInList() {
        return baseMapper.getTimeInList();
    }

}

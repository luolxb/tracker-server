package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.TaskTemplateMapper;
import com.nts.iot.modules.system.dto.PatrolTaskDTO;
import com.nts.iot.modules.system.model.TaskPoint;
import com.nts.iot.modules.system.model.TaskTemplate;
import com.nts.iot.modules.system.service.TaskPatrolmanService;
import com.nts.iot.modules.system.service.TaskPointService;
import com.nts.iot.modules.system.service.TaskTemplateService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
@Service
public class TaskTemplateServiceImpl extends ServiceImpl<TaskTemplateMapper, TaskTemplate> implements TaskTemplateService {

    @Autowired
    private TaskPatrolmanService taskPatrolmanService;

    @Autowired
    private TaskPointService taskPointService;

    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<TaskTemplate> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<TaskTemplate> pageResult = baseMapper.selectByPage(page, title, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public void saveTaskTemplate(TaskTemplate taskTemplate) {
        // 保存任务配置信息
        int result = baseMapper.insert(taskTemplate);
        if (result > 0) {
            // TODO ※ 保存巡更人信息
//            taskTemplate.getTaskPatrolmen().forEach(it -> {
//                it.setTaskId(taskTemplate.getId());
//            });
//            taskPatrolmanService.saveBatch(taskTemplate.getTaskPatrolmen());
            // 保存必到点信息
            taskTemplate.getTaskPoints().forEach(it -> {
                it.setTaskId(taskTemplate.getId());
            });
            taskPointService.saveBatch(taskTemplate.getTaskPoints());
        }
    }

    @Override
    public TaskTemplate getTaskTemplateById(Long id) {
        TaskTemplate taskTemplate = baseMapper.selectById(id);
        if (taskTemplate != null) {
            // TODO ※ 巡更人
            // taskTemplate.setTaskPatrolmen(taskPatrolmanService.getByTaskId(id));
            // 必到点
            taskTemplate.setTaskPoints(taskPointService.getPointsByTaskId(id));
        }
        return taskTemplate;
    }

    @Override
    public void updateTaskTemplate(TaskTemplate taskTemplate) {
        int result = baseMapper.updateById(taskTemplate);
        if (result > 0) {
            // TODO ※ 更新巡更人信息
//            taskPatrolmanService.deleteByTaskId(taskTemplate.getId());
//            taskTemplate.getTaskPatrolmen().forEach(it -> {
//                it.setTaskId(taskTemplate.getId());
//            });
//            taskPatrolmanService.saveBatch(taskTemplate.getTaskPatrolmen());
            // 更新必到点信息
            taskPointService.deleteByTaskId(taskTemplate.getId());
            taskTemplate.getTaskPoints().forEach(it -> {
                it.setTaskId(taskTemplate.getId());
            });
            taskPointService.saveBatch(taskTemplate.getTaskPoints());
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

    @Override
    public Map queryAll(String title, String patrolman, String startTime, String endTime, List<String> jurisdictions, Pageable pageable) {
        Page<PatrolTaskDTO> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<PatrolTaskDTO> pageResult = baseMapper.selectTaskApprovalsByPage(page, title, patrolman, startTime, endTime, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public IPage<TaskTemplate> selectAllByDept(Pageable pageable,Long deptId) {
        Page<TaskTemplate> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        QueryWrapper<TaskTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id",deptId);
        IPage<TaskTemplate> pageResult = baseMapper.selectPage(page, wrapper);
        List<TaskTemplate> taskTemplateList = new ArrayList<>();
        /* 查询模板关联 必到点的相关信息 */
        for (TaskTemplate template : pageResult.getRecords()) {
            QueryWrapper<TaskPoint> query1 = new QueryWrapper<>();
            query1.eq("task_id",template.getId());
            List<TaskPoint> points = taskPointService.list(query1);
            template.setTaskPoints(points);
            StringBuilder pointsName = new StringBuilder();
            for (TaskPoint p : points) {
                if (p.getPointName() != null) {
                    pointsName.append(p.getPointName()).append("→");
                }
            }
            if (pointsName.length()>1){
                pointsName.deleteCharAt(pointsName.length()-1);
            }
            template.setTaskPointName(pointsName.toString());
            taskTemplateList.add(template);
        }
        pageResult.setRecords(taskTemplateList);
        return pageResult;
    }
}

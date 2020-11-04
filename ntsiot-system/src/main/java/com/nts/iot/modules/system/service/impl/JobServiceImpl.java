package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.JobMapper;
import com.nts.iot.modules.system.dto.JobDTO;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Job;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.JobService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author jie
 * @date 2019-03-29
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Autowired
    DeptService deptService;

    @Override
    public Object queryAll(String name, Boolean enabled, Set<Long> deptIds, Long deptId, Pageable pageable) {
        Page<Job> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(name)) {
            wrapper.like("name", name);
        }
        if (deptId != null) {
            wrapper.eq("dept_id", deptId);
        }
        if (enabled != null) {
            wrapper.eq("enabled", enabled);
        }
//        else {
//            // 默认查询状态为正常的
//            wrapper.eq("enabled", true);
//        }
        if (deptIds != null && deptIds.size() > 0) {
            wrapper.in("dept_id", deptIds);
        }
        IPage<Job> pageResult = baseMapper.selectPage(page, wrapper);
        pageResult.getRecords().forEach(job ->{
            Dept dept = deptService.getById(job.getDeptId());
            job.setDept(dept);
        });
        return PageUtil.toPage(pageResult);
    }


    @Override
    public JobDTO findById(Long id) {
        JobDTO jobDTO = new JobDTO();
        Job job = baseMapper.selectById(id);
        BeanUtil.copyProperties(job, jobDTO);
        return jobDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer create(Job resources) {
        resources.setCreateTime(new Timestamp(System.currentTimeMillis()));
        resources.setDeptId(resources.getDept().getId());
        return baseMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Job resources) {
        resources.setDeptId(resources.getDept().getId());
        baseMapper.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);

    }
}
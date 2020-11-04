package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.CommonProblemMapper;
import com.nts.iot.modules.system.model.CommonProblem;
import com.nts.iot.modules.system.service.CommonProblemService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/6 11:21
 * @Description:
 */
@Service
public class CommonProblemServiceImpl extends ServiceImpl<CommonProblemMapper, CommonProblem> implements CommonProblemService {

    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<CommonProblem> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<CommonProblem> pageResult = baseMapper.selectByPage(page, title, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public void saveProblem(CommonProblem commonProblem) {
        baseMapper.insert(commonProblem);
    }

    /**
     * 根据辖区查询列表
     * @param deptId
     * @return
     */
    @Override
    public List<CommonProblem> getProblemsByDeptId(Long deptId) {
        QueryWrapper<CommonProblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jurisdiction", deptId);
        queryWrapper.eq("is_visible", 0);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void updateProblemById(CommonProblem commonProblem) {
        baseMapper.updateById(commonProblem);
    }
}

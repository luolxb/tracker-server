package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.BikeIconMapper;
import com.nts.iot.modules.system.model.BikeIcon;
import com.nts.iot.modules.system.service.BikeIconService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2019-03-25
 */
@Service
public class BikeIconServiceImpl extends ServiceImpl<BikeIconMapper, BikeIcon> implements BikeIconService {

    @Override
    public Map queryAll(String name, List<String> depts, Pageable pageable) {
        Page<BikeIcon> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        QueryWrapper<BikeIcon> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(name)) {
            queryWrapper.like("bike_type", name);
        }
        if (depts != null && depts.size() >0) {
            queryWrapper.in("dept", depts);
        }
        IPage<BikeIcon> pageResult = baseMapper.selectPage(page, queryWrapper);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public BikeIcon findById(Long id) {
        return baseMapper.selectById(id);
    }
}
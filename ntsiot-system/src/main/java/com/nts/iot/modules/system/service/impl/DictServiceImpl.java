package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.DictMapper;
import com.nts.iot.modules.system.dto.DictDTO;
import com.nts.iot.modules.system.model.Dict;
import com.nts.iot.modules.system.service.DictService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author jie
* @date 2019-04-10
*/
@Service
public class DictServiceImpl   extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dict create(Dict resources) {
        baseMapper.insert(resources);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
        baseMapper.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public Object queryAll(DictDTO dict, Pageable pageable) {
        Page<Dict> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(dict.getName())) {
            wrapper.like("name", dict.getName());
        }
        if (StrUtil.isNotEmpty(dict.getRemark())) {
            wrapper.like("remark", dict.getRemark());
        }
        IPage<Dict> pageResult = baseMapper.selectPage(page, wrapper);
        return PageUtil.toPage(pageResult);
    }
}
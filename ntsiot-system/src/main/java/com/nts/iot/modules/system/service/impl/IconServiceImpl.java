package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.IconMapper;
import com.nts.iot.modules.system.model.Icon;
import com.nts.iot.modules.system.service.IconService;
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
public class IconServiceImpl extends ServiceImpl<IconMapper, Icon> implements IconService {

    @Override
    public Map queryAll(String name, List<String> jurisdictions, Pageable pageable) {
        Page<Icon> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Icon> pageResult = baseMapper.selectByPage(page, name, jurisdictions);
        return PageUtil.toPage(pageResult);
    }
}
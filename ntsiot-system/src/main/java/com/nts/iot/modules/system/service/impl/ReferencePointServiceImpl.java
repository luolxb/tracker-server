package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.ReferencePointMapper;
import com.nts.iot.modules.system.model.ReferencePoint;
import com.nts.iot.modules.system.service.ReferencePointService;
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
public class ReferencePointServiceImpl extends ServiceImpl<ReferencePointMapper, ReferencePoint> implements ReferencePointService {

    @Override
    public Map queryAll(String name, Long iconType, List<String> jurisdictions, Pageable pageable) {
        Page<ReferencePoint> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<ReferencePoint> pageResult = baseMapper.selectByPage(page, name, iconType, jurisdictions);
        return PageUtil.toPage(pageResult);
    }
}
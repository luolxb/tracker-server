package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.InformConfigMapper;
import com.nts.iot.modules.system.model.InformConfig;
import com.nts.iot.modules.system.service.InformConfigService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
@Service
public class InformConfigServiceImpl extends ServiceImpl<InformConfigMapper, InformConfig> implements InformConfigService {


    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<InformConfig> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<InformConfig> pageResult = baseMapper.selectByPage(page, title, jurisdictions);
        return PageUtil.toPage(pageResult);
    }
}

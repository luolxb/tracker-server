package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.BikeLicenseMapper;
import com.nts.iot.modules.system.model.BikeLicense;
import com.nts.iot.modules.system.service.BikeLicenseService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhc@rnstec.com
 * @Description
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Service
public class BikeLicenseServiceImpl extends ServiceImpl<BikeLicenseMapper, BikeLicense> implements BikeLicenseService {

    @Override
    public Map queryAll(String name, String telephone, List<String> jurisdictions, Pageable pageable) {
        Page<BikeLicense> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<BikeLicense> pageResult = baseMapper.selectByPage(page, name, telephone, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public List<BikeLicense> findAll(String telephone, String jurisdiction) {
        return baseMapper.selectAll(telephone, jurisdiction);
    }
}

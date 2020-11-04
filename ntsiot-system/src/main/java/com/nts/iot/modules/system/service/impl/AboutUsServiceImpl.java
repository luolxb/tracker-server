package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.AboutUsMapper;
import com.nts.iot.modules.system.model.AboutUs;
import com.nts.iot.modules.system.service.AboutUsService;
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
public class AboutUsServiceImpl extends ServiceImpl<AboutUsMapper, AboutUs> implements AboutUsService {

    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<AboutUs> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<AboutUs> pageResult = baseMapper.selectByPage(page, title, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public Integer saveAboutUs(AboutUs aboutUs) {
        return baseMapper.insert(aboutUs);
    }

    @Override
    public Integer deleteById(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Integer updateAboutUs(AboutUs aboutUs) {
        return baseMapper.updateById(aboutUs);
    }

    @Override
    public AboutUs getAboutUsById(Long id) {
        return baseMapper.selectById(id);
    }
}

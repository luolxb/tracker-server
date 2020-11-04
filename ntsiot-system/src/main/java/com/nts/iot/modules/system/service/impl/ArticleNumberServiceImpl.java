package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.ArticleNumberMapper;
import com.nts.iot.modules.system.model.ArticleNumber;
import com.nts.iot.modules.system.service.ArticleNumberService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleNumberServiceImpl extends ServiceImpl<ArticleNumberMapper, ArticleNumber> implements ArticleNumberService {
    /**
     * 查询
     *
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Object queryAll(Pageable pageable, String name, List<Long> jurisdiction) {
        Page<ArticleNumber> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<ArticleNumber> pageResult = baseMapper.selectByPage(page, name, jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public ArticleNumber selectByArticleNumberId(Long id) {
        return baseMapper.selectByArticleNumberId(id);
    }

    @Override
    public ArticleNumber selectByArticleId(Long id) {
        return baseMapper.selectByArticleId(id);
    }
}

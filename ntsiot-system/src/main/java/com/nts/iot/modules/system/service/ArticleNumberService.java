package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.ArticleNumber;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleNumberService extends IService<ArticleNumber> {
    Object queryAll(Pageable pageable, String name, List<Long> jurisdiction);

    ArticleNumber selectByArticleNumberId(Long id);

    ArticleNumber selectByArticleId(Long id);
}

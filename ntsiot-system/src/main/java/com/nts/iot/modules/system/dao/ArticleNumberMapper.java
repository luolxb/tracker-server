package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.ArticleNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleNumberMapper extends BaseMapper<ArticleNumber> {
    Page<ArticleNumber> selectByPage(Page<ArticleNumber> page, @Param("name") String name, @Param("jurisdiction") List<Long> jurisdiction);

    ArticleNumber  selectByArticleNumberId(@Param("id") Long id);

    ArticleNumber selectByArticleId(@Param("id") Long id);
}

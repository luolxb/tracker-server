package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/4 13:23
 * @Description:
 */
@Repository
@Mapper
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 列表
     * @param page
     * @param title
     * @param jurisdictions
     * @return
     */
    Page<News> selectByPage(Page<News> page, @Param("title") String title, @Param("jurisdictions") List<String> jurisdictions);

    /**
     * 获取新闻公告内容
     * @param id
     * @return
     */
    News getByNewId(@Param("id") Long id);

    /**
     * 获取公告列表
     * @param deptId
     * @return
     */
    List<News> getNewsByDeptId(@Param("deptId") Long deptId);
}

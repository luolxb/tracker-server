package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface NewsService extends IService<News> {

    /**
     * 列表查询
     * @param title
     * @param jurisdictions
     * @param pageable
     * @return
     */
    Map queryAll(String title, List<String> jurisdictions, Pageable pageable);

    /**
     * 新增
     * @param news
     */
    @Transactional(rollbackFor = Exception.class)
    void saveNew(News news);

    /**
     * 删除
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteById(Long id);

    /**
     * 获取新闻公告内容
     * @param id
     * @return
     */
    News getByNewId(Long id);

    /**
     * 编辑
     * @param news
     */
    @Transactional(rollbackFor = Exception.class)
    void updateNewsById(News news);

    /**
     * 获取公告列表
     * @param deptId
     * @return
     */
    List<News> getNewsByDeptId(Long deptId);
}

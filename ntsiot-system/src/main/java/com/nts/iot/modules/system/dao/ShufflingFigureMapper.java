package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.ShufflingFigure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShufflingFigureMapper extends BaseMapper<ShufflingFigure> {

    /**
     * 查询
     *
     * @param page
     * @param name      名称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    Page<ShufflingFigure> queryAll(Page<ShufflingFigure> page, @Param("name") String name, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("jurisdiction") List<Long> jurisdiction);

    /**
     * 根据辖区id查询轮播图
     *
     * @param jurisdiction
     * @return
     */
    List<ShufflingFigure> findShufflingFigure(@Param("jurisdiction") String jurisdiction);

    /**
     * 根据url获得内容信息
     *
     * @param url
     * @return
     */
    ShufflingFigure getContent(@Param("url") String url);
}

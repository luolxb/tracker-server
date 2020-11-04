package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.ShufflingFigure;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;

import java.util.List;

@CacheConfig(cacheNames = "shufflingFigure")
public interface ShufflingFigureService extends IService<ShufflingFigure> {
    /**
     * 查询所有
     *
     * @param name
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable, String name, String startTime, String endTime,List<Long> jurisdiction);

    /**
     * 根据辖区id查询轮播图
     *
     * @param jurisdiction
     * @return
     */
    List<ShufflingFigure> findShufflingFigure(String jurisdiction);


    /**
     * 根据url获得内容信息
     *
     * @param url
     * @return
     */
    ShufflingFigure getContent(String url);
}

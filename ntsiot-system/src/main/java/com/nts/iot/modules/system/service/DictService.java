package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.DictDTO;
import com.nts.iot.modules.system.model.Dict;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-04-10
*/
@CacheConfig(cacheNames = "dict")
public interface DictService extends IService<Dict> {


    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    Dict create(Dict resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Dict resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 分页
     */
    Object queryAll(DictDTO dict,Pageable pageable);
}
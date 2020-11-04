package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.DictDetailDTO;
import com.nts.iot.modules.system.model.Dict;
import com.nts.iot.modules.system.model.DictDetail;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @author jie
* @date 2019-04-10
*/
@CacheConfig(cacheNames = "dictDetail")
public interface DictDetailService extends IService<DictDetail> {

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DictDetail create(DictDetailDTO resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(DictDetailDTO resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * queryAll
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    Object queryAll(DictDetailDTO resources, Pageable pageable);


    List<DictDetail> queryAll(DictDetailDTO dictDetail);

    Dict findDict(String dictName);
}
package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.TopUp;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;

import java.util.List;

@CacheConfig(cacheNames = "topUp")
public interface TopUpService extends IService<TopUp> {

    /**
     * 查询所有
     *
     * @param money
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable, String money, String startTime, String endTime, Long jurisdiction);

    /**
     * 根据辖区查询金额
     *
     * @return
     */
    List<String> queryMoneyByJurisdiction(Long jurisdiction);
}

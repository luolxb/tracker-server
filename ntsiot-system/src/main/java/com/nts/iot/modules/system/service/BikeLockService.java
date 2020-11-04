package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.BikeLock;
import org.springframework.cache.annotation.CacheConfig;

@CacheConfig(cacheNames = "bikeLock")
public interface BikeLockService extends IService<BikeLock> {
    /**
     * 根据车锁编号查询车锁个数
     *
     * @param lockBarcode
     * @return
     */
    Integer selectLockByLockBarcode(String lockBarcode);

    /**
     * create
     *
     * @param lockBarcode
     * @return
     */
    Integer create(String bikeBarcode, String lockBarcode);

    /**
     * update
     *
     * @param bikeLock
     */
    void update(BikeLock bikeLock);

    /**
     * 根据车锁查找
     * @param bylockBarcode
     * @return
     */
    BikeLock findBylockBarcode(String bylockBarcode);

    void initLockBike();

    /**
     * 根据车辆编号查找
     * @param byBikeBarcode
     * @return
     */
    BikeLock findByBikeBarcode(String byBikeBarcode);
}

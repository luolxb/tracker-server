package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.BikeLock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BikeLockMapper extends BaseMapper<BikeLock> {

    /**
     * 根据车锁编号查询车锁个数
     *
     * @param lockBarcode
     * @return
     */
    Integer selectLockByLockBarcode(@Param("lockBarcode") String lockBarcode);

    /**
     * 根据车编号查询该车是否绑定过车锁
     *
     * @param bikeBarcode
     * @return
     */
    BikeLock selectBikeLockByBikeBarcode(@Param("bikeBarcode") String bikeBarcode);


}

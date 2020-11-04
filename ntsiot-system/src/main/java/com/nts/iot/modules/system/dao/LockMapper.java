package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Lock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LockMapper extends BaseMapper<Lock> {

    /**
     * 查询车锁
     *
     * @param page
     * @param lockBarcode 车锁编号
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return
     */
    Page<Lock> queryAllById(Page<Lock> page, @Param("lockBarcode") String lockBarcode, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 查询未使用的车锁
     *
     * @return
     */
    List<Lock> selectLock();

    /**
     * 查询车锁状态
     *
     * @param id
     * @return
     */
    Lock selectLockStatus(@Param("id") Long id);

    /**
     * 修改车锁状态
     *
     * @param status
     * @param lockBarcode
     */
    void updateStatusLock(@Param("status") String status, @Param("lockBarcode") String lockBarcode);

    /**
     * 获得全部锁的信息
     *
     * @return
     */
    List<Lock> selectAll();

    /**
     * 根据锁编号查询锁信息
     *
     * @param lockNo 锁编号
     * @return 锁信息
     */
    List<Lock> selectLockByLockNo(@Param("lockNo") String lockNo);

    /**
     * 导出查询所有
     *
     * @return
     */
    List<Lock> selectLockAll(@Param("lockBarcode") String lockBarcode, @Param("startTime") String startTime, @Param("endTime") String endTime);


}

package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.Lock;
import com.nts.iot.modules.system.model.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CacheConfig(cacheNames = "lock")
public interface LockService extends IService<Lock> {
    /**
     * 查询所有必到点分页
     *
     * @param lockBarcode
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable, String lockBarcode, String startTime, String endTime);

    /**
     * create
     *
     * @param lock
     * @return
     */
    Integer create(Lock lock, User user);

    /**
     * update
     *
     * @param lock
     */
    void update(Lock lock, User user);

    /**
     * delete
     *
     * @param id
     */
    void delete(Long id);

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
     */
    Boolean selectLockStatus(Long id);

    /**
     * 修改车锁状态
     *
     * @param status
     * @param lockBarcode
     */
    void updateStatusLock(String status, String lockBarcode);

    /**
     * 获得全部锁信息
     *
     * @return
     */
    List<Lock> findAll();

    void initLock();

    Lock findLockByNo(String no);

    /**
     * 导出查询所有
     *
     * @return
     */
    void exportLock(HttpServletResponse res, String path,String lockBarcode, String startTime, String endTime);

    /**
     * 通过用户id 查询 车锁编号
     * @param userId 用户编号
     * @return
     */
    String getLockBarcodeByUserId(String userId);
}

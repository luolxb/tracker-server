package com.nts.iot.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.system.dao.BikeLockMapper;
import com.nts.iot.modules.system.model.BikeLock;
import com.nts.iot.modules.system.service.BikeLockService;
import com.nts.iot.modules.system.service.LockService;
import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeLockServiceImpl extends ServiceImpl<BikeLockMapper, BikeLock> implements BikeLockService {

    @Autowired
    private LockService lockService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据车锁编号查询车锁个数
     *
     * @param lockBarcode
     * @return
     */
    @Override
    public Integer selectLockByLockBarcode(String lockBarcode) {
        return baseMapper.selectLockByLockBarcode(lockBarcode);
    }

    /**
     * 创建车锁和车辆的中间表  （添加.车辆.信息智 调用此接口更新车辆和车锁中间表)）
     *
     * @param bikeBarcode 车辆编码
     * @param lockBarcode 车锁编码
     * @return
     */
    @Override
    public Integer create(String bikeBarcode, String lockBarcode) {
        // 查询关系表中是否有关系存在
        BikeLock oldBikeLock = baseMapper.selectBikeLockByBikeBarcode(bikeBarcode);
        // 车辆绑定过锁 将过去的车锁和车辆的中间表设置为0 并将锁状态改成0
        if (oldBikeLock != null && !"".equals(oldBikeLock.getLockBarcode())) {
            oldBikeLock.setIsDel("0");
            update(oldBikeLock);
            // 更改锁的状态
            lockService.updateStatusLock("0", oldBikeLock.getLockBarcode());
        }
        // 创建新的车锁关系
        BikeLock bikeLock = new BikeLock();
        bikeLock.setBikeBarcode(bikeBarcode);
        bikeLock.setLockBarcode(lockBarcode);
        bikeLock.setIsDel("1");
        bikeLock.setCreateTime(System.currentTimeMillis());
        // 更改锁的状态
        lockService.updateStatusLock("1", lockBarcode);
        redisUtil.addRedis(RedisKey.TRACKER_VECHILE_KEY + lockBarcode, JSON.toJSONString(bikeLock));
        return baseMapper.insert(bikeLock);
    }

    /**
     * 修改
     *
     * @param bikeLock
     */
    @Override
    public void update(BikeLock bikeLock) {
        redisUtil.updateRedis(RedisKey.TRACKER_VECHILE_KEY + bikeLock.getLockBarcode(), JSON.toJSONString(bikeLock));
        baseMapper.updateById(bikeLock);
    }

    @Override
    public BikeLock findBylockBarcode(String lockBarcode) {
        QueryWrapper<BikeLock> query = new QueryWrapper<>();
        query.eq("lock_barcode", lockBarcode);
        return baseMapper.selectOne(query);
    }

    @Override
    public void initLockBike() {
        List<BikeLock> list = baseMapper.selectList(null);
        list.forEach(it -> {
            String key = RedisKey.TRACKER_VECHILE_KEY + it.getLockBarcode();
            redisUtil.addRedis(key, JSON.toJSONString(it));
        });
    }

    @Override
    public BikeLock findByBikeBarcode(String bikeBarcode) {
        QueryWrapper<BikeLock> query = new QueryWrapper<>();
        query.eq("bike_barcode", bikeBarcode);
        return baseMapper.selectOne(query);
    }
}

package com.nts.iot.service.impl;

import com.nts.iot.manage.LockBikeManager;
import com.nts.iot.service.LockBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LockBikeServiceImpl implements LockBikeService {

    @Autowired
    private LockBikeManager lockCarManager;

    /**
     * 锁车
     *
     * @param deviceNo 锁编号
     * @return
     */
    @Override
    public Boolean lockBike(String deviceNo) {
        return lockCarManager.lockBike(deviceNo);
    }
}

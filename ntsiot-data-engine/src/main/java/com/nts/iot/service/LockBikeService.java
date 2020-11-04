package com.nts.iot.service;

public interface LockBikeService {
    /**
     * 锁车
     *
     * @param deviceNo 锁编号
     * @return
     */
    Boolean lockBike(String deviceNo);
}

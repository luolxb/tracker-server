package com.nts.iot.rest;

import com.nts.iot.service.LockBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 锁车
 */
@RestController
@RequestMapping("")
public class LockBikeController {

    @Autowired
    private LockBikeService lockCarService;

    /**
     * 锁车
     *
     * @param deviceNo 锁编号
     * @return
     */
    @RequestMapping("/lockBike/{deviceNo}")
    public Boolean saveByDeviceNo(@PathVariable String deviceNo) {
        return lockCarService.lockBike(deviceNo);
    }
}

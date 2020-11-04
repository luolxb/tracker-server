/*******************************************************************************
 * @(#)LockApiController.java 2019-06-18
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.system.rest;

import cn.hutool.core.date.DateUtil;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.BikeWarn;
import com.nts.iot.modules.system.model.Lock;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.BikeWarnService;
import com.nts.iot.modules.system.service.LockService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-06-18 15:12
 */
@RestController
public class LockApiController {

    @Autowired
    private LockService lockService;

    @Autowired
    BikeWarnService bikeWarnService;

    @Autowired
    BikeManagerService bikeManagerService;

    @Log("新增锁")
    @PostMapping("ma/lock/add")
    public ResponseEntity create(String deviceNo, String simId, String mac, String key) {

        if ("QAZwsxQWEasd".equals(key)) {

            Lock lock = null;
            if (deviceNo != null && !"".equals(deviceNo)) {
                lock = lockService.findLockByNo(deviceNo);
            }
            if (lock == null) {
                lock = new Lock();
            }
            lock.setLockBarcode(deviceNo);
            lock.setSimIccidNo(simId);
            lock.setMacAddress(mac);
            if (lock.getId() == null) {
                lock.setIsUse("0");
            }
            lock.setCreateTime(System.currentTimeMillis());

            System.out.println("===========deviceNo:" + deviceNo + "============");
            System.out.println("===========simId:" + simId + "============");
            System.out.println("===========mac:" + mac + "============");
            lockService.saveOrUpdate(lock);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 出圈车辆记录
     * @param collectMessages
     * @return
     */
    @PostMapping("ma/noticeOuter")
    public ResponseEntity noticeOuter(@RequestBody List<CollectMessage> collectMessages) {
        if (collectMessages!=null) {
            List<BikeWarn> bikeWarns = new ArrayList<>();
            for (CollectMessage message:collectMessages) {
                if(StringUtils.isBlank(message.getDeviceNo())){
                    continue;
                }
                /* 车辆告警表 */
                BikeWarn bikeWarn = new BikeWarn();
                bikeWarn.setId(null);
                /* 锁条码 */
                bikeWarn.setLockBarcode(message.getDeviceNo());
                /* 根据锁条码查询车辆 */
                Bike bike = bikeManagerService.getBikeByDeviceNo(message.getDeviceNo());
                if (bike!=null){
                    bikeWarn.setBikeBarcode(bike.getBikeBarcode());
                    bikeWarn.setDeptId(bike.getJurisdiction());
                }
                bikeWarn.setMessage("车辆驶出限行区域");
                bikeWarn.setLongitude(message.getLongitude());
                bikeWarn.setLatitude(message.getLatitude());
                /* 如果  */
                if (message.getTime()!=null){
                    bikeWarn.setTime(DateUtil.parse(message.getTime(), "yyyy-MM-dd HH:mm:ss").getTime());
                }else {
                    bikeWarn.setTime(System.currentTimeMillis());
                }
                bikeWarns.add(bikeWarn);
            }
            bikeWarnService.saveBatch(bikeWarns);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

}

package com.nts.iot.config;

import com.nts.iot.modules.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class BootLoadingRedisConfig {

    @Autowired
    private BikeManagerService bikeManagerService;

    @Autowired
    private FenceService fenceService;

    @Autowired
    private VirtualPileService virtualPileService;

    @Autowired
    private CheckPointService checkPointService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private BikeLockService bikeLockService;

    @Autowired
    private LockService lockService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private OrderManagerService orderManagerService;

    @Autowired
    private DeviceService deviceService;

    /**
     * 启动程序，添加到缓存中
     */
    @PostConstruct
    public void init() {
        System.out.println("================================================");
        System.out.println("================================================");
        System.out.println("=================开始加载=======================");
        System.out.println("================================================");
        System.out.println("================================================");
        //初始化车辆信息
//        bikeManagerService.initBikes();
        deviceService.initDevices();

        //初始化围栏信息
        fenceService.initFences();
        //初始化虚拟桩
        virtualPileService.initPiles();
        // 必到点
        checkPointService.getCheckPointAll();
        // 辖区
        deptService.getDeptAll();
        //车、锁
//        bikeLockService.initLockBike();
        // 智能模块
//        lockService.initLock();
        // 初始化辖区小程序配置
        appConfigService.initAppConfig();
        // 初始化订单
        orderManagerService.initOrderIds();
        System.out.println("================================================");
        System.out.println("================================================");
        System.out.println("=================结束加载=======================");
        System.out.println("================================================");
        System.out.println("================================================");
    }
}

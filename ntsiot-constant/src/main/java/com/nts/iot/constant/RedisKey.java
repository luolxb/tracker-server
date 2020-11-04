/*******************************************************************************
 * @(#)RedisKey.java 2019-05-13
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.constant;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-13 20:41
 */
public final class RedisKey {

    /**
     * 超速告警缓存
     */
    public static final String OVERSPEED_ALARM_KEY = "OVERSPEED:ALARM:KEY:";

    /**
     * http开锁失败状态(false)
     */
    public static final String HTTP_OPENLOCK = "HTTP_OPENLOCK:KEY:";

    /**
     * 必到点redisKey名
     */
    public static final String CHECK_POINT = "CHECK_POINT:KEY:";

    /**
     * 虚拟桩redisKey
     */
    public static final String VIRTUAL_PILE = "VIRTUAL_PILE:KEY:";

    /**
     * 保存车辆信息 (传参为车辆编号)
     */
    public static final String VECHILE_KEY = "VECHILE:KEY:";

    /**
     * 设备心跳
     */
    public static final String TRACKER_HEARTBEAT_KEY = "TRACKER:HEARTBEAT:KEY:";

    /**
     * 保存车辆信息 (传参为车锁编号)
     */
    public static final String TRACKER_KEY = "TRACKER:KEY:";

    /**
     * 限行围栏
     */
    public static final String RESTRICTIONS_FENCE = "RESTRICTIONS:FENCE:KEY:";

    /**
     * 车辆状态key
     */
    public static final String VECHILE_STATE = "VECHILE:STATE:";
    /**
     * 任务需要做每小时任务的key
     */
    public static final String VECHILE_RECORD_TASK = "VECHILE:RECORD:";

    /**
     * 订单编号+锁编号+订单编号
     */
    public static final String ORDER_KEY = "ORDER:KEY:";

    /**
     * 任务需要做每小时任务的key
     */
    public static final String TRACKER_DEVICE_RECORD_TASK = "VECHILE:RECORD:";

    /**
     * 订单轨迹 订单编号 + 锁编号
     */
    public static final String ORDER_TRAJECTORY_KEY = "ORDER:TRAJECTORY:KEY:";

    /**
     * 辖区key
     */
    public static final String JURISDICTION_KEY = "JURISDICTION:KEY:";

    /**
     * 辖区列表的key
     */
    public static final String LIST_JURISDICTION_KEY = "LIST:JURISDICTION:KEY";

    /**
     * 车、锁redisKey
     */
    public static final String TRACKER_VECHILE_KEY = "TRACKER:VECHILE:KEY:";

    /**
     * 智能模块的key
     */
    public static final String TRACKER_LIST_KEY = "TRACKERLIST::KEY";


    /**
     * 锁编号
     */
    public static final String PRE_ORDER_KEY = "PRE:ORDER:KEY:";

    /**
     * weixin ma key
     */
    public static final String WX_MA_KEY = "WX:MA:KEY:";

    /**
     * 任务实例key (参数为 实例id)
     */
    public static final String TASK_INSTANCE_KEY = "TASK:INSTANCE:KEY:";

    /**
     * 车辆key list
     */
    public static final String LIST_VECHILE_KEY = "LIST:VECHILE:KEY";

    /**
     * 订单编号list
     */
    public static final String LIST_ORDER_KEY = "LIST:ORDER:KEY";

    /**
     * 用户全信息key
     */
    public static final String USER_INFO_KEY = "USER:INFO:KEY:";
    /**
     * 用户权限信息key
     */
    public static final String USER_AUTHORITY_KEY = "USER:AUTHORITY:KEY:";
}

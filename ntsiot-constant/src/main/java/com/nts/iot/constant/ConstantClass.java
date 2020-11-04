package com.nts.iot.constant;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/18 14:20
 * @Description:
 */

public final class ConstantClass {

    /**
     * 字典（order_status）订单状态：已预约
     */
    public static final Long ORDER_STATUS_00 = 0L;
    /**
     * 字典（order_status）订单状态：已关闭
     */
    public static final Long ORDER_STATUS_01 = 1L;
    /**
     * 字典（order_status）订单状态：进行中
     */
    public static final Long ORDER_STATUS_02 = 2L;
    /**
     * 字典（order_status）订单状态：临停中
     */
    public static final Long ORDER_STATUS_03 = 3L;
    /**
     * 字典（order_status）订单状态：待开锁
     */
    public static final Long ORDER_STATUS_04 = 4L;
    /**
     * 字典（order_status）订单状态：已失效
     */
    public static final Long ORDER_STATUS_05 = 5L;

    /**
     * 车辆状态 ：正常使用
     */
    public static final Long BIKE_STATUS_00 = 0L;

    /**
     * 车辆状态 ：故障车
     */
    public static final Long BIKE_STATUS_01 = 1L;

    /**
     * 车辆状态 ：电量低 TODO
     */
    public static final Long BIKE_STATUS_02 = 2L;

    /**
     * 删除状态 ：未删除
     */
    public static final String is_del_00 = "0";

    /**
     * 删除状态 ：已删除
     */
    public static final String is_del_01 = "1";


    /* 任务重复频率 */
    // 立即执行
    public static final String TASK_REPEAT_SINGLE = "single";
    // 每日
    public static final String TASK_REPEAT_DAY = "day";
    // 每周
    public static final String TASK_REPEAT_WEEK_OF_DAY = "weekOfDay";


    public static  String switchlanguage = "zh_cn";

}

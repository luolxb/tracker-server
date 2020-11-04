package com.nts.iot.constant;


import java.math.BigDecimal;

/**
 * <pre>
 *      微信小程序REST 与 JT808 server REST 通信协定值
 * </pre>
 *
 * @author zzm
 */
public class MiniAppConstants {

    // 开启成功
    public static final String OPEN_LOCK_FINISH = "0";

    // 开启失败
    public static final String OPEN_LOCK_FAILED = "1";

    // 开启成功
    public static final String CLOSE_LOCK_FINISH = "0";

    // 开启失败
    public static final String CLOSE_LOCK_FAILED = "1";

    // 消息头： 反控
    public static final String MESSAGE_HEAD_CONTROL = "8500";

    // 反控内容: 开锁
    public static final String CONTENT_CONTROL_OPEN_LOCK = "0100";

    // 反控内容: 关锁
    public static final String CONTENT_CONTROL_CLOSE_LOCK = "0101";

    // 反控内容: 蜂鸣打开
    public static final String CONTENT_CONTROL_OPEN_BUZZER = "0201";

    // 反控内容: 蜂鸣关闭
    public static final String CONTENT_CONTROL_CLOSE_BUZZER = "0200";

    // 设置终端参数
    public static final String CONTENT_CONTROL_SETTING = "0300";

    // 获得终端参数
    public static final String CONTENT_CONTROL_GET_SETTING = "0500";


    /*
     *
     * 订单状态（0:待支付; 1:已支付; 2:预付; 3：退款; 4:取消 ; 5:加油完成，订单完成 ; 6:回调失败，订单作废【恶意的修改订单金额或者是签名不对】 ）
     * 【详细说明】
     *
     * 1、创建订单未支付：订单状态设置为 【0:待支付】
     * 2、创建订单已支付：订单状态设置为 【1:已支付】
     * 3、创建订单已支付（加油类型：加满）：订单状态设置为 【2:预付】
     * 4、因为某原因整个订单进行全退款操作：订单状态设置为 【3：退款】
     * 5、订单长期处于未支付状态，定时任务更新订单状态：订单状态设置为 【4:取消】
     * 6、订单-创建-支付-加油完成-（需要退多余金额并退款）：订单状态设置为 【5:加油完成，订单完成】
     * 7、订单启用微信支付，微信支付之后回调失败（恶意的修改订单金额或者是签名不对）：订单状态设置为 【6:回调失败，订单作废】
     *
     */

    public static final Integer STATUS_UNPAID = 0;
    public static final Integer STATUS_PAID = 1;
    public static final Integer STATUS_ADVANCE = 2;
    public static final Integer STATUS_REFUND = 3;
    public static final Integer STATUS_CANCEL = 4;
    public static final Integer OIL_FINISH = 5;
    public static final Integer ORDER_INVALID = 6;


    /**
     * 消费流水表-类型（0:退款; 1:订单支付; 2:充值）
     */
    public static final Integer TYPE_REFUND = 0;
    public static final Integer TYPE_ORDER_PAY = 1;
    public static final Integer TYPE_PAY = 2;

    /**
     * 充值规则-状态（0：禁用 , 1：启动）
     */
    public static final Integer RULE_STATUS_DISABLE = 0;
    public static final Integer RULE_STATUS_ENABLE = 1;


    /**
     * 小程序用户类型（0:外部人员; 1:内部人员）
     */
    public static final Integer USER_TYPE_INNER = 0;
    public static final Integer USER_TYPE_OUTER = 1;

    /**
     * 任务完成状态（0:未完成; 1:已完成；2：超时；3：进行中）
     */
    public static final String MISSION_UNSUCCESS = "0";
    public static final String MISSION_SUCCESS = "1";
    public static final String MISSION_TIMEOUT = "2";
    public static final String MISSION_ON = "3";


    /**
     * 充值记录表状态
     * （0：未支付 ；
     *   1：已支付 ；
     *   2：作废（恶意的修改充值金额或者是签名不对）
     *   ）
     */
    public static final Integer STATUS_RECORD_UNPAID = 0;
    public static final Integer STATUS_RECORD_PAID = 1;
    public static final Integer STATUS_RECORD_INVALID = 2;


    /**
     * 任务图片type
     */
    public static final String REAL_TASK_IMG = "REAL_TASK_IMG";
    public static final String REAL_TASK_MP3 = "REAL_TASK_MP3";
    public static final String REAL_TASK_VIDEO = "REAL_TASK_VIDEO";
}

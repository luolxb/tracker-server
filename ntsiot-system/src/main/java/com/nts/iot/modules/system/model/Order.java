package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author zhc@rnstec.com
 * @Description 订单表
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */

@TableName("orders")
@Data
public class Order extends Model<Order> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 订单状态
     */
    private Long status;

    /**
     * 使用用户
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 车辆编号
     */
    @TableField(value = "bike_barcode")
    private String bikeBarcode;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private Long startTime;

    /**
     * 开始经度
     */
    @TableField(value = "start_lng")
    private String startLng;

    /**
     * 开始纬度
     */
    @TableField(value = "start_lat")
    private String startLat;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private Long endTime;

    /**
     * 结束经度
     */
    @TableField(value = "end_lng")
    private String endLng;

    /**
     * 结束纬度
     */
    @TableField(value = "end_lat")
    private String endLat;

    /**
     * 订单里程
     */
    @TableField(value = "mile")
    private Double mile;

    /**
     * 辖区编号
     */
    private Long jurisdiction;

    /**
     * 结束来源 0：正常关闭； 1：后台关闭
     */
    @TableField(value = "close_source")
    private Long closeSource;

    /**
     * 订单金额
     */
    @TableField(value = "order_amount")
    private Double orderAmount;

    /**
     * 补偿金额
     */
    @TableField(value = "compensation_amount")
    private Double compensationAmount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Long updateTime;


    /**
     * 用户
     */
    @TableField(exist = false)
    private String username;

    /**
     * 电话
     */
    @TableField(exist = false)
    private String phone;

    /**
     * 辖区
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 骑行时长
     */
    @TableField(exist = false)
    private String timeLength;

    /**
     * 车锁编号
     */
    @TableField(exist = false)
    private String lockBarcode;

    /**
     * 1-无需开锁；2-扫码开锁；
     */
    @TableField(value = "unlock_mode")
    private int unlockMode;
}

package com.nts.iot.modules.miniApp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户账目表
 * </p>
 *
 * @author zzm
 * @since 2019-07-09
 */
@Data
@TableName("t_fee_config")
public class FeeConfig extends Model<FeeConfig> implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 计价周期
     */
    @TableField("circle_min")
    private Integer circleMin;

    /**
     * 计价周期对应的费用
     */
    @TableField("circle_fee")
    private BigDecimal circleFee;


    /**
     * 最大计价周期
     */
    @TableField("max_circle_min")
    private Integer maxCircleMin;

    /**
     * 最大费用
     */
    @TableField("max_circle_fee")
    private BigDecimal maxCircleFee;

    /**
     * 最大费用
     */
    @TableField("max_order_fee")
    private BigDecimal maxOrderFee;

    /**
     * 桩外还车扣费
     */
    @TableField("out_park_fee")
    private BigDecimal outParkFee;


    /**
     * 围栏外还车扣费
     */
    @TableField("out_fence_fee")
    private BigDecimal outFenceFee;


    /**
     * 最小需要缴纳多少押金
     */
    @TableField("min_deposit")
    private BigDecimal minDeposit;

    /**
     * 最小账户余额
     */
    @TableField("min_balance")
    private BigDecimal minBalance;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private List<FeeConfigWorker> worker;

}

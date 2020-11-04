package com.nts.iot.modules.miniApp.model;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 用户账目表
 * </p>
 *
 * @author zzm
 * @since 2019-06-11
 */
@Data
@TableName("t_fee_config_worker")
public class FeeConfigHoliday extends Model<FeeConfigHoliday> implements Serializable {

    /**
     * 用户账目表编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 费用id
     */
    @TableField("fee_id")
    private Long feeId;

    private Long timeStart;

    private Long timeEnd;

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
     * 最大费用
     */
    @TableField("max_fee")
    private BigDecimal maxFee;

}

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
 * 充值记录表
 * </p>
 *
 * @author zzm
 * @since 2019-06-11
 */
@Data
@TableName("t_recharge_record")
public class RechargeRecord extends Model<RechargeRecord> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商户充值记录编号
     */
    @TableField("out_recharge_id")
    private String outRechargeId;
    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 流水编号
     */
    @TableField("history_id")
    private Long historyId;
    /**
     * 充值金额
     */
    @TableField("charges_count")
    private BigDecimal chargesCount;
    /**
     * 赠送金额
     */
    @TableField("largess_count")
    private BigDecimal largessCount;
    /**
     * 实际金额
     */
    @TableField("actual_count")
    private BigDecimal actualCount;
    /**
     * 充值时间
     */
    @TableField("charges_time")
    private Long chargesTime;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String deptName;

}

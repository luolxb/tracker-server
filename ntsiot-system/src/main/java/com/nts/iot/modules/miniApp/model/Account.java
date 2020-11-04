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
@TableName("t_account")
public class Account extends Model<RechargeRecord> implements Serializable {

    /**
     * 用户账目表编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 当前金额
     */
    private BigDecimal balance;
    /**
     * 消费总金额
     */
    @TableField("total_expend")
    private BigDecimal totalExpend;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String deptName;

}

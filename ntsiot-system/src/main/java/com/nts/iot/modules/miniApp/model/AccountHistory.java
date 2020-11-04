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
 *  账户流水表
 * </p>
 *
 * @author zzm
 * @since 2019-06-11
 */
@Data
@TableName("t_account_history")
public class AccountHistory extends Model<AccountHistory> implements Serializable {

    /**
     * 流水编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账号编号
     */
    @TableField("account_id")
    private Long accountId;
    /**
     * 账户余额
     */
    private BigDecimal balance;
    /**
     * 余额支付
     */
    @TableField("balance_amount")
    private BigDecimal balanceAmount;
    /**
     * 微信支付
     */
    @TableField("wexin_amount")
    private BigDecimal wexinAmount;
    /**
     * 微信流水号
     */
    @TableField("weixin_no")
    private String weixinNo;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
    /**
     * 类型（0:退款; 1:订单支付; 2:充值）
     */
    private Integer type;

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

package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 物品类别
 */
@Data
@TableName("article")
public class Article {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 物品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 辖区id
     */
    @TableField(value = "jurisdiction")
    private Long jurisdiction;

    /**
     * 创建日期
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String jurisdictionName;

    /**
     * 是否需要归还
     */
    @TableField(value = "is_repay")
    private String isRepay;

    /**
     * 是否归还
     */
    @TableField(exist = false)
    private String isRepayName;

    /**
     * 数量
     */
    @TableField(exist = false)
    private String number;

    /**
     * 借出数量
     */
    @TableField(exist = false)
    private String loanNumber;
}

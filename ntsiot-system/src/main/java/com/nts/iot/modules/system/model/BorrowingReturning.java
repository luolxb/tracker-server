package com.nts.iot.modules.system.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nts.iot.modules.system.dto.LoanCountDto;
import lombok.Data;

import java.util.List;

/**
 * 借还管理记录
 */
@Data
@TableName("borrowing_returning")
public class BorrowingReturning {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 领取时间
     */
    @TableField(value = "get_time")
    private Long getTime;

    /**
     * 归还时间
     */
    @TableField(value = "repay_time")
    private Long repayTime;

    /**
     * 主表id
     */
    @TableField(value = "article_id")
    private Long articleId;


    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 借用人openid
     */
    @TableField(value = "ma_open_id")
    private String maOpenId;

    /**
     * 借用个数
     */
    @TableField(value = "number")
    private String number;

    /**
     * 是否需要归还
     */
    @TableField(exist = false)
    private String isRepayName;

    /**
     * 物品名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String jurisdictionName;

    /**
     * 日期
     */
    @TableField(exist = false)
    private String date;

    /**
     * 辖区
     */
    @TableField(value = "jurisdiction")
    private Long jurisdiction;


    @TableField(exist = false)
    private String isRepay;

    @TableField(exist = false)
    private String username;


    @TableField(exist = false)
    private List<LoanCountDto> loanCountDtoList;

    /**
     * 归还时间
     */
    @TableField(exist = false)
    private String repayTimeStr;

    /**
     * code
     */
    @TableField(exist = false)
    private String code;

    /**
     * isGive
     */
    @TableField(exist = false)
    private String isGive;

    /**
     * 归还数量
     */
    @TableField(exist = false)
    private String giveNumber;

    /**
     * 用户id
     */
    @TableField(exist = false)
    private String userId;
}

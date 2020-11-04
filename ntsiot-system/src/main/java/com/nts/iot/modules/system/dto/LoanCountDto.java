package com.nts.iot.modules.system.dto;


import lombok.Data;

/**
 * 物品借出个数duo
 */
@Data
public class LoanCountDto {
    /**
     * 主表id,记录表id
     */
    private Long id;

    /**
     * 主表id,记录表id
     */
    private Long borrowingReturningId;

    /**
     * 物品表id
     */
    private Long articleId;

    /**
     * 借用数量
     */
    private String number;

    /**
     * 归还数量
     */
    private String giveNumber;

    /**
     * 商品名称
     */
    private String name;

    private String isRepay;

    /**
     * 领用人
     */
    private String username;

    /**
     * 领用时间
     */
    private String getTime;

    /**
     * 归还时间
     */
    private String giveTime;
    /**
     * 辖区名称
     */
    private String jurisdictionName;

    /**
     * 父级辖区
     */
    private String pName;

    /**
     * 状态
     */
    private String state;



}

package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 物品数量
 */
@Data
@TableName("article_number")
public class ArticleNumber {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 总数量
     */
    @TableField(value = "total")
    private String total;

    /**
     * 借出数量
     */
    @TableField(value = " loan_number")
    private String loanNumber;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;


    /**
     * 物品表id
     */
    @TableField(value = "article_id")
    private Long articleId;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private Long jurisdiction;

    @TableField(exist = false)
    private String jurisdictionName;

}

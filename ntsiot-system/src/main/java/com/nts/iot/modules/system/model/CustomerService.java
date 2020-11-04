package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 4S店与客服
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("customer_service")
public class CustomerService {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_by")
    private String updateBy;
    /**
     * 删除标记 1：正常  2：删除
     */
    @TableField("del_flag")
    private Long delFlag;

    /**
     * b
     */
    @TableField("help_phone")
    private String helpPhone;

    /**
     * 预约电话
     */
    @TableField("booking_line")
    private String bookingLine;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
}

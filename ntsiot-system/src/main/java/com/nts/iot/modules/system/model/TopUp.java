package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("top_up")
public class TopUp extends Model<TopUp> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 充值金额
     */
    @TableField(value = "money")
    private String money;

    /**
     * 辖区编号
     */
    @TableField(value = "jurisdiction")
    private Long jurisdiction;

    /**
     * 创建日期
     */
    @TableField(value = "create_time")
    private Long createTime;
}

package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/6 11:16
 * @Description:
 */
@TableName("common_problem")
@Data
public class CommonProblem extends Model<CommonProblem> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 辖区
     */
    private Long jurisdiction;

    /**
     * 是否显示
     */
    @TableField("is_visible")
    private String isVisible;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;
}

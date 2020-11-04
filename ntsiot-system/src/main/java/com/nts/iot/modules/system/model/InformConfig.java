package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 新闻
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:29
 * @Description:
 */

@TableName("inform_config")
@Data
public class InformConfig extends Model<InformConfig> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 新闻标题
     */
    private String title;


    /**
     * 新闻内容
     */
    private String content;

    /**
     * 辖区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Long updateTime;
}

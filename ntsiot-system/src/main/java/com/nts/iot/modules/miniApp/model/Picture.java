package com.nts.iot.modules.miniApp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 工作日报 文件表
 */
@Data
@TableName("t_picture")
public class Picture extends Model<Picture> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 主表id
     */
    @TableField("pk")
    private Long pk;

    /**
     * 图片地址
     */
    @TableField("path")
    private String path;

    /**
     * 类别
     */
    @TableField("type")
    private String type;
}

package com.nts.iot.modules.system.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("shuffling_figure")
public class ShufflingFigure extends Model<ShufflingFigure> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 轮播图名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 是否启用
     */
    @TableField(value = "is_enable")
    private String isEnable;

    /**
     * 辖区id
     */
    @TableField(value = "jurisdiction")
    private Long jurisdiction;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String jurisdictionName;

    /**
     * 是否显示
     */
    @TableField(exist = false)
    private String radio;

    /**
     * url链接
     */
    @TableField(value = "url_link")
    private String urlLink;

    /**
     * 创建日期
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

}

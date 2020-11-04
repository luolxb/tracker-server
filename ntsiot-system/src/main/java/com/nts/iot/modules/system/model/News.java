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

@TableName("news")
@Data
public class News extends Model<News> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻类型
     */
    private String type;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 是否显示
     */
    @TableField("is_visible")
    private String isVisible;

    /**
     * 辖区
     */
    private Long jurisdiction;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String jurisdictionName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 图片路径
     */
    @TableField(exist = false)
    private String urlLink;

    /**
     * 类型
     */
    @TableField(exist = false)
    private String tag;

    /**
     * 发布时间
     */
    @TableField(exist = false)
    private String time;

}

package com.nts.iot.modules.miniApp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * 工作日报
 */
@Data
@TableName("work_diary")
public class WorkDiary extends Model<WorkDiary> {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 图片urlList
     */
    @TableField(exist = false)
    private List<String> sourceData;

    /**
     * 视频urlList
     */
    @TableField(exist = false)
    private List<String> videoList;

    /**
     * 音频urlList
     */
    @TableField(exist = false)
    private String frequency;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 坐标点
     */
    @TableField("point")
    private String point;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;


    @TableField("open_id")
    private String openId;

    /**
     * 上传人
     */
    @TableField("name")
    private String name;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String jurisdictionName;

}

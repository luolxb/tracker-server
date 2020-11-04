package com.nts.iot.modules.miniApp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 违法举报
 */
@Data
@TableName("illegal_report")
public class IllegalReport {

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
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 手机号
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 辖区编号
     */
    @TableField("jurisdiction")
    private Long jurisdiction;

    /**
     * 是否受理 0:未受理，1：已受理 2：无需受理 3：无效举报
     */
    @TableField("type")
    private String type;

    /**
     * 是否受理
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 坐标点
     */
    @TableField("point")
    private String point;

    /**
     * 受理人
     */
    @TableField("user_name")
    private String userName;

    /**
     * 辖区名称
     */
    @TableField("jurisdiction_name")
    private String jurisdictionName;

    /**
     * 辖区父名称
     */
    @TableField(exist = false)
    private String pname;

    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String time;

    @TableField(exist = false)
    private String deptName;

    /**
     * 处理时间
     */
    @TableField("deal_time")
    private Long dealTime;

    /**
     * 备注
     */
    private String remark;
}

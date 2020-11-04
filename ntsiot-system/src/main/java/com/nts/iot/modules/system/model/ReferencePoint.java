package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/5 11:15
 * @Description:
 */
@TableName("reference_point")
@Data
public class ReferencePoint extends Model<ReferencePoint> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    @TableField("icon_type")
    private Long iconType;

    /**
     * 类型
     */
    @TableField(exist = false)
    private String type;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 描述
     */
    private String remark;

    /**
     * 辖区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;
}

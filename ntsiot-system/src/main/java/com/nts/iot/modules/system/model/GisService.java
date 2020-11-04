package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gis_service")
public class GisService extends Model<GisService> {

    /**
     * GIS服务id
     */
    private Long id;
    /**
     * gis服务名称
     */
    private String name;
    /**
     * gis服务类型
     */
    private String type;
    /**
     * gis服务经度
     */
    private String latitude;

    /**
     * gis服务纬度
     */
    private String longitude;
    /**
     * gis服务备注
     */
    private String remark;
    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_by")
    private String updateBy;

    /**
     * gis服务所属用户id
     */
    private Long userId;
}

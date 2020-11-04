package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
* @author jie
* @date 2019-03-25
*/
@Data
@TableName("dept")
@ApiModel("部门")
public class Dept extends Model<Dept> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("部门ID")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("状态")
    private Boolean enabled;

    /**
     * 上级部门
     */
    @ApiModelProperty("上级部门")
    private Long pid;

    /**
     * 树形结构的编码（用于父级查询子级）
     */
    @ApiModelProperty("树形结构的编码（用于父级查询子级）")
    private String number;

    private String code;

    private String logo;

    @TableField("bike_icon")
    private String bikeIcon;

    @TableField("app_id")
    private String appId;

    private String secret;

    private String token;

    @TableField("aes_key")
    private String aesKey;

    @TableField("create_time")
    private Timestamp createTime;

    /**
     * 市公安局电话
     */
    @TableField("city_phone")
    private String cityPhone;

    /**
     * 辖区电话
     */
    @TableField("dept_phone")
    private String deptPhone;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    private String latitude;

    /**
     * 授权认证
     */
    @TableField(value = "check_license")
    private Boolean checkLicense;

    @TableField(exist = false)
    private Set<Role> roles;

    @TableField(exist = false)
    private List<AppModule> appModules;

}
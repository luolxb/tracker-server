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

/**
 * @author jie
 * @date 2018-12-17
 */
@Data
@TableName("menu")
@ApiModel("菜单")
public class Menu extends Model<Menu> {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("菜单ID")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("排序")
    private Long sort;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("图标")
    private String icon;

    /**
     * 是否隐藏
     */
    @ApiModelProperty("是否隐藏")
    private String hidden;

    /**
     * 上级菜单ID
     */
    @ApiModelProperty("上级菜单ID")
    private Long pid;

    /**
     * 是否为外链 true/false
     */
    @ApiModelProperty("是否为外链 true/false")
    @TableField("i_frame")
    private Boolean iFrame;

    @TableField("create_time")
    private Timestamp createTime;

//    @TableField(exist = false)
//    private Set<Role> roles;

}

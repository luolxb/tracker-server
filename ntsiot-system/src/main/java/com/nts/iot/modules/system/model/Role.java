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
import java.util.Set;

/**
 * 角色
 *
 * @author jie
 * @date 2018-11-22
 */
@Data
@TableName("role")
@ApiModel("角色")
public class Role extends Model<Role> {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("角色ID")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("数据权限类型 默认本级 全部 、 本级 、 自定义")
    // 数据权限类型 全部 、 本级 、 自定义
    @TableField("data_scope")
    private String dataScope = "本级";

    @ApiModelProperty("备注")
    private String remark;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField(exist = false)
    private Set<User> users;

    @TableField(exist = false)
    private Set<Permission> permissions;

    @TableField(exist = false)
    private Set<Menu> menus;

    @TableField(exist = false)
    private Set<Dept> depts;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createDateTime=" + createTime +
                '}';
    }

    public enum RoleDataScope {
        All("全部"), CurrentLevel("本级"), Custom("自定义");

        public String text;

        RoleDataScope(String text) {
            this.text = text;
        }

    }

}

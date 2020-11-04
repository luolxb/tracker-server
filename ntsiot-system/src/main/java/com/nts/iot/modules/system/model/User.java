package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author jie
 * @date 2018-11-22
 */
@Data
@TableName("user")
@ApiModel("用户实体类")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User extends Model<User> {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户图像")
    private String avatar;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty(value = "用户电话",name="phone",required = false)
    private String phone;

    @ApiModelProperty("状态：1启用、0禁用")
    private Boolean enabled;

    @JsonIgnore
    private String password;

    @TableField("nick_name")
    private String nickName;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("last_password_reset_time")
    private Date lastPasswordResetTime;

    /**
     * 客户类型ID 客户类型ID（数据字典new__dict）
     */
    @TableField("custom_type_id")
    public Long customTypeId;

    @TableField("p_id")
    private Long pId;

    /**
     * 地址
     */
    @ApiModelProperty(value="用户地址",name ="address",required=false)
    private String address;

    /**
     * 联系人
     */
    @ApiModelProperty(value="联系人",name = "contactPerson",required = false)
    @TableField("contact_person")
    private String contactPerson;


    /***
     * 删除标记 1正常  2删除
     */
    @TableField("del_flag")
    private Integer delFlag;



    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String icon;

    /**
     * 公众号open id
     */
    @ApiModelProperty("公众号open id")
    @TableField("mp_open_id")
    private String mpOpenId;

    /**
     * 小程序open id
     */
    @ApiModelProperty("小程序open id")
    @TableField("ma_open_id")
    private String maOpenId;

    /**
     * unionId
     * 用户唯一标识符
     */
    @ApiModelProperty("用户唯一标识符")
    @TableField("union_id")
    private String unionId;

    @TableField(exist = false)
    @ApiModelProperty("角色集合")
    private Set<Role> roles;

    @TableField(exist = false)
    @ApiModelProperty("子集")
    private List<User> childUser;

    /**
     * 设备数量
     */
    @TableField(exist = false)
    @ApiModelProperty("设备数量")
    private Integer deviceSum;

}
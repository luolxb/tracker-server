package com.nts.iot.modules.miniApp.model;



import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 小程序用户表
 * </p>
 *
 * @author zzm
 * @since 2019-06-26
 */
@Data
@TableName("t_ma_user")
public class MaUser extends Model<MaUser> implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名称
     */
    @TableField(value = "name" ,strategy = FieldStrategy.IGNORED)
    private String name;

    /**
     * 电话号
     */
    @TableField(value = "phone" ,strategy = FieldStrategy.IGNORED)
    private String phone;

    /**
     * openid
     */
    @TableField("openid")
    private String openid;

    /**
     * appid
     */
    @TableField("appid")
    private String appid;

    /**
     * 辖区编号（※忽略null值的判断）
     */
    @TableField(value = "dept_id" ,strategy = FieldStrategy.IGNORED)
    private Long deptId;

    /**
     * 小程序用户类型（0:外部人员; 1:内部人员）
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

}

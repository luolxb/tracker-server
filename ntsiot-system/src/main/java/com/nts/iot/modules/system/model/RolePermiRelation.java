package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("roles_permissions")
public class RolePermiRelation extends Model<RolePermiRelation> {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色编号
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 权限编号
     */
    @TableField(value = "permission_id")
    private Long permissionId;
}


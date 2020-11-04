package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author jie
 * @date 2018-12-03
 */
@Data
@TableName("permission")
@ApiModel("权限")
public class Permission extends Model<Permission> {

	@ApiModelProperty("权限ID")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@NotBlank
	@ApiModelProperty("权限名称")
	private String name;

	/**
	 * 上级类目
	 */
	@ApiModelProperty("父ID")
	private Long pid;

	@ApiModelProperty("别名")
	private String alias;

	@TableField(exist = false)
	private Set<Role> roles;

	@TableField("create_time")
	private Timestamp createTime;

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pid=" + pid +
				", alias='" + alias + '\'' +
				", createTime=" + createTime +
				'}';
	}

	public @interface Update{}
}

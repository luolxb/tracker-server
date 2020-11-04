package com.nts.iot.modules.system.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nts.iot.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @PackageName: com.nts.iot.modules.system.model.vo
 * @program: nts
 * @author: ruosen
 * @create: 2020-04-30 09:34
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserVo implements Serializable {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("登录账号")
    @Excel(name = "登录账号")
    private String username;

//    @NotNull(message = "用户邮箱不能为空")
//    @ApiModelProperty("用户邮箱")
//    private String email;

    @ApiModelProperty("用户电话")
    @Excel(name = "用户电话")
    private String phone;

    @ApiModelProperty("密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty("地址")
    @Excel(name = "地址")
    private String address;

    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contactPerson;

    @ApiModelProperty("客户名称")
    @Excel(name = "客户名称")
    private String nickName;

    @ApiModelProperty("上级客户")
    private Long pId;

    /**
     * 客户类型ID 客户类型ID（数据字典new__dict）
     */
    @ApiModelProperty("类型")
    public Long customTypeId;

    @ApiModelProperty("设备数量")
    @Excel(name = "设备数量")
    private Integer deviceSum;

}

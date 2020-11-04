package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

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
public class UserRq {

    @ApiModelProperty("用户ID")
    private Long id;

    @NotBlank(message = "登录账号不能为空")
    @ApiModelProperty("登录账号")
    private String username;

//    @NotNull(message = "用户邮箱不能为空")
//    @NotEmpty(message = "用户邮箱不能为空")
//    @Email(message = "邮箱格式不正确")
//    @ApiModelProperty("用户邮箱")
//    private String email;

    @NotBlank(message = "用户电话不能为空")
    @ApiModelProperty("用户电话")
    private String phone;

    @ApiModelProperty("密码")
    @Size(min = 6,max = 20,message = "密码长度6~20")
    private String password;

    @ApiModelProperty("确认密码")
    private String confirmPassword;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("联系人")
    private String contactPerson;

    @NotBlank(message = "昵称/客户名称不能为空")
    @ApiModelProperty("昵称/客户名称")
    private String nickName;

    @NotNull(message = "上级客户不能为空")
    @ApiModelProperty("上级客户")
    private Long pId;

    /**
     * 客户类型ID 客户类型ID（数据字典new__dict）
     */
    @NotNull(message = "客户类型不能为空")
    @ApiModelProperty("类型")
    public Long customTypeId;

}

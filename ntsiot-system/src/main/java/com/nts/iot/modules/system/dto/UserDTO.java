package com.nts.iot.modules.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * @author jie
 * @date 2018-11-23
 */
@Data
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

    private String phone;

    private Boolean enabled;

    @JsonIgnore
    private String password;

    private Timestamp createTime;

    private Long jobId;

    private Date lastPasswordResetTime;

    @ApiModelProperty(hidden = true)
    private Set<RoleDTO> roles;

    @ApiModelProperty(hidden = true)
    private JobDTO job;

    private DeptDTO dept;

    private Long deptId;
}

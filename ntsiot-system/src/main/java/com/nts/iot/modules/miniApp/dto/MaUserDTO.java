package com.nts.iot.modules.miniApp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jie
 * @date 2018-11-23
 */
@Data
public class MaUserDTO implements Serializable {

    // 用户名称
    private String name;

    // 电话号
    private String phone;

    // 辖区编号
    private Long deptId;

}

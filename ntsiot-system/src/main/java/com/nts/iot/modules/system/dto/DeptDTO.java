package com.nts.iot.modules.system.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author jie
 * @date 2019-03-25
 */
@Data
public class DeptDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 別名
     */
    private String aliasName;

    @NotNull
    private Boolean enabled;

    /**
     * 上级部门
     */
    private Long pid;

    /**
     * 树形结构的编码（用于父级查询子级）
     */
    private String number;

    private String code;

    private String logo;

    private String bikeIcon;

    private String appId;

    private String secret;

    private String token;

    private String aesKey;

    /**
     * 市公安局电话
     */
    private String cityPhone;

    /**
     * 辖区电话
     */
    private String deptPhone;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    private List<DeptDTO> children;

    private Timestamp createTime;

    private Boolean checkLicense;

    public String getLabel() {
        return name;
    }
}
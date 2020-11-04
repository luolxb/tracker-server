package com.nts.iot.dto;

import lombok.Data;

@Data
public class VirtualPile {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 名称
     */
    private String name;

    /**
     * 范围
     */
    private Long scope;

    /**
     * 说明
     */
    private String remark;

    /**
     * 辖区编号
     */
    private Long jurisdiction;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 辖区名称
     */
    private String deptName;
}

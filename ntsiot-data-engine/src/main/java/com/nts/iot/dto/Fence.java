package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zhc@rnstec.com
 * @Description 限行围栏
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */

@Data
public class Fence implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 围栏坐标
     */
    private String coordinate;

    /**
     * 名称
     */
    private String name;


    /**
     * 说明
     */
    private String remark;

    /**
     * 类型
     */
    private String type;

    /**
     * 所属用户id
     */
    private Long userId;
    /**
     * 状态
     */
    private String status;

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

}

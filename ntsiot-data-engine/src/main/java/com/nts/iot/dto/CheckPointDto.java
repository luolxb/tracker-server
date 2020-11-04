/*******************************************************************************
 * @(#)CheckPointDto.java 2019-05-15
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-15 18:49
 */
@Data
public class CheckPointDto implements Serializable {

    /**
     * ID
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
     * 说明
     */
    private String remark;

    /**
     * 辖区编号
     */
    private String jurisdiction;

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
     * 任务list
     */
    private String checkPointTaskList;

    /**
     * 任务list
     */
    private List<CheckPointTaskDto> pointTaskList;

    /**
     * 辖区名称
     */
    private String deptName;

    /**
     * 删除任务id
     */
    private String taskId;


}

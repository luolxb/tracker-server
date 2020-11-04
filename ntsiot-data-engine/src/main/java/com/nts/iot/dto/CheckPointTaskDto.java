/*******************************************************************************
 * @(#)CheckPointTaskDto.java 2019-05-15
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.dto;

import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-15 18:52
 */
@Data
public class CheckPointTaskDto {

    /**
     * ID
     */
    private Long id;

    /**
     * 时间类型
     */
    private String dtType;

    /**
     * 持续时间
     */
    private String stayTime;

    /**
     * 任务名称
     */
    private String name;

    /**
     *  考核指标
     */
    private String target;

    /**
     * 说明
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 必到点编号
     */
    private Long checkPointId;

}

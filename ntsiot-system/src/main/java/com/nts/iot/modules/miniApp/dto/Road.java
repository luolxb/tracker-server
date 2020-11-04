/*******************************************************************************
 * @(#)Distance.java 2019-05-14
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     两个点之间的距离对象
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-14 10:30
 */
@Data
public class Road implements Serializable {

    /**
     * 起始点
     */
    private CollectMessage from;

    /**
     * 到达点
     */
    private CollectMessage to;

    /**
     * 距离
     */
    private Double distance;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 起始点时间
     */
    private String beginTime;

    /**
     * 结束点时间
     */
    private String endTime;

}

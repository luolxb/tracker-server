/*******************************************************************************
 * @(#)CheckPoint.java 2019-05-03
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     必到点
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-03 10:16
 */
@Data
public class CheckPoint implements Serializable {

    /**
     * 必到点的编号，跟mysql中的id一致
     */
    private String id;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 停留时间
     */
    private Long stopTime;

    /**
     * 必到点的采集坐标
     */
    private List<Trajectory> trajectories;
}

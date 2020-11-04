/*******************************************************************************
 * @(#)CollectLock.java 2019-06-21
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-06-21 12:59
 */
@Data
public class CollectLock implements Serializable {

    private String lockNo;

    private String deviceNo;

    private String simId;

    private String mac;

}

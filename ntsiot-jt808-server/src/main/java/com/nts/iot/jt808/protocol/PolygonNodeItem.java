/*******************************************************************************
 * @(#)PolygonNodeItem.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 23:10
 */
public class PolygonNodeItem {

    /**
     * 顶点纬度
     */
    private int latitude;

    public final int getLatitude() {
        return latitude;
    }

    public final void setLatitude(int value) {
        latitude = value;
    }

    /**
     * 顶点经度
     */
    private int longitude;

    public final int getLongitude() {
        return longitude;
    }

    public final void setLongitude(int value) {
        longitude = value;
    }

}

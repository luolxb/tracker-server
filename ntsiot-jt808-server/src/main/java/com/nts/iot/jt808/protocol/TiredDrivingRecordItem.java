/*******************************************************************************
 * @(#)TiredDrivingRecordItem.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 20:09
 */
public class TiredDrivingRecordItem {

    /**
     * 机动车驾驶证号码
     */
    private String driverLincenseNo;

    public final String getDriverLincenseNo() {
        return driverLincenseNo;
    }

    public final void setDriverLincenseNo(String value) {
        driverLincenseNo = value;
    }

    /**
     * 疲劳开始时间YY-MM-DD-hh-mm
     */
    private java.util.Date startTime = new java.util.Date(0);

    public final java.util.Date getStartTime() {
        return startTime;
    }

    public final void setStartTime(java.util.Date value) {
        startTime = value;
    }

    /**
     * 疲劳结束时间YY-MM-DD-hh-mm
     */
    private java.util.Date endTime = new java.util.Date(0);

    public final java.util.Date getEndTime() {
        return endTime;
    }

    public final void setEndTime(java.util.Date value) {
        endTime = value;
    }

}

/*******************************************************************************
 * @(#)EventSettingItem.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 22:53
 */
public class EventSettingItem {

    /**
     * 事件ID
     */
    private byte eventId;

    public final byte getEventId() {
        return eventId;
    }

    public final void setEventId(byte value) {
        eventId = value;
    }

    /**
     * 事件内容长度
     */
    private byte eventLength;

    public final byte getEventLength() {
        return eventLength;
    }

    public final void setEventLength(byte value) {
        eventLength = value;
    }

    /**
     * 事件内容
     */
    private String eventContent;

    public final String getEventContent() {
        return eventContent;
    }

    public final void setEventContent(String value) {
        eventContent = value;
    }

}

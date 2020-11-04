/*******************************************************************************
 * @(#)MuldimediaSearchDataItem.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 22:36
 */
public class MuldimediaSearchDataItem {

    //媒体ID

    private int multimediaId;

    public final int getMultimediaId() {
        return multimediaId;
    }

    public final void setMultimediaId(int value) {
        multimediaId = value;
    }

    /**
     * 多媒体类型
     */
    private byte multimediaType;

    public final byte getMultimediaType() {
        return multimediaType;
    }

    public final void setMultimediaType(byte value) {
        multimediaType = value;
    }

    /**
     * 通道ID
     */
    private byte channelId;

    public final byte getChannelId() {
        return channelId;
    }

    public final void setChannelId(byte value) {
        channelId = value;
    }

    /**
     * 事件项编码
     */
    private byte eventCode;

    public final byte getEventCode() {
        return eventCode;
    }

    public final void setEventCode(byte value) {
        eventCode = value;
    }

    /**
     * 位置信息汇报
     */
    private JT_0200 position;

    public final JT_0200 getPosition() {
        return position;
    }

    public final void setPosition(JT_0200 value) {
        position = value;
    }

}

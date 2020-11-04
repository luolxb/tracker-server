/*******************************************************************************
 * @(#)PointcastMessageItem.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 23:00
 */
public class PointcastMessageItem {

    /**
     * 信息类型
     */
    private byte messageType;

    public final byte getMessageType() {
        return messageType;
    }

    public final void setMessageType(byte value) {
        messageType = value;
    }

    /**
     * 信息名称长度
     */
    private short messageLength;

    public final short getMessageLength() {
        return messageLength;
    }

    public final void setMessageLength(short value) {
        messageLength = value;
    }

    /**
     * 信息名称
     */
    private String message;

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String value) {
        message = value;
    }

}

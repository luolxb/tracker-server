/*******************************************************************************
 * @(#)AnswerItem.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 22:58
 */
public class AnswerItem {

    /**
     * 答案ID
     */
    private byte answerId;

    public final byte getAnswerId() {
        return answerId;
    }

    public final void setAnswerId(byte value) {
        answerId = value;
    }

    /**
     * 答案内容长度
     */

    private short answerLength;

    public final short getAnswerLength() {
        return answerLength;
    }

    public final void setAnswerLength(short value) {
        answerLength = value;
    }

    /**
     * 答案内容
     */
    private String answerContent;

    public final String getAnswerContent() {
        return answerContent;
    }

    public final void setAnswerContent(String value) {
        answerContent = value;
    }

}

/*******************************************************************************
 * @(#)ParameterItem.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 18:42
 */
public class ParameterItem {

    /**
     * 参数ID
     */
    private int parameterId;

    public final int getParameterId() {
        return parameterId;
    }

    public final void setParameterId(int value) {
        parameterId = value;
    }

    /**
     * 参数类型，0:Byte,1:ushort,2:uint,N:string以0x00结尾
     */
    private byte parameterLength;

    public final byte getParameterLength() {
        return parameterLength;
    }

    public final void setParameterLength(byte value) {
        parameterLength = value;
    }

    /**
     * 参数值
     */
    private Object parameterValue;

    public final Object getParameterValue() {
        return parameterValue;
    }

    public final void setParameterValue(Object value) {
        parameterValue = value;
    }

}

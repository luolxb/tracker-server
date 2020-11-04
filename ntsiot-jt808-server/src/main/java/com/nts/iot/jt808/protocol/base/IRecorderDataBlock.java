/*******************************************************************************
 * @(#)IRecorderDataBlock.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.base;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 16:14
 */
public interface IRecorderDataBlock {

    byte getCommandWord();

    short getDataLength();

    byte[] writeToBytes();

    void readFromBytes(byte[] bytes);

    /**
     * 重写toString方法
     * @return
     */
    @Override
    String toString();

}

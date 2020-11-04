/*******************************************************************************
 * @(#)IMessageBody.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.base;

/**
 * <p>
 *     808消息抽象接口
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 16:13
 */
public interface IMessageBody {

    /**
     * 数据转成字节流
     * @return
     */
    byte[] writeToBytes();
    /**
     * 读取字节流，解析出数据
     * @param messageBodyBytes
     */
    void readFromBytes(byte[] messageBodyBytes);

}

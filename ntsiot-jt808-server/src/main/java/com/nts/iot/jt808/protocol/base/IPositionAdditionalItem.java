/*******************************************************************************
 * @(#)IPositionAdditionalItem.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.base;

/**
 * <p>
 *     定位包的附加协议抽象接口
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 16:13
 */
public interface IPositionAdditionalItem {

    /**
     * 附加信息Id
     * @return
     */
    int getAdditionalId();
    /**
     * 附加信息长度
     * @return
     */
    byte getAdditionalLength();
    /**
     * 附加信息转字节流
     * @return
     */
    byte[] writeToBytes();
    /**
     * 读取字节流，解析出附加信息数据
     * @param bytes
     */
    void readFromBytes(byte[] bytes);

}

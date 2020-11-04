/*******************************************************************************
 * @(#)PostitionAdditional_RouteDriveTimeAlarmAdditional.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * <p>
 *     行驶时间过长或过短报警附加信息
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:08
 */
public class PostitionAdditional_RouteDriveTimeAlarmAdditional implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x13;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x07;
    }

    /**
     * 路段ID
     */
    private int routeId;

    public final int getRouteId() {
        return routeId;
    }

    public final void setRouteId(int value) {
        routeId = value;
    }

    /**
     * 行驶时间,单位为秒(s)
     */
    private short driveTime;

    public final short getDriveTime() {
        return driveTime;
    }

    public final void setDriveTime(short value) {
        driveTime = value;
    }

    /**
     * 结果,0: 不足,1:过长
     */
    private byte result;

    public final byte getResult() {
        return result;
    }

    public final void setResult(byte value) {
        result = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getRouteId());
        buff.put(getDriveTime());
        buff.put(getResult());
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        try {
            setRouteId(buff.getInt());
            setDriveTime(buff.getShort());
            setResult(buff.get());
        } finally {
            buff.release();
        }
    }

}

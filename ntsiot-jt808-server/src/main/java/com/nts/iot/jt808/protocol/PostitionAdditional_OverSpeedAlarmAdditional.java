/*******************************************************************************
 * @(#)PostitionAdditional_OverSpeedAlarmAdditional.java 2019-04-28
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
 * @version ntsiot 1.0 $ 2019-04-28 19:12
 */
public class PostitionAdditional_OverSpeedAlarmAdditional implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x11;
    }

    @Override
    public final byte getAdditionalLength() {
        if (getPositionType() == 0) {
            return 0x01;
        } else {
            return 0x05;
        }
    }

    /**
     * 位置类型
     */
    private byte positionType;

    public final byte getPositionType() {
        return positionType;
    }

    public final void setPositionType(byte value) {
        positionType = value;
    }

    /**
     * 区域或路段ID
     */
    private int areaId;

    public final int getAreaId() {
        return areaId;
    }

    public final void setAreaId(int value) {
        areaId = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getPositionType());
        if (getPositionType() != 0) {
            buff.put(getAreaId());
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setPositionType(buff.get());
        if (getPositionType() != 0) {
            setAreaId(buff.getInt());
        }
    }
}

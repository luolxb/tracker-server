/*******************************************************************************
 * @(#)PostitionAdditional_InOutAreaAlarmAdditional.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * <p>
 *     进出区域报警附加信息
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:04
 */
public class PostitionAdditional_InOutAreaAlarmAdditional implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x12;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x06;
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

    /**
     * 方向,0:进,1:出
     */
    private byte direction;

    public final byte getDirection() {
        return direction;
    }

    public final void setDirection(byte value) {
        direction = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getPositionType());
        buff.put(getAreaId());
        buff.put(getDirection());
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        try {
            setPositionType(buff.get());
            setAreaId(buff.getInt());
            setDirection(buff.get());
        } finally {
            buff.release();
        }
    }
}

/*******************************************************************************
 * @(#)PostitionAdditional_AlarmEventId.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:20
 */
public class PostitionAdditional_AlarmEventId implements IPositionAdditionalItem {

    @Override
    public final int getAdditionalId() {
        return 0x04;
    }

    @Override
    public final byte getAdditionalLength() {
        return 0x02;
    }

    /**
     * 需要人工确认报警事件的ID，WORD，从1开始计数
     */

    private short alarmEventId;

    public final short getAlarmEventId() {
        return alarmEventId;
    }

    public final void setAlarmEventId(short value) {
        alarmEventId = value;
    }

    @Override
    public final byte[] writeToBytes() {
        return BitConverter.getBytes(getAlarmEventId());
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setAlarmEventId((short) BitConverter.toUInt16(bytes, 0));
    }
}

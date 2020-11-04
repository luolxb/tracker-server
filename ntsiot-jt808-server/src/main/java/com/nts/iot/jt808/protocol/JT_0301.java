package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 事件报告
 */
public class JT_0301 implements IMessageBody {

    /**
     * 事件ID
     */
    private byte eventId;

    public final byte getEventId() {
        return eventId;
    }

    public final void setEventId(byte value) {
        eventId = value;
    }

    @Override
    public final byte[] writeToBytes() {
        return new byte[]{getEventId()};
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setEventId(bytes[0]);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("事件ID：%1$s", getEventId()));
        return sBuilder.toString();
    }
}
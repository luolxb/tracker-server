package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 临时位置跟踪控制
 */
public class JT_8202 implements IMessageBody {

    /**
     * 时间间隔,单位为秒（s），0则停止跟踪。停止跟踪无需带后继字段
     */
    private short timeInterval;

    public final short getTimeInterval() {
        return timeInterval;
    }

    public final void setTimeInterval(short value) {
        timeInterval = value;
    }

    /**
     * 位置跟踪有效期,单位为秒（s），终端在接收到位置跟踪控制消息后，在有效期截止时间之前，依据消息中的时间间隔发送位置汇报
     */
    private int trackExpire;

    public final int getTrackExpire() {
        return trackExpire;
    }

    public final void setTrackExpire(int value) {
        trackExpire = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getTimeInterval());
        buff.put(getTrackExpire());
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setTimeInterval(buff.getShort());
        setTrackExpire(buff.getInt());
    }

    @Override
    public String toString() {
        return String.format("时间间隔:%1$s秒,有效期：%2$s秒", getTimeInterval(), getTrackExpire());
    }
}
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 存储多媒体数据检索
 */
public class JT_8802 implements IMessageBody {

    /**
     * 多媒体类型
     */
    private byte multimediaType;

    public final byte getMultimediaType() {
        return multimediaType;
    }

    public final void setMultimediaType(byte value) {
        multimediaType = value;
    }

    /**
     * 通道ID
     */
    private byte channelId;

    public final byte getChannelId() {
        return channelId;
    }

    public final void setChannelId(byte value) {
        channelId = value;
    }

    /**
     * 事件项编码
     */
    private byte eventCode;

    public final byte getEventCode() {
        return eventCode;
    }

    public final void setEventCode(byte value) {
        eventCode = value;
    }

    /**
     * 起始时间
     */
    private java.util.Date startTime = new java.util.Date(0);

    public final java.util.Date getStartTime() {
        return startTime;
    }

    public final void setStartTime(java.util.Date value) {
        startTime = value;
    }

    /**
     * 结束时间
     */
    private java.util.Date endTime = new java.util.Date(0);

    public final java.util.Date getEndTime() {
        return endTime;
    }

    public final void setEndTime(java.util.Date value) {
        endTime = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[15];
        bytes[0] = getMultimediaType();
        bytes[1] = getChannelId();
        bytes[2] = getEventCode();
        byte[] date1 = BitConverter.getBytes(getStartTime());
        byte[] date2 = BitConverter.getBytes(getEndTime());
        System.arraycopy(date1, 0, bytes, 3, 6);
        System.arraycopy(date2, 0, bytes, 9, 6);
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setMultimediaType(bytes[0]);
        setChannelId(bytes[1]);
        setEventCode(bytes[2]);
        setStartTime(new java.util.Date(java.util.Date.parse("20" + String.format("%02X", bytes[3]) + "-" + String.format("%02X", bytes[4]) + "-" + String.format("%02X", bytes[5]) + " " + String.format("%02X", bytes[6]) + ":" + String.format("%02X", bytes[7]) + ":" + String.format("%02X", bytes[8]))));
        setEndTime(new java.util.Date(java.util.Date.parse("20" + String.format("%02X", bytes[9]) + "-" + String.format("%02X", bytes[10]) + "-" + String.format("%02X", bytes[11]) + " " + String.format("%02X", bytes[12]) + ":" + String.format("%02X", bytes[13]) + ":" + String.format("%02X", bytes[14]))));

    }
}
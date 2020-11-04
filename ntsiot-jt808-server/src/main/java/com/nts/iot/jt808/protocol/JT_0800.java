package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 多媒体事件信息上传
 */
public class JT_0800 implements IMessageBody {

    /**
     * 多媒体数据ID
     */
    private int multimediaDataId;

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
     * 多媒体格式编码
     */
    private byte multidediaCodeFormat;

    public final byte getMultidediaCodeFormat() {
        return multidediaCodeFormat;
    }

    public final void setMultidediaCodeFormat(byte value) {
        multidediaCodeFormat = value;
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
     * 通道ID
     */
    private byte channelId;

    public final byte getChannelId() {
        return channelId;
    }

    public final void setChannelId(byte value) {
        channelId = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (getMultimediaDataId() >> 24);
        bytes[1] = (byte) (getMultimediaDataId() >> 16);
        bytes[2] = (byte) (getMultimediaDataId() >> 8);
        bytes[3] = (byte) (getMultimediaDataId());
        bytes[4] = getMultimediaType();
        bytes[5] = getMultidediaCodeFormat();
        bytes[6] = getEventCode();
        bytes[7] = getChannelId();
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setMultimediaDataId(((bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3]));
        setMultimediaType(bytes[4]);
        setMultidediaCodeFormat(bytes[5]);
        setEventCode(bytes[6]);
        setChannelId(bytes[7]);
    }

    @Override
    public String toString() {
        byte mediaType = getMultimediaType();
        String strMediaType = "未知类型";
        if (mediaType == 0) {
            strMediaType = "图像";
        } else if (mediaType == 1) {
            strMediaType = "音频";
        } else if (mediaType == 2) {
            strMediaType = "视频";
        }
        byte format = getMultidediaCodeFormat();
        String strFormat = "其他格式";
        if (format == 0) {
            strFormat = "JPEG";
        } else if (format == 1) {
            strFormat = "TIF";
        } else if (format == 2) {
            strFormat = "MP3";
        } else if (format == 3) {
            strFormat = "WAV";
        } else if (format == 4) {
            strFormat = "WMV";
        }
        byte code = getEventCode();
        String strCode = "其他事件编码";
        if (code == 0) {
            strCode = "平台下发指令";
        } else if (code == 1) {
            strCode = "定时动作";
        } else if (code == 2) {
            strCode = "抢劫报警触发";
        } else if (code == 3) {
            strCode = "碰撞侧翻报警触发";
        }
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("数据ID：%1$s,多媒体类型：%2$s,编码格式：%3$s,事件编码：%4$s,通道ID：%5$s", getMultimediaDataId(), strMediaType, strFormat, strCode, getChannelId()));
        return sBuilder.toString();
    }

    public final int getMultimediaDataId() {
        return multimediaDataId;
    }

    public final void setMultimediaDataId(int value) {
        multimediaDataId = value;
    }
}
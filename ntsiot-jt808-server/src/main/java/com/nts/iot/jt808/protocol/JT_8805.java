package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 单条存储多媒体数据检索上传命令
 */
public class JT_8805 implements IMessageBody {

    /**
     * 多媒体ID
     */
    private int multimediaId;

    public final int getMultimediaId() {
        return multimediaId;
    }

    public final void setMultimediaId(int value) {
        multimediaId = value;
    }

    /**
     * 删除标志
     */
    private byte deleteFlag;

    public final byte getDeleteFlag() {
        return deleteFlag;
    }

    public final void setDeleteFlag(byte value) {
        deleteFlag = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[5];
        bytes[0] = (byte) (getMultimediaId() >> 24);
        bytes[1] = (byte) (getMultimediaId() >> 16);
        bytes[2] = (byte) (getMultimediaId() >> 8);
        bytes[3] = (byte) (getMultimediaId());
        bytes[4] = getDeleteFlag();
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setMultimediaId((int) ((bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3]));
        setDeleteFlag(bytes[4]);
    }
}
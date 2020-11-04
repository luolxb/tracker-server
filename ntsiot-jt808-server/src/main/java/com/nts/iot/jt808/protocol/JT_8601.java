package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 删除圆形区域
 */
public class JT_8601 implements IMessageBody {

    /**
     * 区域数,本条消息中包含的区域数，不超过125个，多于125个建议用多条消息，0为删除所有圆形区域
     */
    private byte circleAreasCount;

    public final byte getCircleAreasCount() {
        return circleAreasCount;
    }

    public final void setCircleAreasCount(byte value) {
        circleAreasCount = value;
    }

    /**
     * 要删除圆形区域ID列表
     */
    private java.util.ArrayList<Integer> circleAreaIDs;

    public final java.util.ArrayList<Integer> getCircleAreaIDs() {
        return circleAreaIDs;
    }

    public final void setCircleAreaIDs(java.util.ArrayList<Integer> value) {
        circleAreaIDs = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        bytes.put(getCircleAreasCount());
        if (getCircleAreasCount() > 0 && getCircleAreaIDs() != null && getCircleAreaIDs().size() > 0) {
            for (int areaId : getCircleAreaIDs()) {
                bytes.put((byte) (areaId >> 24));
                bytes.put((byte) (areaId >> 16));
                bytes.put((byte) (areaId >> 8));
                bytes.put((byte) areaId);
            }
        }
        return bytes.array();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setCircleAreasCount(bytes[0]);
        setCircleAreaIDs(new java.util.ArrayList<>(getCircleAreasCount()));
        int pos = 1;
        while (pos < bytes.length) {
            int areaId = ((bytes[pos] << 24) + (bytes[pos + 1] << 16) + (bytes[pos + 2]) << 8 + bytes[pos + 3]);
            getCircleAreaIDs().add(areaId);
            pos += 4;
        }
    }
}
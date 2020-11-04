package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 删除多边形区域
 */
public class JT_8605 implements IMessageBody {

    /**
     * 区域数,本条消息中包含的区域数，不超过125个，多于125个建议用多条消息，0为删除所有多边形区域
     */
    private byte polygonAreasCount;

    public final byte getPolygonAreasCount() {
        return polygonAreasCount;
    }

    public final void setPolygonAreasCount(byte value) {
        polygonAreasCount = value;
    }

    /**
     * 要删除多边形区域ID列表
     */
    private java.util.ArrayList<Integer> polygonAreaIDs;

    public final java.util.ArrayList<Integer> getPolygonAreaIDs() {
        return polygonAreaIDs;
    }

    public final void setPolygonAreaIDs(java.util.ArrayList<Integer> value) {
        polygonAreaIDs = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        bytes.put(getPolygonAreasCount());
        if (getPolygonAreasCount() > 0 && getPolygonAreaIDs() != null && getPolygonAreaIDs().size() > 0) {
            for (int areaId : getPolygonAreaIDs()) {
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
        setPolygonAreasCount(bytes[0]);
        setPolygonAreaIDs(new java.util.ArrayList<>(getPolygonAreasCount()));
        int pos = 1;
        while (pos < bytes.length) {
            int areaId = (int) ((bytes[pos] << 24) + (bytes[pos + 1] << 16) + (bytes[pos + 2]) << 8 + bytes[pos + 3]);
            getPolygonAreaIDs().add(areaId);
            pos += 4;
        }
    }
}
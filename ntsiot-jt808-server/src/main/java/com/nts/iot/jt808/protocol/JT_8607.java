package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 删除路线
 */
public class JT_8607 implements IMessageBody {

    /**
     * 路线数,本条消息中包含的路线数，不超过125个，多于125个建议用多条消息，0为删除所有路线
     */
    private byte routesCount;

    public final byte getRoutesCount() {
        return routesCount;
    }

    public final void setRoutesCount(byte value) {
        routesCount = value;
    }

    /**
     * 要路线ID列表
     */
    private java.util.ArrayList<Integer> routeIDs;

    public final java.util.ArrayList<Integer> getRouteIDs() {
        return routeIDs;
    }

    public final void setRouteIDs(java.util.ArrayList<Integer> value) {
        routeIDs = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        bytes.put(getRoutesCount());
        if (getRoutesCount() > 0 && getRouteIDs() != null && getRouteIDs().size() > 0) {

            for (int areaId : getRouteIDs()) {
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
        setRoutesCount(bytes[0]);
        setRouteIDs(new java.util.ArrayList<>(getRoutesCount()));
        int pos = 1;
        while (pos < bytes.length) {
            int areaId = (int) ((bytes[pos] << 24) + (bytes[pos + 1] << 16) + (bytes[pos + 2]) << 8 + bytes[pos + 3]);
            getRouteIDs().add(areaId);
            pos += 4;
        }
    }
}
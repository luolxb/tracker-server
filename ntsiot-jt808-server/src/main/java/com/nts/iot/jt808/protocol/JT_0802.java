package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import com.nts.iot.jt808.protocol.constant.JT808Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储多媒体数据检索应带
 */
public class JT_0802 implements IMessageBody {

    /**
     * 应答流水号
     */
    private short responseMessageSerialNo;

    public final short getResponseMessageSerialNo() {
        return responseMessageSerialNo;
    }

    public final void setResponseMessageSerialNo(short value) {
        responseMessageSerialNo = value;
    }

    /**
     * 多媒体数据总项数
     */

    private short multimediaSearchDataItemsCount;

    public final short getMultimediaSearchDataItemsCount() {
        return multimediaSearchDataItemsCount;
    }

    public final void setMultimediaSearchDataItemsCount(short value) {
        multimediaSearchDataItemsCount = value;
    }

    /**
     * 检索项
     */
    private List<MuldimediaSearchDataItem> dataItems;

    public final List<MuldimediaSearchDataItem> getDataItems() {
        return dataItems;
    }

    public final void setDataItems(ArrayList<MuldimediaSearchDataItem> value) {
        dataItems = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put((byte) (getResponseMessageSerialNo() >> 8));
        buff.put((byte) getResponseMessageSerialNo());
        buff.put((byte) (getMultimediaSearchDataItemsCount() >> 8));
        buff.put((byte) (getMultimediaSearchDataItemsCount()));
        for (MuldimediaSearchDataItem item : getDataItems()) {
            buff.put((byte) (item.getMultimediaId() >> 24));
            buff.put((byte) (item.getMultimediaId() >> 16));
            buff.put((byte) (item.getMultimediaId() >> 8));
            buff.put((byte) (item.getMultimediaId()));
            buff.put(item.getMultimediaType());
            buff.put(item.getChannelId());
            buff.put(item.getEventCode());
            buff.put(item.getPosition().writeToBytes());
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        short sn = (short) BitConverter.toUInt16(bytes, 0);
        setResponseMessageSerialNo(sn);
        short count = (short) BitConverter.toUInt16(bytes, 2);
        setMultimediaSearchDataItemsCount((short) ((bytes[2] << 8) + bytes[3]));
        setDataItems(new java.util.ArrayList<>());
        int pos = 4;
        while (pos < bytes.length) {
            MuldimediaSearchDataItem item = new MuldimediaSearchDataItem();
            // 终端发送的协议是否是补充协议
            if (JT808Constant.isNew808Protocol()) {
                item.setMultimediaId((int) ((bytes[pos + 0] << 24)
                        + (bytes[pos + 1] << 16) + (bytes[pos + 2] << 8) + bytes[pos + 3]));

                item.setMultimediaType(bytes[pos + 4]);
                item.setChannelId(bytes[pos + 5]);
                item.setEventCode(bytes[pos + 6]);

                byte[] positionBytes = new byte[28];
                System.arraycopy(bytes, pos + 7, positionBytes, 0, 28);
                item.setPosition(new JT_0200());
                item.getPosition().readFromBytes(positionBytes);
                // 每个媒体检索项35个字节
                pos += 35;
            } else {
                item.setMultimediaType(bytes[pos + 0]);
                item.setChannelId(bytes[pos + 1]);
                item.setEventCode(bytes[pos + 2]);

                byte[] positionBytes = new byte[28];
                System.arraycopy(bytes, pos + 3, positionBytes, 0, 28);
                item.setPosition(new JT_0200());
                item.getPosition().readFromBytes(positionBytes);
                // 每个媒体检索项31个字节
                pos += 31;
            }
            getDataItems().add(item);

        }
    }
}
package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 电子运单上报
 */
public class JT_0701 implements IMessageBody {

    /**
     * 电子运单上报
     */
    private int electronicFreightLength;

    /**
     * 电子运单内容
     */
    private String electronicFreightContent;

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        byte[] contentBytes = BitConverter.getBytes(getElectronicFreightContent());
        setElectronicFreightLength((contentBytes.length));
        bytes.put((byte) (getElectronicFreightLength() >> 24));
        bytes.put((byte) (getElectronicFreightLength() >> 16));
        bytes.put((byte) (getElectronicFreightLength() >> 8));
        bytes.put((byte) getElectronicFreightLength());
        bytes.put(contentBytes);
        return bytes.array();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setElectronicFreightLength((int) ((bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3]));
        setElectronicFreightContent(BitConverter.getString(bytes, 4, (int) getElectronicFreightLength()));
    }

    @Override
    public String toString() {
        return String.format("电子运单内容：%1$s", getElectronicFreightContent());
    }

    public final int getElectronicFreightLength() {
        return electronicFreightLength;
    }

    public final void setElectronicFreightLength(int value) {
        electronicFreightLength = value;
    }

    public final String getElectronicFreightContent() {
        return electronicFreightContent;
    }

    public final void setElectronicFreightContent(String value) {
        electronicFreightContent = value;
    }
}
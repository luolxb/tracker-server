package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;
import com.nts.iot.jt808.protocol.jt2012.factory.RecorderDataBlockFactory_2012;

/**
 * 记录仪
 *
 * @author admin
 */
public class JT_0710 implements IMessageBody {

    private short privateResponseMessageSerialNo;

    public final short getResponseMessageSerialNo() {
        return privateResponseMessageSerialNo;
    }

    public final void setResponseMessageSerialNo(short value) {
        privateResponseMessageSerialNo = value;
    }

    private byte privateCommandWord;

    public final byte getCommandWord() {
        return privateCommandWord;
    }

    public final void setCommandWord(byte value) {
        privateCommandWord = value;
    }

    private byte[] _data_1905_2012;

    public final byte[] getData_1905_2012() {
        return _data_1905_2012;
    }

    public final void setData_1905_2012(byte[] value) {
        _data_1905_2012 = value;
    }

    private IRecorderDataBlock_2012 privateData;

    public final IRecorderDataBlock_2012 getData() {
        return privateData;
    }

    public final void setData(IRecorderDataBlock_2012 value) {
        privateData = value;
    }

    private short _totalPacketsCount = 1;

    public final short getTotalPacketsCount() {
        return _totalPacketsCount;
    }

    private short _packetNo = 1;

    public final short getPacketNo() {
        return _packetNo;
    }

    public JT_0710() {
        _totalPacketsCount = 1;
        _packetNo = 1;
    }

    public JT_0710(short totalPacketsCount, short packetNo) {
        _totalPacketsCount = totalPacketsCount;
        _packetNo = packetNo;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytesHead = new byte[3];
        bytesHead[0] = ((byte) (getResponseMessageSerialNo() >> 8));
        bytesHead[1] = ((byte) getResponseMessageSerialNo());
        bytesHead[2] = getCommandWord();
        byte[] bytes = bytesHead;
        if (getCommandWord() == 0x82 || getCommandWord() == 0x83 || getCommandWord() == 0x84 || getCommandWord() == 0xC2 || getCommandWord() == 0xC3 || getCommandWord() == 0xC4 || getCommandWord() == 0x08 || getCommandWord() == 0x09 || getCommandWord() == 0x10 || getCommandWord() == 0x11 || getCommandWord() == 0x12 || getCommandWord() == 0x13 || getCommandWord() == 0x14 || getCommandWord() == 0x15) {
            byte[] temp = getData().writeToBytes();
            bytes = new byte[3 + temp.length];
            System.arraycopy(bytesHead, 0, bytes, 0, 3);
            System.arraycopy(temp, 0, bytes, 3, temp.length);
        }
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setResponseMessageSerialNo((short) ((bytes[0] << 8) + bytes[1]));
        setCommandWord(bytes[2]);
        byte[] blockBytes = new byte[bytes.length - 3];
        System.arraycopy(bytes, 3, blockBytes, 0, bytes.length - 3);
        setData(RecorderDataBlockFactory_2012.create(getCommandWord()));
        getData().readFromBytes(blockBytes);

    }
}

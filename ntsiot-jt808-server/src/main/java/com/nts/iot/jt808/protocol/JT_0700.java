package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.base.IRecorderDataBlock;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import com.nts.iot.jt808.protocol.message.factory.RecorderDataBlockFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 行驶记录数据上传
 */
@Slf4j
public class JT_0700 implements IMessageBody {

    /**
     * 命令字的数据用于转发
     */
    private byte[] cmdData;
    private short responseMessageSerialNo;

    public final short getResponseMessageSerialNo() {
        return responseMessageSerialNo;
    }

    public final void setResponseMessageSerialNo(short value) {
        responseMessageSerialNo = value;
    }

    private byte commandWord;

    public final byte getCommandWord() {
        return commandWord;
    }

    public final void setCommandWord(byte value) {
        commandWord = value;
    }

    private IRecorderDataBlock data;

    public final IRecorderDataBlock getData() {
        return data;
    }

    public final void setData(IRecorderDataBlock value) {
        data = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        bytes.put((byte) (getResponseMessageSerialNo() >> 8));
        bytes.put((byte) getResponseMessageSerialNo());
        bytes.put(getCommandWord());
        bytes.put(getData().writeToBytes());
        return bytes.array();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        short t = (short) BitConverter.toUInt16(bytes, 0);
        this.responseMessageSerialNo = t;
        this.commandWord = (bytes[2]);
        this.cmdData = new byte[bytes.length - 3];
        //将命令字的数据填充到缓冲中，便于转发
        System.arraycopy(bytes, 3, cmdData, 0, cmdData.length);
        byte h1 = bytes[3];
        byte h2 = bytes[4];
        int recordWord = bytes[5];
        int dataLength = (int) ((bytes[6] << 8) + bytes[7]);
        int resved = bytes[8];
        byte[] blockBytes = new byte[bytes.length - 9];
        System.arraycopy(bytes, 9, blockBytes, 0, blockBytes.length);
        this.data = RecorderDataBlockFactory.create(getCommandWord());
        if (data != null) {
            data.readFromBytes(blockBytes);
        } else {
            log.error("记录仪返回非法的命令字：" + this.getCommandWord());
        }
    }

    public byte[] getCmdData() {
        return cmdData;
    }

    public void setCmdData(byte[] cmdData) {
        this.cmdData = cmdData;
    }
}
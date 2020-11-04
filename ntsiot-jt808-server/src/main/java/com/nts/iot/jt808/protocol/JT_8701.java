package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.base.IRecorderDataBlock;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import com.nts.iot.jt808.protocol.message.factory.RecorderDataBlockFactory;

/**
 * 行驶记录参数下传命令
 */
public class JT_8701 implements IMessageBody {
    /**
     * 命令字
     */
    private byte commandWord;

    public final byte getCommandWord() {
        return commandWord;
    }

    public final void setCommandWord(byte value) {
        commandWord = value;
    }

    /**
     * 数据块
     */
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
        bytes.put(getCommandWord());
        bytes.put(getData().writeToBytes());
        return bytes.array();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setCommandWord(bytes[0]);
        byte[] blockBytes = new byte[bytes.length - 1];
        System.arraycopy(bytes, 1, blockBytes, 0, bytes.length - 1);
        setData(RecorderDataBlockFactory.create(getCommandWord()));
        getData().readFromBytes(blockBytes);
    }
}
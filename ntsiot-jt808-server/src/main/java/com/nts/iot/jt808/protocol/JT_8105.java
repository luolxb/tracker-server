package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 终端控制
 */
public class JT_8105 implements IMessageBody {

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
     * 命令参数项
     */
    private String commandParameters;

    public final String getCommandParameters() {
        return commandParameters;
    }

    public final void setCommandParameters(String value) {
        commandParameters = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getCommandWord());
        if (this.commandParameters != null && this.commandParameters.length() > 0) {
            buff.put(getCommandParameters().getBytes());
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setCommandWord(buff.get());
        setCommandParameters(buff.getString());
    }

    @Override
    public String toString() {
        return String.format("命令字：%1$s,命令参数:%2$s", getCommandWord(), getCommandParameters());
    }
}
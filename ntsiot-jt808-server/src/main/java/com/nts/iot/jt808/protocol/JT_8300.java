package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

import java.io.UnsupportedEncodingException;

/**
 * 文本信息下发(0x8300)
 *
 * @author DELL
 */

public class JT_8300 implements IMessageBody {

    /**
     * 标志
     */
    private byte privateFlag;

    public final byte getFlag() {
        return privateFlag;
    }

    public final void setFlag(byte value) {
        privateFlag = value;
    }

    /**
     * 文本信息
     */
    private String text;

    public final String getText() {
        return text;
    }

    public final void setText(String value) {
        text = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getFlag());
        try {
            buff.put(getText().getBytes("gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setFlag(buff.get());
        setText(buff.getString());
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("标志：%1$s,文本：%2$s", getFlag(), getText()));
        return sBuilder.toString();
    }
}

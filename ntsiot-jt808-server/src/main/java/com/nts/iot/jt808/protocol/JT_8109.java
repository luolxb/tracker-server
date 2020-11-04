package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import lombok.Data;

/**
 * 时间同步应答
 */
@Data
public class JT_8109 implements IMessageBody {

    /**
     * 时间字符串
     */
    private String timeString;

    /**
     * 数据转成字节流
     *
     * @return
     */
    @Override
    public byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getTimeString());
        return buff.toByteArrayAndRelease();
    }

    /**
     * 读取字节流，解析出数据
     *
     * @param messageBodyBytes
     */
    @Override
    public void readFromBytes(byte[] messageBodyBytes) {

    }
}

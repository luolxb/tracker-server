package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 时间同步
 */
public class JT_0109 implements IMessageBody {
    /**
     * 数据转成字节流
     *
     * @return
     */
    @Override
    public byte[] writeToBytes() {
        return new byte[0];
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

package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 位置信息查询,位置信息查询消息体为空
 */
public class JT_8201 implements IMessageBody {

    @Override
    public final byte[] writeToBytes() {
        return null;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {

    }
}
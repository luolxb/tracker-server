package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import lombok.Data;

/**
 * @program: ntsiot-jt808-server
 * @description: 升级成功通知
 * @author: wongshan
 * @create: 2019-08-06 10:57
 **/
@Data
public class JT_0108 implements IMessageBody {
    private byte type;
    private byte upgrade_result;
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
        ByteBuffer buff = new ByteBuffer(messageBodyBytes);
        setType(buff.get());
        setUpgrade_result(buff.get());
    }
}

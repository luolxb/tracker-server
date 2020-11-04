package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 终端请求地址应答
 */
public class JT_B004 implements IMessageBody {

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;


    @Override
    public byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(address);
        return buff.toByteArrayAndRelease();
    }

    @Override
    public void readFromBytes(byte[] messageBodyBytes) {
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("地址：%1$s", this.address));
        return sBuilder.toString();
    }


}

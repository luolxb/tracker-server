package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import lombok.Data;

/**
 * @program: ntsiot-jt808-server
 * @description: OTA升级
 * @author: wongshan
 * @create: 2019-07-31 15:56
 **/
@Data
public class JT_8108 implements IMessageBody {
    private byte type;
    private String manufactureId;
    private int port;
    private byte sw_lenth;
    private String sw_version;
    private int url_lenth;
    private String url;
    /**
     * 数据转成字节流
     *
     * @return
     */
    @Override
    public byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        if (url != null)
        {
            // todo 终端类型
            buff.put(type);
            // todo 制造商id
            buff.put(manufactureId);
            // todo 端口 9501
            buff.put(port);
            //todo 版本号长度
            buff.put(sw_lenth);
            // todo 版本号
            buff.put(sw_version);
            // todo url长度
            buff.put(url_lenth);
            // todo url
            buff.put(url);
        }
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

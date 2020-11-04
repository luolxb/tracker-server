package com.nts.iot.jt808.protocol;


import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 终端请求地址
 */
public class JT_B005 implements IMessageBody {
    private int latitude;

    private int longitude;

    /**
     * 时间,YY-MM-DD-hh-mm-ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）
     */
    private String time;

    @Override
    public byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getLatitude());
        buff.put(getLongitude());
        buff.put(Byte.parseByte(this.time.substring(2, 4), 16));
        buff.put(Byte.parseByte(this.time.substring(5, 7), 16));
        buff.put(Byte.parseByte(this.time.substring(8, 10), 16));
        buff.put(Byte.parseByte(this.time.substring(11, 13), 16));
        buff.put(Byte.parseByte(this.time.substring(14, 16), 16));
        buff.put(Byte.parseByte(this.time.substring(17, 19), 16));
        return buff.toByteArrayAndRelease();
    }

    @Override
    public void readFromBytes(byte[] messageBodyBytes) {
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("纬度：%1$s,经度：%2$s,时间：%3$s", getLongitude(), getLatitude(), this.time));
        return sBuilder.toString();
    }

    /**
     * 经度,以度为单位的经度值乘以10的6次方，精确到百万 分之一度
     */
    public final int getLongitude() {
        return longitude;
    }

    public final void setLongitude(int value) {
        longitude = value;
    }

    public final int getLatitude() {
        return latitude;
    }

    public final void setLatitude(int value) {
        latitude = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

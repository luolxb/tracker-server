package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import lombok.extern.slf4j.Slf4j;

/**
 * 终端请求地址
 */
@Slf4j
public class JT_A004 implements IMessageBody {

    private int latitude;

    private int longitude;

    /**
     * 移动国家码
     */
    private int MCC;

    /**
     * 移动网络码
     */
    private int MNC;
    /**
     * 位置区号码
     */
    private long LAC;

    /**
     * 小区号
     */
    private long CID;

    /**
     * 0：中文，1：英文
     */
    private byte language;

    /**
     * 0：不回复，1：回复
     */
    private byte ack;

    /**
     * 0：地址信息不含链接，1：含链接
     */
    private byte address;

    @Override
    public byte[] writeToBytes() {
        return new byte[0];
    }

    @Override
    public void readFromBytes(byte[] messageBodyBytes) {
        ByteBuffer buff = new ByteBuffer(messageBodyBytes);
        this.latitude = buff.getInt();
        this.longitude = buff.getInt();
        this.MCC = buff.getUnsignedShort();
        this.MNC = buff.getUnsignedShort();
        this.LAC = buff.getUnsignedInt();
        this.CID = buff.getUnsignedInt();
        this.language = buff.get();
        this.ack = buff.get();
        this.address = buff.get();
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format(
                "纬度：%1$s,经度：%2$s,移动国家码：%3$s,移动网络码：%4$s,位置区号码：%5$s,小区号：%6$s,语言：%7$s,回复：%8$s,地址链接：%9$s",
                getLongitude(), getLatitude(), this.MCC, this.MNC, this.LAC, this.CID, this.language, this.ack, this.address));
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

    public int getMCC() {
        return MCC;
    }

    public void setMCC(int MCC) {
        this.MCC = MCC;
    }

    public int getMNC() {
        return MNC;
    }

    public void setMNC(int MNC) {
        this.MNC = MNC;
    }

    public long getLAC() {
        return LAC;
    }

    public void setLAC(long LAC) {
        this.LAC = LAC;
    }

    public long getCID() {
        return CID;
    }

    public void setCID(long CID) {
        this.CID = CID;
    }

    public byte getLanguage() {
        return language;
    }

    public void setLanguage(byte language) {
        this.language = language;
    }

    public byte getAck() {
        return ack;
    }

    public void setAck(byte ack) {
        this.ack = ack;
    }

    public byte getAddress() {
        return address;
    }

    public void setAddress(byte address) {
        this.address = address;
    }
}

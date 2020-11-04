package com.nts.iot.jt808.protocol;


import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 终端 AGPS 请求
 */
public class JT_A005 implements IMessageBody {
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

    @Override
    public byte[] writeToBytes() {
        return new byte[0];
    }

    @Override
    public void readFromBytes(byte[] messageBodyBytes) {
        ByteBuffer buff = new ByteBuffer(messageBodyBytes);
        this.MCC = buff.getUnsignedShort();
        this.MNC = buff.getUnsignedShort();
        this.LAC = buff.getUnsignedInt();
        this.CID = buff.getUnsignedInt();
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(String.format("移动国家码：%1$s,移动网络码：%2$s,位置区号码：%3$s,小区号：%4$s", this.MCC, this.MNC, this.LAC, this.CID));
        return sBuilder.toString();
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

}

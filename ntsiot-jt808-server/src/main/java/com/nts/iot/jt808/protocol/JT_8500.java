package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 车辆控制
 */
public class JT_8500 implements IMessageBody {
    /**
     * 控制标志
     */
    private byte privateFlag;

    /**
     * 控制命令
     */
    private byte privateCmd;

    /**
     * 开关锁时增加时间戳
     * @return
     */
    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 时间戳
     */
    private Integer timestamp;

    public final byte getFlag() {
        return privateFlag;
    }

    public final void setFlag(byte value) {
        privateFlag = value;
    }


    public byte getPrivateCmd() {
        return privateCmd;
    }

    public void setPrivateCmd(byte privateCmd) {
        this.privateCmd = privateCmd;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer bytes = new ByteBuffer();
        // 控制标志
        bytes.put(getFlag());
        // 命令标志
        bytes.put(getPrivateCmd());
        if (getTimestamp() != null){
            bytes.put(getTimestamp());
        }
        return bytes.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setFlag(bytes[0]);
        setPrivateCmd(bytes[1]);
    }

    @Override
    public String toString() {
        return String.format("控制标志：%1$s", (new Byte(getFlag())).toString());
    }
}
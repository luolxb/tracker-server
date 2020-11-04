package com.nts.iot.jt808.protocol;


import com.nts.iot.jt808.protocol.base.IMessageBody;

/**
 * 终端鉴权
 */
public class JT_0102 implements IMessageBody {

    private String registerNo;

    public final String getRegisterNo() {
        return registerNo;
    }

    public final void setRegisterNo(String value) {
        registerNo = value;
    }

    @Override
    public final byte[] writeToBytes() {
        return getRegisterNo().getBytes();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setRegisterNo(new String(bytes));
    }

    @Override
    public String toString() {
        return String.format("鉴权码：%1$s", getRegisterNo());
    }
}
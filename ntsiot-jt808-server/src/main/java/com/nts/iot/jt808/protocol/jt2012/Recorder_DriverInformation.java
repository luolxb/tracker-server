package com.nts.iot.jt808.protocol.jt2012;


import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

public class Recorder_DriverInformation implements IRecorderDataBlock_2012 {

    @Override
    public final byte getCommandWord() {
        return 0x01;
    }

    @Override
    public final short getDataLength() {
        return 18;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = null;
        return bytes;
    }

    private String privateDriverLicenseNo;

    public final String getDriverLicenseNo() {
        return privateDriverLicenseNo;
    }

    public final void setDriverLicenseNo(String value) {
        privateDriverLicenseNo = value;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        String license = BitConverter.getString(bytes);
        this.privateDriverLicenseNo = license;
        if (getDriverLicenseNo().length() == 15) {
            String add = "00H";
            setDriverLicenseNo(getDriverLicenseNo() + add);
        }
    }
}


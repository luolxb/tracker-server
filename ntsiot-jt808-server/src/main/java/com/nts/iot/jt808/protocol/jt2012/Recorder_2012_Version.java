package com.nts.iot.jt808.protocol.jt2012;


import com.nts.iot.jt808.utils.StringUtil;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

public class Recorder_2012_Version implements IRecorderDataBlock_2012 {

    @Override
    public final byte getCommandWord() {
        return 0x00;
    }

    @Override
    public final short getDataLength() {
        return 2;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = null;
        return bytes;
    }

    private String privateModifiedOrder;

    public final String getModifiedOrder() {
        return privateModifiedOrder;
    }

    public final void setModifiedOrder(String value) {
        privateModifiedOrder = value;
    }

    private String privateRecardVersion;

    public final String getRecardVersion() {
        return privateRecardVersion;
    }

    public final void setRecardVersion(String value) {
        privateRecardVersion = value;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        byte year = bytes[0];
        byte nub = bytes[1];
        String yearTo2 = Integer.toBinaryString(year);
        if (yearTo2.length() < 8) {
            yearTo2 = StringUtil.leftPad(yearTo2, 8, '0');
        }
        int years = Integer.parseInt(yearTo2, 2);
        String y = (new Integer(years)).toString();
        String n = Integer.toHexString(nub);
        setRecardVersion("20" + y);
        setModifiedOrder(n);
    }
}


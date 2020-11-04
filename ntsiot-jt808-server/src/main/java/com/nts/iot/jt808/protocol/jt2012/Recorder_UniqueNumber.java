package com.nts.iot.jt808.protocol.jt2012;


import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

public class Recorder_UniqueNumber implements IRecorderDataBlock_2012 {

    @Override
    public final byte getCommandWord() {
        return 0x07;
    }

    @Override
    public final short getDataLength() {
        return 35;
    }

    private String cCCcode;

    public final String getCCCcode() {
        return cCCcode;
    }

    public final void setCCCcode(String value) {
        cCCcode = value;
    }


    private String certifiedProductModel;

    public final String getCertifiedProductModel() {
        return certifiedProductModel;
    }

    public final void setCertifiedProductModel(String value) {
        certifiedProductModel = value;
    }

    private java.util.Date privateProductDateTime = new java.util.Date(0);

    public final java.util.Date getProductDateTime() {
        return privateProductDateTime;
    }

    public final void setProductDateTime(java.util.Date value) {
        privateProductDateTime = value;
    }

    private String privateProductSerialNumber;

    public final String getProductSerialNumber() {
        return privateProductSerialNumber;
    }

    public final void setProductSerialNumber(String value) {
        privateProductSerialNumber = value;
    }

    private String privateProductOther;

    public final String getProductOther() {
        return privateProductOther;
    }

    public final void setProductOther(String value) {
        privateProductOther = value;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setCCCcode(BitConverter.getString(bytes, 0, 7));
        setCertifiedProductModel(BitConverter.getString(bytes, 7, 16));
        setProductDateTime(new java.util.Date(java.util.Date.parse("20" + String.format("%02X", bytes[23]) + "-" + String.format("%02X", bytes[24]) + "-" + String.format("%02X", bytes[25]))));
        String nub1 = Integer.toHexString(bytes[26]);
        String nub2 = Integer.toHexString(bytes[27]);
        String nub3 = Integer.toHexString(bytes[28]);
        String nub4 = Integer.toHexString(bytes[29]);
        if (nub1.length() < 2) {
            nub1 = "0" + nub1;
        }
        if (nub2.length() < 2) {
            nub2 = "0" + nub2;
        }
        if (nub3.length() < 2) {
            nub3 = "0" + nub3;
        }
        if (nub4.length() < 2) {
            nub4 = "0" + nub4;
        }
        setProductSerialNumber(nub1 + nub2 + nub3 + nub4);
    }

    @Override
    public final byte[] writeToBytes() {
        return null;
    }
}

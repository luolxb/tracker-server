package com.nts.iot.jt808.protocol.jt2012;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

import java.util.ArrayList;
import java.util.List;

public class Recorder_DriverIdentity implements IRecorderDataBlock_2012 {

    private List<RecorderEvent> eventList = new ArrayList<>();

    @Override
    public final byte getCommandWord() {
        return 0x06;
    }

    @Override
    public final short getDataLength() {
        return 87;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = null;
        return bytes;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        if (bytes != null) {
            for (int i = 0; i < bytes.length / 25; i++) {
                byte[] EventOccursTime = new byte[6];
                System.arraycopy(bytes, 0 + 25 * i, EventOccursTime, 0, 6);
                String time = new java.util.Date(java.util.Date.parse("20"
                        + String.format("%02X", EventOccursTime[0]) + "-"
                        + String.format("%02X", EventOccursTime[1]) + "-"
                        + String.format("%02X", EventOccursTime[2]) + " "
                        + String.format("%02X", EventOccursTime[3]) + ":"
                        + String.format("%02X", EventOccursTime[4]) + ":"
                        + String.format("%02X", EventOccursTime[5])))
                        .toString();
                String strEventTime = time;
                byte[] nub = new byte[18];
                System.arraycopy(bytes, 6 + 25 * i, nub, 0, 18);
                String driverNub = BitConverter.getString(nub);
                if (driverNub.length() == 15) {
                    String add = "00H";
                    driverNub = driverNub + add;
                }
                String strLicense = driverNub;
                String bu = String.format("%02X", bytes[24 + 25 * i]);
                String strEventType = "";
                if (String.format("%02X", bytes[24 + 25 * i]).equals("01")) {
                    strEventType = "";
                } else if (String.format("%02X", bytes[24 + 25 * i]).equals(
                        "02")) {
                    strEventType = "";
                }
                this.eventList.add(new RecorderEvent(strEventTime, strEventType, strLicense));
            }
        }
    }

}

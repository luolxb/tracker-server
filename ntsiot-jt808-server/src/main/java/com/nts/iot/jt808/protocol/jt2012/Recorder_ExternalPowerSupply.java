package com.nts.iot.jt808.protocol.jt2012;

import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

import java.util.ArrayList;
import java.util.List;


public class Recorder_ExternalPowerSupply implements IRecorderDataBlock_2012 {

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
        String info = null;
        if (bytes != null) {
            for (int i = 0; i < bytes.length / 7; i++) {
                byte[] EventTime = new byte[6];
                System.arraycopy(bytes, 0, EventTime, 0, 6);
                String strEventTime = new java.util.Date(java.util.Date.parse("20" + String.format("%02X", EventTime[0]) + "-" + String.format("%02X", EventTime[1]) + "-" + String.format("%02X", EventTime[2]) + " " + String.format("%02X", EventTime[3]) + ":" + String.format("%02X", EventTime[4]) + ":" + String.format("%02X", EventTime[5]))).toString();

                String strEventType = "";
                if (String.format("%02X", bytes[6 + 7 * i]).equals("01")) {
                    strEventType = "ͨ";
                } else if (String.format("%02X", bytes[6 + 7 * i]).equals("02")) {
                    strEventType = "";
                }
                this.eventList.add(new RecorderEvent(strEventTime, strEventType));
            }
        }
    }

    public List<RecorderEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<RecorderEvent> eventList) {
        this.eventList = eventList;
    }


}

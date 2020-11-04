package com.nts.iot.jt808.protocol.jt2012;

import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

import java.util.ArrayList;
import java.util.List;

public class Recorder_ParameterChange implements IRecorderDataBlock_2012 {

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
            for (int i = 0; i < bytes.length / 7; i++) {

                byte[] EventTime = new byte[6];
                System.arraycopy(bytes, 0 + 7 * i, EventTime, 0, 6);
                String strTime = new java.util.Date(java.util.Date.parse("20" + String.format("%02X", EventTime[0]) + "-" + String.format("%02X", EventTime[1]) + "-" + String.format("%02X", EventTime[2]) + " " + String.format("%02X", EventTime[3]) + ":" + String.format("%02X", EventTime[4]) + ":" + String.format("%02X", EventTime[5]))).toString();
                String eventType = "";
                switch (bytes[6 + 7 * i]) {
                    case 0x00:
                        eventType = "00H";
                        break;
                    case 0x01:
                        eventType = "01H";
                        break;
                    case 0x02:
                        eventType = "O2H";
                        break;
                    case 0x03:
                        eventType = "03H";
                        break;
                    case 0x04:
                        eventType = "04H";
                        break;
                    case 0x05:
                        eventType = "05H";
                        break;
                    case 0x06:
                        eventType = "06H";
                        break;
                    case 0x07:
                        eventType = "07H";
                        break;
                    case 0x08:
                        eventType = "08H";
                        break;
                    case 0x09:
                        eventType = "09H";
                        break;
                    case 0x10:
                        eventType = "10H";
                        break;
                    case 0x11:
                        eventType = "11H";
                        break;
                    case 0x12:
                        eventType = "12H";
                        break;
                    case 0x13:
                        eventType = "13H";
                        break;
                    case 0x14:
                        eventType = "14H";
                        break;
                    case 0x15:
                        eventType = "15H";
                        break;
                    case 0x16:
                        eventType = "16H";
                        break;
                    default:
                        eventType = "";
                        break;
                }
                this.eventList.add(new RecorderEvent(strTime, eventType));
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


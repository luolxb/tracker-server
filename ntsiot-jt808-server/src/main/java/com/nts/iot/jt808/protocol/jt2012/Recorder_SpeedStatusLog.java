package com.nts.iot.jt808.protocol.jt2012;

import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

public class Recorder_SpeedStatusLog implements IRecorderDataBlock_2012 {

    private java.util.HashMap<Integer, String> privateOneMinuteSpeedLogStatus;

    public final java.util.HashMap<Integer, String> getOneMinuteSpeedLogStatus() {
        return privateOneMinuteSpeedLogStatus;
    }

    public final void setOneMinuteSpeedLogStatus(
            java.util.HashMap<Integer, String> value) {
        privateOneMinuteSpeedLogStatus = value;
    }

    private static java.util.HashMap<java.util.Date, String> privateOneMinuteLogOtherInfo;

    public static java.util.HashMap<java.util.Date, String> getOneMinuteLogOtherInfo() {
        return privateOneMinuteLogOtherInfo;
    }

    public static void setOneMinuteLogOtherInfo(
            java.util.HashMap<java.util.Date, String> value) {
        privateOneMinuteLogOtherInfo = value;
    }

    private static java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> privateSpeedLogStatus;

    public static java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> getSpeedLogStatus() {
        return privateSpeedLogStatus;
    }

    public static void setSpeedLogStatus(
            java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> value) {
        privateSpeedLogStatus = value;
    }

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
        getOneMinuteLogOtherInfo().clear();
        getSpeedLogStatus().clear();
        StringBuilder sb = new StringBuilder();
        String info = null;
        if (bytes != null) {
            for (int i = 0; i < bytes.length / 133; i++) {
                switch (bytes[0]) {
                    case 0x01:
                        info = "";
                        break;
                    case 0x02:
                        info = "";
                        break;
                    default:
                        info = "";
                        break;
                }

                byte[] StateEstimationBeginTime = new byte[6];
                System.arraycopy(bytes, 1, StateEstimationBeginTime, 0, 6);
                java.util.Date beginTime = new java.util.Date(
                        java.util.Date.parse("20"
                                + String.format("%02X",
                                StateEstimationBeginTime[0])
                                + "-"
                                + String.format("%02X",
                                StateEstimationBeginTime[1])
                                + "-"
                                + String.format("%02X",
                                StateEstimationBeginTime[2])
                                + " "
                                + String.format("%02X",
                                StateEstimationBeginTime[3])
                                + ":"
                                + String.format("%02X",
                                StateEstimationBeginTime[4])
                                + ":"
                                + String.format("%02X",
                                StateEstimationBeginTime[5])));
                byte[] StateEstimationEndTime = new byte[6];
                System.arraycopy(bytes, 7, StateEstimationEndTime, 0, 6);
                java.util.Date begintime = new java.util.Date(
                        java.util.Date.parse("20"
                                + String.format("%02X",
                                StateEstimationBeginTime[0])
                                + "-"
                                + String.format("%02X",
                                StateEstimationBeginTime[1])
                                + "-"
                                + String.format("%02X",
                                StateEstimationBeginTime[2])
                                + " "
                                + String.format("%02X",
                                StateEstimationBeginTime[3])
                                + ":"
                                + String.format("%02X",
                                StateEstimationBeginTime[4])
                                + ":"
                                + String.format("%02X",
                                StateEstimationBeginTime[5])));
                java.util.Date endTime = new java.util.Date(
                        java.util.Date.parse("20"
                                + String.format("%02X",
                                StateEstimationEndTime[0])
                                + "-"
                                + String.format("%02X",
                                StateEstimationEndTime[1])
                                + "-"
                                + String.format("%02X",
                                StateEstimationEndTime[2])
                                + " "
                                + String.format("%02X",
                                StateEstimationEndTime[3])
                                + ":"
                                + String.format("%02X",
                                StateEstimationEndTime[4])
                                + ":"
                                + String.format("%02X",
                                StateEstimationEndTime[5])));
                getOneMinuteLogOtherInfo().put(beginTime, endTime + info);
                for (int j = 0; j < 60; j++) {
                    StringBuilder sb2 = new StringBuilder();
                    /**
                     * String speed1 = Integer.parseInt(String.format("%02X",
                     * bytes[13 + 2 * j])).toString(); if (speed1.length() < 3)
                     * { speed1 = speed1.PadLeft(3, '0'); } String speed2 =
                     * Integer.parseInt(String.format("%02X", bytes[14 + 2 *
                     * j])).toString(); if (speed2.length() < 3) { speed2 =
                     * StringUitlspeed2.PadLeft(3, '0'); }
                     * getOneMinuteSpeedLogStatus().put(j, speed1 + speed2);
                     */
                }
                getSpeedLogStatus().put(begintime, getOneMinuteSpeedLogStatus());
                setOneMinuteSpeedLogStatus(new java.util.HashMap<Integer, String>());
            }
        }

    }
}

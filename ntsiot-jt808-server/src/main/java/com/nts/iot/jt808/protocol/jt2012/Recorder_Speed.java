package com.nts.iot.jt808.protocol.jt2012;


import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.utils.DateUtil;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

import java.util.Calendar;

public class Recorder_Speed implements IRecorderDataBlock_2012 {

    private java.util.HashMap<Integer, String> privateOneMinuteSpeedInfo;

    public final java.util.HashMap<Integer, String> getOneMinuteSpeedInfo() {
        return privateOneMinuteSpeedInfo;
    }

    public final void setOneMinuteSpeedInfo(java.util.HashMap<Integer, String> value) {
        privateOneMinuteSpeedInfo = value;
    }

    private java.util.HashMap<Integer, String> privateOneMinuteStateInfo;

    public final java.util.HashMap<Integer, String> getOneMinuteStateInfo() {
        return privateOneMinuteStateInfo;
    }

    public final void setOneMinuteStateInfo(java.util.HashMap<Integer, String> value) {
        privateOneMinuteStateInfo = value;
    }

    private java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> privateMinuteSpeedInfo;

    public final java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> getMinuteSpeedInfo() {
        return privateMinuteSpeedInfo;
    }

    public final void setMinuteSpeedInfo(java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> value) {
        privateMinuteSpeedInfo = value;
    }

    private java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> privateMinuteSpeedState;

    public final java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> getMinuteSpeedState() {
        return privateMinuteSpeedState;
    }

    public final void setMinuteSpeedState(java.util.HashMap<java.util.Date, java.util.HashMap<Integer, String>> value) {
        privateMinuteSpeedState = value;
    }

    @Override
    public final byte getCommandWord() {
        return 0x08;
    }

    private short privateDataLength;

    @Override
    public final short getDataLength() {
        return privateDataLength;
    }

    public final void setDataLength(short value) {
        privateDataLength = value;
    }

    private java.util.Date privateBenginTime = new java.util.Date(0);

    public final java.util.Date getBenginTime() {
        return privateBenginTime;
    }

    public final void setBenginTime(java.util.Date value) {
        privateBenginTime = value;
    }

    private java.util.Date privateEndTime = new java.util.Date(0);

    public final java.util.Date getEndTime() {
        return privateEndTime;
    }

    public final void setEndTime(java.util.Date value) {
        privateEndTime = value;
    }

    private int privateMaxNumber;

    public final int getMaxNumber() {
        return privateMaxNumber;
    }

    public final void setMaxNumber(int value) {
        privateMaxNumber = value;
    }

    @Override
    public final byte[] writeToBytes() {
        byte[] bytes = new byte[14];

        String strBeginTime = DateUtil.toStringByFormat(this.getBenginTime(), "yyMMddHHmmss");

        String begintimeyear2 = strBeginTime.substring(0, 2);
        String begintimemonth = strBeginTime.substring(2, 4);
        String begintimeday = strBeginTime.substring(4, 6);
        String begintimehour = strBeginTime.substring(6, 8);
        String begintimeminute = strBeginTime.substring(8, 10);
        String begintimesecond = strBeginTime.substring(10, 12);
        byte[] byteBegintime = time(begintimeyear2, begintimemonth, begintimeday, begintimehour, begintimeminute, begintimesecond);

        String strEndTime = DateUtil.toStringByFormat(this.getBenginTime(), "yyMMddHHmmss");
        String endtimeyear2 = strEndTime.substring(0, 2);
        String endtimemonth = strEndTime.substring(2, 4);
        String endtimeday = strEndTime.substring(4, 6);
        String endtimehour = strEndTime.substring(6, 8);
        String endtimeminute = strEndTime.substring(8, 10);
        String endtimesecond = strEndTime.substring(10, 12);
        byte[] byteEedTime = time(endtimeyear2, endtimemonth, endtimeday, endtimehour, endtimeminute, endtimesecond);
        System.arraycopy(byteBegintime, 0, bytes, 0, 6);
        System.arraycopy(byteEedTime, 0, bytes, 6, 6);
        bytes[12] = (byte) (getMaxNumber() >> 8);
        bytes[13] = (byte) (getMaxNumber());

        return bytes;
    }

    public final byte[] time(String year, String month, String day, String hour, String minte, String second) {
        byte years;
        int a, b;
        if (year.length() > 1) {
            a = Integer.parseInt(year.substring(0, 1));
            b = Integer.parseInt(year.substring(1, 2));
            years = (byte) ((int) (a << 4) + (int) b);
        } else {
            a = Integer.parseInt(year.substring(0, 1));
            years = (byte) ((int) a);
        }

        byte months;
        if (month.length() > 1) {
            a = Integer.parseInt(month.substring(0, 1));
            b = Integer.parseInt(month.substring(1, 2));
            months = (byte) ((int) (a << 4) + (int) b);
        } else {
            a = Integer.parseInt(year.substring(0, 1));
            months = (byte) ((int) a);
        }

        byte days;
        if (day.length() > 1) {
            a = Integer.parseInt(day.substring(0, 1));
            b = Integer.parseInt(day.substring(1, 2));
            days = (byte) ((int) (a << 4) + (int) b);
        } else {
            a = Integer.parseInt(day.substring(0, 1));
            days = (byte) ((int) a);
        }

        byte hours;
        if (hour.length() > 1) {
            a = Integer.parseInt(hour.substring(0, 1));
            b = Integer.parseInt(hour.substring(1, 2));
            hours = (byte) ((int) (a << 4) + (int) b);
        } else {
            a = Integer.parseInt(hour.substring(0, 1));
            hours = (byte) ((int) a);
        }

        byte mintes;
        if (minte.length() > 1) {
            a = Integer.parseInt(minte.substring(0, 1));
            b = Integer.parseInt(minte.substring(1, 2));
            mintes = (byte) ((int) (a << 4) + (int) b);
        } else {
            a = Integer.parseInt(minte.substring(0, 1));
            mintes = (byte) ((int) a);
        }

        byte seconds;
        if (second.length() > 1) {
            a = Integer.parseInt(second.substring(0, 1));
            b = Integer.parseInt(second.substring(1, 2));
            seconds = (byte) ((int) (a << 4) + (int) b);
        } else {
            a = Integer.parseInt(second.substring(0, 1));
            seconds = (byte) ((int) a);
        }

        byte[] date = new byte[6];

        date[0] = years;
        date[1] = months;
        date[2] = days;
        date[3] = hours;
        date[4] = mintes;
        date[5] = seconds;

        return date;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        setOneMinuteSpeedInfo(new java.util.HashMap<>());
        setOneMinuteStateInfo(new java.util.HashMap<>());
        setMinuteSpeedState(new java.util.HashMap<>());
        setMinuteSpeedInfo(new java.util.HashMap<>());
        StringBuilder sb = new StringBuilder();
        if (bytes != null) {
            for (int i = 0; i < bytes.length / 126; i++) {
                int add = 126 * i;
                byte[] minutedate = new byte[126];
                System.arraycopy(bytes, 0 + add, minutedate, 0, 126);
                java.util.Date dt = new java.util.Date();
                for (int j = 0; j < 60; j++) {
                    byte[] time = new byte[6];
                    System.arraycopy(minutedate, 0, time, 0, 6);
                    new java.util.Date(java.util.Date.parse("20" + String.format("%02X", time[0]) + "-" + String.format("%02X", time[1]) + "-" + String.format("%02X", time[2]) + " " + String.format("%02X", time[3]) + ":" + String.format("%02X", time[4]) + ":" + String.format("%02X", time[5])));

                    String SpeedInfo = "" + BitConverter.toUInt32(minutedate[6 + j * 2]);
                    String State = "";

                    if ((minutedate[7 + j * 2] & 0x80) == 0x80) {
                        State = "1";
                    } else {
                        State = "0";
                    }
                    if ((minutedate[7 + j * 2] & 0x40) == 0x40) {
                        State += "1";
                    } else {
                        State += "0";
                    }
                    if ((minutedate[7 + j * 2] & 0x20) == 0x20) {
                        State += "1";
                    } else {
                        State += "0";
                    }
                    if ((minutedate[7 + j * 2] & 0x10) == 0x10) {
                        State += "1";
                    } else {
                        State += "0";
                    }
                    if ((minutedate[7 + j * 2] & 8) == 8) {
                        State += "1";
                    } else {
                        State += "0";
                    }
                    if ((minutedate[7 + j * 2] & 4) == 4) {
                        State += "1";
                    } else {
                        State += "0";
                    }
                    if ((minutedate[7 + j * 2] & 2) == 2) {
                        State += "1";
                    } else {
                        State += "0";
                    }
                    if ((minutedate[7 + j * 2] & 0) == 0) {
                        State += "1";
                    } else {
                        State += "0";
                    }


                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dt);

                    getOneMinuteSpeedInfo().put(calendar.get(Calendar.SECOND) + j, SpeedInfo);
                    getOneMinuteStateInfo().put(calendar.get(Calendar.SECOND) + j, State);
                }

                getMinuteSpeedInfo().put(dt, getOneMinuteSpeedInfo());
                getMinuteSpeedState().put(dt, getOneMinuteStateInfo());

            }
        }
    }

}
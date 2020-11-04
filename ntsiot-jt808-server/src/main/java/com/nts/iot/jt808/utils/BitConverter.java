/*******************************************************************************
 * @(#)BitConverter.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.utils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *     字节数组与java基本类型之间的转换
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:49
 */
public class BitConverter {

    /**
     * 整数转字节数组
     * @param num
     * @return
     */
    public static byte[] getBytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >>> 24);
        result[1] = (byte) (num >>> 16);
        result[2] = (byte) (num >>> 8);
        result[3] = (byte) (num);
        return result;
    }

    /**
     * 字节数组转成无符合short类型
     * @param bytes
     * @param start
     * @return
     */
    public static int toUInt16(byte[] bytes, int start) {
        int value = 0;
        int m = start+2;
        for (int i = start; i < m; i++) {
            int shift = (m - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static int toUInt32(byte[] bytes, int start) {
        int value = 0;
        int m = start+4;
        for (int i = start; i < m; i++) {
            int shift = (m - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static int toUInt32(byte b) {
        int value = 0;
        int m = 4;
        int shift = (m - 1 - 3) * 8;
        value += (b & 0x000000FF) << shift;
        return value;
    }

    public static byte[] getBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 6位BCD码转成日期
     * @param bytes
     * @param start
     * @return
     */
    public static Date getDate(byte[] bytes, int start) {
        StringBuilder sb = new StringBuilder();
        sb.append("20").append(String.format("%02X", bytes[start + 0]))
                .append("-").append(String.format("%02X", bytes[start + 1]))
                .append("-").append(String.format("%02X", bytes[start + 2]))
                .append(" ").append(String.format("%02X", bytes[start + 3]))
                .append(":").append(String.format("%02X", bytes[start + 4]))
                .append(":").append(String.format("%02X", bytes[start + 5]));
        String strDate = sb.toString();
        Date d = DateUtil.stringToDatetime(strDate, "yyyy-MM-dd HH:mm:ss");
        return d;
    }

    public static String getString(byte[] data) {
        return getString(data, 0, data.length);
    }

    public static String getString(byte[] data, int start, int len) {
        return new String(data, start, len, StandardCharsets.UTF_8);
    }

    /**
     * 日期转6位BCD码
     * @param date
     * @return
     */
    public static byte[] getBytes(Date date) {
        int start = 0;
        byte[] bytes = new byte[6];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        bytes[start++] = Byte.parseByte("" + (year - 2000), 16);
        bytes[start++] = Byte.parseByte((month + ""), 16);
        bytes[start++] = Byte.parseByte((day + ""), 16);
        bytes[start++] = Byte.parseByte((hour + ""), 16);
        bytes[start++] = Byte.parseByte((minute + ""), 16);
        bytes[start++] = Byte.parseByte((second + ""), 16);
        return bytes;
    }

    public static String format(Date date) {

        SimpleDateFormat time = new SimpleDateFormat("yy-MM-dd HH:mm:s");
        return time.format(date);
    }

    public int getUnsignedByte(byte data) {
        return data & 0x0FF;
    }

    public int getUnsignedByte(short data) {

        return data & 0x0FFFF;
    }

    public long getUnsignedInt(int data) {
        return data & 0x0FFFFFFFFl;
    }

}

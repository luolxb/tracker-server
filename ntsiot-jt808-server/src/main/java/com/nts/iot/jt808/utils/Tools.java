/*******************************************************************************
 * @(#)Tools.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 15:59
 */
public class Tools {

    public static int CRC(byte[] buffer) {
        int wTemp = 0;
        int crc = 0xffff;

        for (int i = 0; i < buffer.length; i++) {
            for (int j = 0; j < 8; j++) {
                wTemp = (short)(((buffer[i] << j) & 0x80) ^ ((crc & 0x8000) >> 8));

                crc <<= 1;
                if (wTemp != 0)
                {
                    crc ^= 0x1021;
                }
            }
        }
        return (crc);
    }

    public static String turnDataLength(String data, int length) {
        int data_length = data.length();
        for (int i = data_length; i < length; i++) {
            data = "0" + data;
        }
        return data;
    }

    public static String turnStrLength(String data, int length) {
        int data_length = data.length();
        for (int i = data_length; i < length; i++) {
            data = data + "0";
        }
        return data;
    }

    public static String ToHexString(byte[] bts) {
        StringBuilder strBuild = new StringBuilder();

        for (int i = 0; i < bts.length; i++) {
            strBuild.append(ToHexString(bts[i]));
        }
        return strBuild.toString();
    }

    public static String ToHexFormatString(byte[] bts) {
        StringBuilder strBuild = new StringBuilder();

        for (int i = 0; i < bts.length; i++) {
            strBuild.append(ToHexString(bts[i])).append(" ");
        }
        return strBuild.toString();
    }

    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = ((byte) (parse(c0) << 4 | parse(c1)));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return c - 'a' + 10 & 0xF;
        }
        if (c >= 'A') {
            return c - 'A' + 10 & 0xF;
        }
        return c - '0' & 0xF;
    }

    public static String ToHexString(byte data) {
        return Integer.toHexString(data & 0xff | 0x100).substring(1);
    }

    public static String ToHexString(Short data) {
        return Integer.toHexString(data & 0xffff | 0x10000).substring(1);
    }

    public static String ToHexString(long val) {
        String tmp = Long.toHexString(val);
        StringBuilder sb = new StringBuilder("0000000000000000");
        sb.replace(16 - tmp.length(), 16, tmp);
        return sb.toString();
    }

    public static String ToHexString(int data) {
        String tmp = Integer.toHexString(data);
        StringBuilder sb = new StringBuilder("00000000");
        sb.replace(8 - tmp.length(), 8, tmp);
        return sb.toString();
    }

    public static String ToHexString(long data, int byteNum) {
        int totalLen = byteNum * 2;
        String tmp = Long.toHexString(data);
        while(tmp.length() < totalLen) {
            tmp = "0" + tmp;
        }
        int start = tmp.length() - totalLen;
        tmp = tmp.substring(start);
        return tmp;
    }

    public static String ToHexString(String str) {
        String str1 = "";
        try {
            byte[] b = str.getBytes("gbk");
            int i = 0;
            int max = b.length;
            for (; i < max; i++) {
                str1 = str1 + Integer.toHexString(b[i] & 0xFF);
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("转换ToHexString" + e.getMessage());
        }
        return str1;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }

    public static String ToHexString(String str, int length) {
        if(str == null) {
            str = "";
        }
        String str1 = "";
        byte[] b = (byte[]) null;
        try {
            b = str.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            System.out.println("转换(ControllerReport TurnISN)" + e.getMessage());
        }
        System.out.println();
        int i = 0;
        int max = b.length;
        for (; i < max; i++) {
            str1 = str1 + Integer.toHexString(b[i] & 0xFF);
        }
        str1 = str1.toUpperCase();
        return turnStrLength(str1, length * 2);
    }

    public static int getSeconds(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String year = format.format(date);
        return Integer.valueOf(year.substring(12, 14)).intValue();
    }


    public static void main(String[] args) {
        short t = (short) 0x01;

        String a = String.format("%04x", t);
        System.out.println(a);

    }
}

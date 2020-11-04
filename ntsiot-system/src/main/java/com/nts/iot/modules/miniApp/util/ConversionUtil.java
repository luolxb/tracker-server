package com.nts.iot.modules.miniApp.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

/**
 * 10进制、62进制互转
 * edited by zhibo on 2015/05/21.
 */
public class ConversionUtil {

    private static int scale = 62;

    private static String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 将10进制转化为62进制
     * @param num
     * @param length 转化成的62进制长度，不足length长度的话高位补0，否则不改变什么
     * @return
     */
    public static String encode(long num, int length){
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (num > scale - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }


    /**
     * 将62进制转换成10进制数
     *
     * @param str
     * @return
     */
    public static Long decode( String str ) {
        /**
         * 将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            /**
             * 查找字符的索引位置
             */
            index = chars.indexOf(str.charAt(i));
            /**
             * 索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        return num;
    }

//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        System.out.println("62System=" +encode(105501005149L, 7));
//        System.out.println("10System=" +decode("1R9RsTj"));
//    }



}


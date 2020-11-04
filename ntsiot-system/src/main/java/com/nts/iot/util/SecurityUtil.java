package com.nts.iot.util;

import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityUtil {

    private static final String PATTERN1 = "密码必须有大小写字母和数字组成";
    private static final String PATTERN2 = "最少8个字符";
    private static final String PATTERN3 = "不可以连续3个相同的字符";
    private static final String PATTERN4 = "首字母大写";


    private static final String EXP1 = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])$";
    private static final String EXP2 = "^.{8,}$";
    private static final String EXP3 = "^(?!.*?([a-zA-Z]+?)\\1\\1)$";
    private static final String EXP4 = "^[A-Z][A-z0-9]*$";

    private static final String PASS = "成功";
    private static final Integer CHECK_FAIL= -1;
    private static final Integer CHECK_OK= 0;



    //把需要过滤的样式ID依次放在patterns里，得出检验结果
    public static RegularExpResult doCheck(String psw, Integer[] patterns) {

        RegularExpResult rs = RegularExpResult.OK;
        for (Integer pattern : patterns) {
            if (checkPwdFowmat(psw, pattern) == false) {
                rs = parsePattern(pattern);
                break;
            }
        }
        return rs;
    }

    // 正则表达式：必须同时含有大写字母，小写字母，数字，长度至少为3
    private static boolean checkPwdFowmat(String psw, int pattern) {
        if (psw == null || psw.isEmpty()) {
            return false;
        }
        String regex = "EXP" + pattern;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(psw);
        return m.matches();
    }
    private static RegularExpResult parsePattern(int pattern){
        RegularExpResult rs = RegularExpResult.OK;
        switch (pattern) {
            case 1:
                rs= RegularExpResult.FAIL_PATTERN1;
                break;
            case 2:
                rs= RegularExpResult.FAIL_PATTERN2;
                break;
            case 3:
                rs= RegularExpResult.FAIL_PATTERN3;
                break;
            case 4:
                rs= RegularExpResult.FAIL_PATTERN4;
                break;
            default:
                break;
        }
        return rs;
    }

    public enum RegularExpResult {
        FAIL_PATTERN1(PATTERN1, CHECK_FAIL, EXP1),
        FAIL_PATTERN2(PATTERN2, CHECK_FAIL, EXP2),
        FAIL_PATTERN3(PATTERN3, CHECK_FAIL, EXP3),
        FAIL_PATTERN4(PATTERN4, CHECK_FAIL, EXP4),
        OK(PASS,CHECK_OK, "");

        private final String msg;
        private final Integer status;
        private final String express;

        public String getMsg() {
            return msg;
        }

        public Integer getStatus() {
            return status;
        }

        public String getExpress() {
            return express;
        }


        RegularExpResult(String msg, Integer status, String express) {
            this.msg = msg;
            this.status = status;
            this.express = express;
        }

        public String toJson() throws Exception {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", this.msg);
            jsonObject.put("status", this.status);
            jsonObject.put("expess", this.express);
            return jsonObject.toString();
        }


    }
}

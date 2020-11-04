/*******************************************************************************
 * @(#)JsonUtil.java 2019-01-09
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:cej@rnstec.com">迟恩军</a>
 * @version jlight 1.0 $ 2019-01-09 16:33
 */
public class JsonUtil {

    /**
     * 获得json字符串
     *
     * @param obj 任何对象
     * @return
     */
    public static String getJson(Object obj) {
        String dataJson = "";
        if (obj != null) {
            dataJson = JSON.toJSONString(obj);
        }
        return dataJson;
    }

    /**
     * 判断json格式是否正确
     *
     * @param json json字符串
     * @return
     */
    public static boolean isJson(String json) {
        try {
            JSONObject.parseObject(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * json转消息包List<String>
     *
     * @param dataJson
     * @return
     */
    public static List<String> jsonConvertList(String dataJson) {
        List<String> list = null;
        if (dataJson != null && !"".equals(dataJson)) {
            try {
                list = JSON.parseObject(dataJson, new TypeReference<List<String>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    /**
     * json转消息包Map
     *
     * @param dataJson
     * @return
     */
    public static Map<String, String> jsonConvertMap(String dataJson) {
        Map<String, String> map = null;
        if (dataJson != null && !"".equals(dataJson)) {
            try {
                map = JSON.parseObject(dataJson, new TypeReference<Map<String, String>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <T> List<T> jsonConvertList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    public static <T> T jsonConvertObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
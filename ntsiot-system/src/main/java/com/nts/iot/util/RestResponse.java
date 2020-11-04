package com.nts.iot.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.nts.iot.util
 * @program: nts
 * @author: ruosen
 * @create: 2020-04-30 18:01
 **/
public class RestResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public RestResponse() {
        put("code", 0);
        put("msg", "success");
    }

    public static RestResponse error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static RestResponse error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static RestResponse error(int code, String msg) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("code", code);
        restResponse.put("msg", msg);
        return restResponse;
    }

    public static RestResponse object(int code, String msg, Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("code", code);
        restResponse.put("msg", msg);
        restResponse.put("data", data);
        return restResponse;
    }

    public static RestResponse success(String msg) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("msg", msg);
        return restResponse;
    }

    public static RestResponse success(Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("data", data);
        return restResponse;
    }

    public static RestResponse success(String msg, Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("msg", msg);
        restResponse.put("data", data);
        return restResponse;
    }

    public static RestResponse success(Map<String, Object> map) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("data", map);
        return restResponse;
    }

    public static RestResponse success(String msg, Map<String, Object> map) {
        RestResponse restResponse = new RestResponse();
        restResponse.put("msg", msg);
        restResponse.put("data", map);
        return restResponse;
    }

    public static RestResponse success() {
        return new RestResponse();
    }

    @Override
    public RestResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

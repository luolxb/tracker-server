package com.nts.iot.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @PackageName: com.nts.itech.trackproxy.utils
 * @program: track-proxy
 * @author: ruosen
 * @create: 2020-06-15 17:44
 **/
@Slf4j
@Component
public class BaiDuGeocoderUtils {


    private String key ="http://api.map.baidu.com/reverse_geocoding/v3/";

    private String URL = "675o3UG6If1y2qooX260PGZR7UBqOsy3";


    public String getBaiDuLocation(String language, String lat, String lon, String coordtype) {
        System.setProperty("java.net.useSystemProxies", "true");

        String location = lat + "," + lon;

        String line;
        String res = "";
        String formattedAddress = "";
        try {

            java.net.URL url = new URL(URL + "?language=" + language + "&output=json&radius=10&sensor=true&location=" + location + "&ak=" + key + "&coordtype=" + coordtype);
            URLConnection con = url.openConnection();

            System.out.println(url);
            log.info("BaiDuGeocoderUtils url =>{}",url);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

            while ((line = br.readLine()) != null) {
                res += line.trim();
            }
            if (res != "") {
                JSONObject parse = JSONObject.parseObject(res);
                JSONObject result = (JSONObject) parse.get("result");
                if (result == null) {
                    return null;
                }
                formattedAddress = (String) result.get("formatted_address");
            }
        } catch (Exception e) {
            log.error("e", e);
        }
        return formattedAddress;
    }
}
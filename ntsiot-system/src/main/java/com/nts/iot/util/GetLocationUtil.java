package com.nts.iot.util;


import com.alibaba.fastjson.JSONObject;

import java.net.URL;

public class GetLocationUtil {

    /**
     * 高德地图
     * 根据坐标获取具体地址
     * @param
     * @return
     */
    public static String getAdd(String lat, String log){
        String coor = log + "," + lat;
        String urlString = "http://restapi.amap.com/v3/geocode/regeo?key=8325164e247e15eea68b59e89200988b&s=rsv3&location="+coor+"&radius=2800&platform=JS&logversion=2.0&sdkversion=1.3&appname=http%3A%2F%2Flbs.amap.com%2Fconsole%2Fshow%2Fpicker&csid=49851531-2AE3-4A3B-A8C8-675A69BCA316";
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line+"\n";
            }
            in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());
        }
//        System.out.println(res);

        /**
         * {"status":"1","regeocode":{"addressComponent":{"city":"深圳市","province":"广东省","adcode":"440304","district":"福田区",
         * "towncode":"440304004000","streetNumber":{"number":"5024号","location":"114.068974,22.5297794","direction":"西北",
         * "distance":"20.0727","street":"滨河大道"},"country":"中国","township":"福田街道","businessAreas":[{"location":"114.087405,22.543854",
         * "name":"华强北","id":"440304"},{"location":"114.042766,22.521577","name":"新洲","id":"440304"},{"location":"114.083088,22.555988",
         * "name":"黄木岗","id":"440304"}],"building":{"name":"联合广场","type":"商务住宅;楼宇;商务写字楼"},"neighborhood":{"name":[],"type":[]},
         * "citycode":"0755"},"formatted_address":"广东省深圳市福田区福田街道联合广场联合广场A座(彩田路)"},"info":"OK","infocode":"10000"}
         */
        JSONObject parse = (JSONObject)JSONObject.parse(res);
        String status = (String) parse.get("status");
//        System.out.println("status:"+status);

        String regeocode = parse.get("regeocode").toString();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(regeocode);
        String formatted_address = (String) jsonObject.get("formatted_address");
//        System.out.println("formatted_address:"+formatted_address);
        return formatted_address;
    }

//    public static void main(String[] args) {
//        String add = getAdd("22.52966215200301","114.06912262135019");
//        System.out.println(add);
//    }
}
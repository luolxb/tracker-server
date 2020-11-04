package com.nts.iot.manage;

import com.nts.iot.config.EngineConfig;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 高德地图Api管理
 */
@Component
public class GaoDeMapApiManager {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gaode.userKey}")
    private String userKey;

    public static final String RAIL_NAME = "RAIL_NAME_";

    /**
     * 围栏 url+key TODO  userKey
     */
    public static String railUrl = "https://restapi.amap.com/v4/geofence/meta?key=fa7bf16deb815e729283e0e8407a73a7";

    /**
     * 围栏设备监控url
     */
    public static String monitoringRailUrl = "https://restapi.amap.com/v4/geofence/status?key=fa7bf16deb815e729283e0e8407a73a7";


    /**
     * 监控车辆是否在电子围栏内
     */
    public static List<CollectMessage> checkRail(List<String> railList, List<CollectMessage> collectMessageList) {
        List<CollectMessage> resultList = new ArrayList<>();
        // 创建围栏
        addRail(railList);
        // 比较是否出围栏
        resultList = compareRail(collectMessageList);
        // 查询围栏
        List<String> gidList = getGid(selectRail());
        if (gidList != null && gidList.size() > 0) {
            for (int i = 0; i < gidList.size(); i++) {
                // 删除围栏
                delRail(gidList.get(i));
            }
        }
        return resultList;
    }


    /**
     * 比较是否出围栏    TODO  调用高德api比较  调研中
     *
     * @param collectMessageList 出围栏的车辆信息
     * @return
     */
    private static List<CollectMessage> compareRail(List<CollectMessage> collectMessageList) {
        // TODO
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> paramMap = new HashMap<>();
        // TODO  用户key
        paramMap.put("key", "fa7bf16deb815e729283e0e8407a73a7");
        // TODO 猜测是设备code
        paramMap.put("diu", "10009011");
//        // 经度,纬度,时间戳
        paramMap.put("locations", "121.357779,31.217651," + getTime());

        String url = "https://restapi.amap.com/v4/geofence/status?key=fa7bf16deb815e729283e0e8407a73a7&diu=12345&locations=121.357779,31.217651," + getTime();

        // 调用创建围栏接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        System.out.println("监控车辆 = " + responseEntity.getBody());

        return null;
    }


    /**
     * 创建电子围栏，一次最多创建10个
     *
     * @param railList 围栏坐标list
     * @return
     */
    private static List<String> addRail(List<String> railList) {
        // TODO
        RestTemplate restTemplate = new RestTemplate();
        // 围栏唯一标识 gidList
        List<String> resultList = new ArrayList<>();
        // 围栏list判空
        if (railList != null && railList.size() > 0) {
            // 循环单个围栏的坐标点
            for (int i = 0; i < railList.size(); i++) {
                String name = RAIL_NAME + i;
                Map<String, String> paramMap = new HashMap<>();
                // 围栏名称
                paramMap.put("name", name);
                String[] coordinatePointArray = railList.get(i).split(";");
                if (coordinatePointArray.length > 1) {
                    // 多边形围栏坐标点   lon1,lat1;lon2,lat2;lon3,lat3（3<=点个数<=5000）。多边形围栏外接圆半径最大为5000米。
                    paramMap.put("points", railList.get(i));
                } else {
                    paramMap.put("center", railList.get(i));
                    paramMap.put("radius", "1000");
                }
                // 围栏监控状态
                paramMap.put("enable", "true");
                // 当前日期
                paramMap.put("valid_time", getDate());
                // 一周内围栏监控日期的重复模式
                paramMap.put("repeat", "Mon,Tues,Wed,Thur,Fri,Sat,Sun");
                // 一天内围栏监控时段
                paramMap.put("time", "00:00,11:59;13:00,23:59");
                // 围栏描述
                paramMap.put("desc", name + "围栏描述");
                // 配置触发围栏所需动作
                paramMap.put("alert_condition", "enter;leave");
                // 调用创建围栏接口
                ResponseEntity<String> responseEntity = restTemplate.exchange(railUrl, HttpMethod.POST, EngineConfig.getHttpEntity(JsonUtil.getJson(paramMap)), String.class);
                /**
                 * 判断返回结果
                 */
                if (200 == responseEntity.getStatusCodeValue()) {
                    String resultJson = responseEntity.getBody();
                    // 返回结果判空
                    if (resultJson != null && !"".equals(resultJson)) {
                        System.out.println("创建电子围栏返回结果===" + resultJson);
                        resultList.add(resultJson);
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 查询围栏信息
     *
     * @return
     */
    private static String selectRail() {
        String result = "";
        // TODO restTemplate
        RestTemplate restTemplate = new RestTemplate();
        // 调用查询接口返回值
        ResponseEntity<String> resultResponseEntity = restTemplate.exchange(railUrl, HttpMethod.GET, null, String.class);
        // 查询成功
        if (200 == resultResponseEntity.getStatusCodeValue()) {
            result = resultResponseEntity.getBody();
        }
        return result;
    }

    /**
     * 删除围栏
     */
    private static String delRail(String gid) {
        if (gid != null && !"".equals(gid)) {
            // TODO restTemplate
            RestTemplate restTemplate = new RestTemplate();
            // 调用查询接口返回值
            ResponseEntity<String> resultResponseEntity = restTemplate.exchange(railUrl + "&method=delete&gid=" + gid, HttpMethod.POST, null, String.class);
            // 返程200  成功
            if (200 == resultResponseEntity.getStatusCodeValue()) {
                String result = resultResponseEntity.getBody();
                // 返回结果判空
                if (result != null && !"".equals(result)) {
                    Map<String, String> map = JsonUtil.jsonConvertObject(result, Map.class);
                    // map判空
                    if (map != null && map.size() > 0) {
                        System.out.println("电子围栏=" + gid + "&删除结果=" + map.get("errmsg"));
                        gid = map.get("errmsg");
                    }
                }
            }
        }
        // 删除围栏
        return gid;
    }

    /**
     * 根据查询结果返回gid
     *
     * @param result
     * @return
     */
    private static List<String> getGid(String result) {
        // 围栏信息返回
        List<String> resultList = new ArrayList<>();
        // 返回结果判空
        if (result != null && !"".equals(result)) {
            Map<String, Object> map = JsonUtil.jsonConvertObject(result, Map.class);
            // 判断查询是否成功
            if ("OK".equals(map.get("errmsg"))) {
                // 获得data
                Map<String, Object> data = JsonUtil.jsonConvertObject(String.valueOf(map.get("data")), Map.class);
                // data判空
                if (data != null && data.size() > 0) {
                    // 获得 rsList
                    List<Object> rsList = JsonUtil.jsonConvertObject(String.valueOf(data.get("rs_list")), List.class);
                    // list判空
                    if (rsList != null && rsList.size() > 0) {
                        for (int i = 0; i < rsList.size(); i++) {
                            Map<String, String> rsMap = JsonUtil.jsonConvertObject(String.valueOf(rsList.get(i)), Map.class);
                            // rsMap判空
                            if (rsMap != null && rsMap.size() > 0) {
                                System.out.println("查询电子围栏的gid=" + rsMap.get("gid"));
                                resultList.add(rsMap.get("gid"));
                            }
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 获得当前日期  yyyy-MM-dd
     *
     * @return
     */
    private static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    /**
     * 获得时间戳
     *
     * @return
     */
    private static String getTime() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static void main(String[] args) {
        String str = "[\n" +
                "\"121.357779,31.217651;121.357744,31.21841;121.358291,31.217854\",\n" +
                "\"121.43279,31.224546;121.432929,31.22383;121.433096,31.223798;121.433305,31.223849;121.433461,31.223977;121.433503,31.224101;121.433487,31.224211;121.433498,31.22439;121.433439,31.224495;121.433359,31.224628;121.433128,31.224683;121.432833,31.224697;121.432457,31.224656\",\n" +
                "\"121.3814,31.227528;121.38155,31.227184;121.381813,31.227198;121.381781,31.227381\",\n" +
                "\"121.364304,31.240935;121.364051,31.239843;121.364212,31.239738;121.365012,31.24076\",\n" +
                "\"121.330598,31.280522;121.330646,31.279536;121.331966,31.279348;121.332116,31.280573\",\n" +
                "\"121.349104,31.241438;121.348819,31.239732;121.350831,31.239434;121.351105,31.241012\",\n" +
                "\"121.286022,31.249752;121.286585,31.248839;121.287213,31.249147;121.286617,31.249133\",\n" +
                "\"121.519268,31.252506;121.519456,31.252002;121.520201,31.252075;121.519858,31.252662\",\n" +
                "\"121.766128,31.183179;121.766552,31.182261;121.767201,31.182849\",\n" +
                "\"121.640698,31.213706;121.640833,31.212967;121.641187,31.21338;121.640237,31.213265\",\n" +
                "\"121.489862,31.21338;121.490114,31.213003;121.490602,31.213205;121.490441,31.213499\"\n" +
                "]";

        /**
         * 测试查询 删除
         */
//        List<String> gidList = getGid(selectRail());
//        if (gidList != null && gidList.size() > 0) {
//            for (int i = 0; i < gidList.size(); i++) {
//                delRail(gidList.get(i));
//            }
//        }


        /**
         *      查询电子围栏
         */
        if (!"".equals(selectRail())) {
            getGid(selectRail());
        }

        /**
         * 创建
         */
//        List<String> railList = new ArrayList<>();
//        railList.add("121.357779,31.217651;121.357744,31.21841;121.358291,31.217854");
//        railList.add("121.3814,31.227528;121.38155,31.227184;121.381813,31.227198;121.381781,31.227381");
//        railList.add("121.519268,31.252506;121.519456,31.252002;121.520201,31.252075;121.519858,31.252662");
//
//        System.out.println(addRail(railList));


        /**
         * 监控车辆
         */
//        checkRail();
    }
}

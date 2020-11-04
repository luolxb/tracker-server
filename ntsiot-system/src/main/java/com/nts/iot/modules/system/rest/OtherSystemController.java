/*******************************************************************************
 * @(#)OtherSystemController.java 2019-06-04
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.system.rest;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.dto.RidingTrack;
import com.nts.iot.modules.system.model.Bike;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Dict;
import com.nts.iot.modules.system.model.DictDetail;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.DictDetailService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import static com.nts.iot.constant.RedisKey.VECHILE_STATE;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-06-04 13:48
 */
@RestController
@RequestMapping("osc")
public class OtherSystemController {

    @Autowired
    BikeManagerService bikeManagerService;

    @Autowired
    DeptService deptService;


    @Autowired
    DictDetailService detailService;

    @Autowired
    RedisUtil redisUtil;


    /* 车辆类型接口 */
    @GetMapping("getBikeTypes")
    @PreAuthorize("hasAnyRole('OTHER_SYSTEM')")
    public ResponseEntity<List<Map<String, Object>>> getBikeTypes() {
        Dict dict = detailService.findDict("bike_type");
        if (dict == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        // 根据bikeBarcode查询 Bike
        QueryWrapper<DictDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dict.getId());
        List<DictDetail> dictDetails = detailService.list(queryWrapper);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DictDetail dictDetail : dictDetails) {
            Map<String, Object> map = new HashMap<>();
            map.put("lable", dictDetail.getLabel());
            map.put("value", dictDetail.getValue());
            mapList.add(map);
        }
        return new ResponseEntity<>(mapList, HttpStatus.OK);
    }


    /* 辖区接口 */
    @GetMapping("getAllDept")
    @PreAuthorize("hasAnyRole('OTHER_SYSTEM')")
    public ResponseEntity<List<Dept>> getAllDept() {
        // 根据bikeBarcode查询 Bike
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        List<Dept> depts = deptService.list(queryWrapper);
        return new ResponseEntity<>(depts, HttpStatus.OK);
    }

    /**
     * 派出所 所有的车辆信息
     *
     * @return
     */
    @GetMapping("getAllBikes")
    @PreAuthorize("hasAnyRole('OTHER_SYSTEM')")
    public ResponseEntity<List<Bike>> getAllBikes(Long jurisdiction, String bikeType, String bikeBarcode) {
        // 根据bikeBarcode查询 Bike
        QueryWrapper<Bike> queryWrapper = new QueryWrapper<>();
        if (jurisdiction != null) {
            queryWrapper.eq("jurisdiction_id", jurisdiction);
        }
        if (bikeType != null) {
            queryWrapper.eq("type", bikeType);
        }
        if (bikeBarcode != null) {
            queryWrapper.eq("bike_barcode", bikeBarcode);
        }
        List<Bike> bikes = bikeManagerService.list(queryWrapper);
        for(int i=0;i<bikes.size();i++){
            CollectMessage collectMessage = JsonUtil.jsonConvertObject(redisUtil.getData(VECHILE_STATE + bikes.get(i).getLockBarcode()), CollectMessage.class);
            if(null==collectMessage){
                continue;
            }
            bikes.get(i).setLatitude(collectMessage.getLatitude());
            bikes.get(i).setLongitude(collectMessage.getLongitude());
        }
        return new ResponseEntity<>(bikes, HttpStatus.OK);
    }

    /**
     * 实时位置信息
     *
     * @param jurisdiction 辖区id
     * @param bikeBarcodes 车条码的list
     * @return
     */
    @GetMapping("getBikeLocation")
    @PreAuthorize("hasAnyRole('OTHER_SYSTEM')")
    public ResponseEntity<List<CollectMessage>> getBikeLocation(String bikeBarcodes, Long jurisdiction) {
        List<CollectMessage> resultList = new ArrayList<>();
        List<String> bikeBarcodeList = Arrays.asList(bikeBarcodes.split(","));
        // 车辆编号list
        List<String> bikeList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.LIST_VECHILE_KEY), String.class);
        if (bikeList != null && bikeList.size() > 0) {
            for (String bikeCode : bikeList) {
                String key = RedisKey.VECHILE_KEY + bikeCode;
                Bike bike = JsonUtil.jsonConvertObject(redisUtil.getData(key), Bike.class);
                if (bike != null && jurisdiction.equals(bike.getJurisdiction()) &&
                        bike.getBikeBarcode() != null && bikeBarcodeList.contains(bike.getBikeBarcode())) {
                    String stateJson = redisUtil.getData(VECHILE_STATE + bike.getLockBarcode());
                    CollectMessage collectMessage = JsonUtil.jsonConvertObject(stateJson, CollectMessage.class);
                    resultList.add(collectMessage);
                }
            }
        }
        // 车辆状态存在
        if (resultList.size() > 0) {
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * 历史轨迹信息
     *
     * @param bikeBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("historicalTrack")
    @PreAuthorize("hasAnyRole('OTHER_SYSTEM')")
    public ResponseEntity<RidingTrack> historicalTrack(String bikeBarcode, Long startTime, Long endTime) throws IOException {
        RidingTrack ridingTrack = bikeManagerService.historicalTrack(bikeBarcode, startTime, endTime);
        //根据经纬度获取具体位置
        for(int i=0;i<ridingTrack.getTrajectoryList().size();i++){
            String location=ridingTrack.getTrajectoryList().get(i).get("longitude")+","+ridingTrack.getTrajectoryList().get(i).get("latitude");
            JSONObject jsonObject= doGet("https://restapi.amap.com/v3/geocode/regeo?key=5a3f9625c2a361e7188886b18c6f6d84&location="+location+
                    "&poitype=&radius=&extensions=base&batch=false&roadlevel=0");
            String address=jsonObject.getJSONObject("regeocode").get("formatted_address").toString();
            ridingTrack.getTrajectoryList().get(i).put("address",address);
        }
        return new ResponseEntity<>(ridingTrack, HttpStatus.OK);
    }

    public static JSONObject doGet(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet             httpGet    = new HttpGet(url);
        JSONObject jsonObject=null;
        HttpResponse response= httpClient.execute(httpGet);
        HttpEntity entity=response.getEntity();
        if(entity!=null){
            String result= EntityUtils.toString(entity,"UTF-8");
            jsonObject=JSONObject.parseObject(result);
        }
        return jsonObject;
    }


}

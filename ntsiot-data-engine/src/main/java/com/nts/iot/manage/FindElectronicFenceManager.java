package com.nts.iot.manage;

import com.nts.iot.constant.RedisKey;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询电子围栏
 */
@Component
public class FindElectronicFenceManager {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询指定辖区下所有的电子围栏的点
     *
     * @param jurisdiction 辖区编号
     * @return
     */
    public List<List<Map<String, Object>>> FindElectronicFence(String jurisdiction) {
        List<List<Map<String, Object>>> result = new ArrayList<>();

        // 电子围栏List
        List<String> electronicFenceList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.RESTRICTIONS_FENCE + jurisdiction), String.class);

        // 电子围栏List判空
        if (electronicFenceList != null && electronicFenceList.size() > 0) {
            for (int i = 0; i < electronicFenceList.size(); i++) {
                // 一个围栏所有的点
                String rail = electronicFenceList.get(i);
                if (rail != null && !"".equals(rail)) {
                    // 切分出围栏的坐标点
                    String[] railArray = rail.split(";");

                    // 围栏的坐标点判空
                    if (railArray != null && railArray.length > 0) {
                        List<Map<String, Object>> pointList = new ArrayList<>();
                        // 循环其中一个围栏的坐标点
                        for (int j = 0; j < railArray.length; j++) {
                            String[] array = railArray[j].split(",");
                            Map<String, Object> map = new HashMap<>();
                            // 经度
                            map.put("longitude", array[0]);
                            // 纬度
                            map.put("latitude", array[1]);
                            pointList.add(map);
                        }
                        // 将一个围栏点的list装入返回结果中
                        result.add(pointList);
                    }
                }
            }
        }
        return result;
    }
}

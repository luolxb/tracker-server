package com.nts.iot.util;

import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.Trajectory;
import com.nts.iot.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 添加测试数据
 */
@RestController
@RequestMapping("")
public class AddTestData {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/getDataObj")
    public void getDataObj(@RequestBody String jsonData) {
        List<Record> recordList = JsonUtil.jsonConvertList(jsonData, Record.class);
        List<Trajectory> collectMessageList = new ArrayList<>();
        String date = getDay();
        if (recordList != null && recordList.size() > 0) {
            Record record = recordList.get(0);
            for (int i = 0; i < record.getTrajectories().size(); i++) {
//                CollectMessage collectMessage = new CollectMessage();
                Trajectory trajectory = record.getTrajectories().get(i);
//                collectMessage.setLongitude(trajectory.getLongitude());
//                collectMessage.setLatitude(trajectory.getLatitude());
//                collectMessage.setTime(trajectory.getTime());

                String[] dateArray = trajectory.getTime().split(" ");
                System.out.println(date + " " + dateArray[1]);
                trajectory.setTime(date + " " + dateArray[1]);
                collectMessageList.add(trajectory);
            }
        }
        String key = RedisKey.TRACKER_DEVICE_RECORD_TASK + "2019-07-12-12:C28036152825";

        redisUtil.addRedis(key, JsonUtil.getJson(collectMessageList));
//        System.out.println(recordList.size());
    }


    public String getDay() {
        // 时间日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前日期
        Date today = new Date();
        return sdf.format(today);
    }
}

package com.nts.iot.influxService.influxImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nts.iot.dto.LocateInfo;
import com.nts.iot.influxService.LogsService;
import com.nts.iot.jt808.utils.Constant;
import com.nts.iot.jt808.utils.PositionUtil;
import com.nts.iot.util.InfluxDBTemplate;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LogsServiceImpl implements LogsService {
//    @Autowired
//    InfluxDBTemplate influxDBTemplate;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    /**
     * 通用添加数据点
     *
     * @param deviceNo
     */
    @Override
    public void addPointKeepAlive(String deviceNo) {
        Point point = Point.measurement(Constant.MEASUREMENT_KEEPALIVE)
                .tag(Constant.TAG_DEVICENO,deviceNo)
                .time(DateUtil.current(false), TimeUnit.MILLISECONDS)
                .addField("keepAlivePoint",true)
                .build();
        //influxDBTemplate.insertPoint(point);
    }

    /**
     * 保存GPS数据点
     *
     * @param timemestamp
     * @param deviceNo
     * @param latitude
     * @param longitude
     */
    @Override
    public void addPointForLoction(String timemestamp, String deviceNo, Double latitude, Double longitude) {
        LocateInfo gps =  PositionUtil.gcj02_To_Wgs84(latitude,longitude);
        if ((latitude.doubleValue() != 0.0)&&(longitude.doubleValue() != 0.0)){
            //位置轨迹信息分析
            Point locationPoint = Point.measurement(Constant.MEASUREMENT_LOCATION)
                    .tag(Constant.TAG_DEVICENO,deviceNo)
                    .time(DateUtil.parse(timemestamp).getTime()+ RandomUtil.randomInt(1,999)%1000,TimeUnit.MILLISECONDS)
                    .addField("longitude",gps.getLongitude())
                    .addField("latitude",gps.getLatitude())
                    .build();
            //influxDBTemplate.insertPoint(locationPoint);
        }
    }

    /**
     * 发送一条日志到MQ
     *
     * @param deviceNo 设备编号
     * @param content  日志内容（无结构化）
     */
    @Override
    public void addPointNormalLog(String deviceNo, String content) {
        JSONObject object = new JSONObject();
        object.put("deviceNo",deviceNo);
        object.put("content",deviceNo +"==> "+content);
        try {
            kafkaTemplate.send(Constant.MQ_LOGS_TOPIC, object.toJSONString()).get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存GSM信号质量
     *
     * @param deviceNo    设备编号
     * @param singleValue
     */
    @Override
    public void addPointSignal(String deviceNo, Integer singleValue) {
        Point locationPoint = Point.measurement(Constant.MEASUREMENT_SIGNAL)
                .tag(Constant.TAG_DEVICENO,deviceNo)
                .time(DateUtil.current(false), TimeUnit.MILLISECONDS)
                .addField("singleValue",singleValue)
                .build();
        //influxDBTemplate.insertPoint(locationPoint);
    }

    /**
     * 保存速度
     *
     * @param deviceNo 设备编号
     * @param speed
     */
    @Override
    public void addPointSpeed(String deviceNo, Double speed) {
        Point locationPoint = Point.measurement(Constant.MEASUREMENT_SPEED)
                .tag(Constant.TAG_DEVICENO,deviceNo)
                .time(DateUtil.current(false), TimeUnit.MILLISECONDS)
                .addField("speed",speed)
                .build();
        //influxDBTemplate.insertPoint(locationPoint);
    }

    /**
     * 保存外部电池电量
     *
     * @param deviceNo 设备编号
     * @param votal
     */
    @Override
    public void addPointOutSideVotal(String deviceNo, Double votal) {
        Point locationPoint = Point.measurement(Constant.MEASUREMENT_OUTSIDEVOTAL)
                .tag(Constant.TAG_DEVICENO,deviceNo)
                .time(DateUtil.current(false), TimeUnit.MILLISECONDS)
                .addField("batteryOutSideVoltage",votal)
                .build();
        //influxDBTemplate.insertPoint(locationPoint);
    }

    /**
     * 保存内部电池电量
     *
     * @param deviceNo 设备编号
     * @param votal
     */
    @Override
    public void addPointInSideVotal(String deviceNo, Double votal) {
        Point locationPoint = Point.measurement(Constant.MEASUREMENT_INSIDEVOTAL)
                .tag(Constant.TAG_DEVICENO,deviceNo)
                .time(DateUtil.current(false), TimeUnit.MILLISECONDS)
                .addField("batteryInSideVoltage",votal)
                .build();
        //influxDBTemplate.insertPoint(locationPoint);
    }

    /**
     * 保存锁梁状态
     *
     * @param deviceNo 设备编号
     * @param state
     */
    @Override
    public void addPointLockState(String deviceNo, Boolean state) {
        Point locationPoint = Point.measurement(Constant.MEASUREMENT_LOCKSTATE)
                .tag(Constant.TAG_DEVICENO,deviceNo)
                .time(DateUtil.current(false), TimeUnit.MILLISECONDS)
                .addField("LockState",state)
                .build();
        //influxDBTemplate.insertPoint(locationPoint);
    }

    /**
     * 从MQ里取出，发送到fluxDB
     * @param objString
     */
    @KafkaListener(topics = Constant.MQ_LOGS_TOPIC)
    private void listherLogs(String objString){
        JSONObject object = JSON.parseObject(objString);
        Point point = Point.measurement(Constant.MEASUREMENT_LOG)
                .tag(Constant.TAG_DEVICENO,object.getString("deviceNo"))
                .time(DateUtil.current(false), TimeUnit.MILLISECONDS)
                .addField("content",object.getString("content"))
                .build();
       // influxDBTemplate.insertPoint(point);
    }
}

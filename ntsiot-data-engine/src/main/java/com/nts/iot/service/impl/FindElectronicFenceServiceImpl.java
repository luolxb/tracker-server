package com.nts.iot.service.impl;

import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.OverSpeedAlarmVo;
import com.nts.iot.manage.FindElectronicFenceManager;
import com.nts.iot.service.FindElectronicFenceService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FindElectronicFenceServiceImpl implements FindElectronicFenceService {
    @Autowired
    private FindElectronicFenceManager findElectronicFenceManager;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询指定辖区下所有的电子围栏的点
     *
     * @param jurisdiction 辖区编号
     * @return
     */
    @Override
    public List<List<Map<String, Object>>> FindElectronicFence(String jurisdiction) {
        return findElectronicFenceManager.FindElectronicFence(jurisdiction);
    }


    @Override
    public List<OverSpeedAlarmVo> findOverSpeedAlarmVoList(Long startTime, Long endTime, String deviceNo) {

        List<OverSpeedAlarmVo> overSpeedAlarmDtoList = new ArrayList<OverSpeedAlarmVo>();
        List<OverSpeedAlarmVo> overSpeedAlarmDtoList_ = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.OVERSPEED_ALARM_KEY + deviceNo), OverSpeedAlarmVo.class);
        if (CollectionUtils.isEmpty(overSpeedAlarmDtoList_)) {
            return overSpeedAlarmDtoList;
        }
        for (OverSpeedAlarmVo vo : overSpeedAlarmDtoList_) {
            //时间格式是:YYYY-MM-DD-hh-mm-ss
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置要读取的时间字符串格式
            Date date = null;//转换为Date类
            try {
                date = format.parse(vo.getTrackTime());
                Long timeStamp = date.getTime();
                if (timeStamp > startTime && timeStamp < endTime) {//过滤时间
                    overSpeedAlarmDtoList.add(vo);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return overSpeedAlarmDtoList;
    }
}

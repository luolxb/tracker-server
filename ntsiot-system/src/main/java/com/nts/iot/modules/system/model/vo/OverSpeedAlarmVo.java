package com.nts.iot.modules.system.model.vo;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OverSpeedAlarmVo implements Serializable, Comparable<OverSpeedAlarmVo> {

    private String speed;

    private Double alertSpeed;

    private String trackTime;

    private Double latitude;

    private Double longitude;

    private String address;


    @Override
    public int compareTo(OverSpeedAlarmVo o) {//有序排列
        String time = o.getTrackTime();
        if (time != null && this.getTrackTime() != null) {
            Date after = DateUtil.parse(time, "yyyy-MM-DD HH:mm:ss");
            Date before = DateUtil.parse(this.getTrackTime(), "yyyy-MM-DD HH:mm:ss");
            long stop = before.getTime() - after.getTime();
            if (stop > 0) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

}

package com.nts.iot.modules.miniApp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/27 19:20
 * @Description:
 */
@Data
public class BikeDto implements Serializable {

    /**
     * 车辆条码
     */
    private String bikeBarcode;

    /**
     * 智能锁条码
     */
    private String lockBarcode;

    /**
     * 所属辖区
     */
    private Long jurisdiction;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updater;

    /**
     * 鉴权码
     */
    private String registerNo;


    /**
     * sim卡iccid号
     */
    private String iccid;

    /**
     * 车辆状态
     */
    private Long status;

    /**
     * 骑行人
     */
    private String user;

    /**
     * 骑行人电话
     */
    private String phone;

    private String username;

    /**
     * 车辆类型
     */
    private String type;

    private CollectMessage collectMessage;

    /**
     * 图标
     */
    private String bikeIcon;

    private String bikeCode;

    private String showRealLine;

    private String color;

    /**
     * 排序序号
     */
    private Long bikeSeq;

    /**
     * 1-无需开锁；2-扫码开锁；
     */
    private int unlockMode;


    public enum UnlockMode {
        No_Unlock(1),
        Scan_Unlock(2);

        public final int value;

        UnlockMode(int value) {
            this.value = value;
        }
    }


}

package com.nts.iot.influxService;

public interface LogsService {
    /**
     * 心跳包
     * @param map
     */
    void addPointKeepAlive(String deviceNo);

    /**
     * 保存GPS数据点
     * @param timemestamp
     * @param deviceNo
     * @param latitude
     * @param longitude
     */
    void addPointForLoction(String timemestamp, String deviceNo, Double latitude, Double longitude);

    /**
     * 保存一条普通日志
     * @param deviceNo 设备编号
     * @param content 日志内容（无结构化）
     */
    void addPointNormalLog(String deviceNo,String content);

    /**
     * 保存GSM信号质量
     * @param deviceNo 设备编号
     */
    void addPointSignal(String deviceNo,Integer singleValue);

    /**
     * 保存速度
     * @param deviceNo 设备编号
     */
    void addPointSpeed(String deviceNo,Double speed);
    /**
     * 保存外部电池电量
     * @param deviceNo 设备编号
     */
    void addPointOutSideVotal(String deviceNo,Double votal);
    /**
     * 保存内部电池电量
     * @param deviceNo 设备编号
     */
    void addPointInSideVotal(String deviceNo,Double votal);

    /**
     * 保存锁梁状态
     * @param deviceNo 设备编号
     */
    void addPointLockState(String deviceNo,Boolean state);
}

package com.nts.iot.jt808.protocol;

import com.nts.iot.influxService.LogsService;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 新增106号协议。WiFi/LBS 数据点上报
 */
@Data
@Slf4j
public class JT_0106 implements IMessageBody {

    @Autowired
    private LogsService logsService;
    /**
     * 时间,YY-MM-DD-hh-mm-ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）
     */
    private String time;

    /**
     * 无线信号强度
     */
    private String signalStrength;
    /**
     * 开锁次数
     */
    private String openlockTimes;
    /**
     * 锁的状态
     */
    private String lockerStatus;
    /**
     * 外部电量信息
     */
    private String outSideBatteryVol;
    /**
     * 内部电量信息
     */
    private String inSideBatteryVol;
    /**
     * 设备接入基站时对 应的网关 IP
     */
    private String serverIP;

    /**
     * 接入基站信息
     */
    private String bts;

    /**
     * 周边基站信息(不 含接入基站信息)
     */
    private String nearbts;

    /**
     * wifi 列表中 mac 信 息
     */
    private String macs;



    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        try {
//            buff.put(getResponseMessageSerialNo());
//            buff.put(getResponseMessageId());
//            buff.put(getResponseResult());
        } finally {

        }
        return buff.toByteArrayAndRelease();

    }

    @Override
    public final void readFromBytes(byte[] messageBodyBytes) {
        ByteBuffer buff = new ByteBuffer(messageBodyBytes);

        try {
            setTime("20" + String.format("%02X", buff.getUnsignedByte()) + "-"
                    + String.format("%02X", buff.getUnsignedByte()) + "-"
                    + String.format("%02X", buff.getUnsignedByte()) + " "
                    + String.format("%02X", buff.getUnsignedByte()) + ":"
                    + String.format("%02X", buff.getUnsignedByte()) + ":"
                    + String.format("%02X", buff.getUnsignedByte()));
            setSignalStrength(String.format("%d",buff.getUnsignedByte()));
            setOpenlockTimes(String.format("%d",buff.getShort()));
            setLockerStatus(String.format("%d",buff.getShort()));
            setOutSideBatteryVol(new String(buff.gets(6)));
            setInSideBatteryVol(new String(buff.gets(4)));

            int length = buff.getUnsignedByte();
            if (length>0){
                setServerIP(new String(buff.gets(length)));
            }
            length = buff.getUnsignedByte();
            if (length>0){
                setBts(new String(buff.gets(length)));
            }
            length = buff.getShort();
            if (length > 0){
                setNearbts(new String(buff.gets(length)));
            }
            length = buff.getShort();
            if (length > 0){
                setMacs(new String(buff.gets(length)));
            }
            log.info("解析上报WiFi/LBS :"+this.toString());
        }catch (Exception e){
            logsService.addPointNormalLog("000000","WiFi 日志解析出错");
        }
    }
}

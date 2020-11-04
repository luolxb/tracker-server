/*******************************************************************************
 * @(#)MessageCommand.java 2019-05-01
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.command;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CmdResult;
import com.nts.iot.dto.CollectLock;
import com.nts.iot.dto.Bike;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.influxService.LogsService;
import com.nts.iot.jt808.protocol.*;
import com.nts.iot.jt808.protocol.message.T808Message;
import com.nts.iot.jt808.protocol.message.T808MessageHeader;
import com.nts.iot.jt808.protocol.message.factory.T808MessageFactory;
import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.utils.Tools;
import com.nts.iot.session.SessionManager;
import com.nts.iot.util.RedisUtil;
import com.nts.iot.session.Session;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.nts.iot.constant.RedisKey.HTTP_OPENLOCK;


/**
 * <p>
 * 消息的应答
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-01 21:04
 */
@Component
@Slf4j
public class MessageCommand {

    @Autowired
    private LogsService logsService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static final String LOCALPOSITION_TOPIC = "collect.message.topic";
    private static final String LOCALPOSITION_TOPIC_WIFLLBS = "collect.message.topic.wifi";


    private static final String COLLECT_LOCK_TOPIC = "collect.lock.topic";

    //    @Value("${systemServerUrl}")
//"sapi.ntsiot.com";
    //private static String systemUrl = "115.159.188.112:8000";
    @Value("${systemServerUrl}")
    private String systemUrl;

    private SessionManager manager = SessionManager.INSTANCE;

    @Autowired
    RedisUtil redisUtil;

    // 鉴权码
    private static String registerNoTest = "ab1400";


    /**
     * 根据请求的消息，返回应答的消息
     *
     * @param message
     * @return
     */
    public T808Message command(T808Message message) {
        // 根据消息类型返回数据包

        String simNo = message.getSimNo();
        T808Message response = new T808Message();
        response.setHeader(new T808MessageHeader());
        response.getHeader().setSimId(simNo);
        response.getHeader().setIsPackage(false);

        // 消息类型
        int type = message.getMessageType();
        if (type == 0) {
            return null;
        }
        T808MessageHeader header = message.getHeader();
        String messageJson = JSON.toJSONString(message.getMessageContents());
        /* 设备编号 */
        String deviceNo = header.getSimId();
        log.info("消息设备ID " + deviceNo);
        logsService.addPointNormalLog(deviceNo, "原始数据: " + message.getPacketDescr());
        //  0x0100 注册信息包（ 3次鉴权失败 或者 更改IP地址会重新注册 ）
        if (T808Message.REQUEST_REGISTER.equals(type)) {
            /* 注册结果,0：成功；1：车辆已被注册；2：数据库中无该车辆；3：终端已被注册；4：数据库中无该终端 */
            int result = 0;
            // 获取 消息体
            JT_0100 jt0100 = (JT_0100) message.getMessageContents();
            // 车辆标识 iccid / ble
            String plateNo = jt0100.getPlateNo();
            log.info("注册信息包：" + deviceNo);
            logsService.addPointNormalLog(deviceNo, "注册信息包");

            // 生成鉴权码，算法（随机数 然后 md5 一下， 取前6位）
            /* 鉴权码,只有在成功后才有该字段 */
            // 生成6位随机数字
            int radomNumber = RandomUtil.randomInt(0, 999999);
            // MD5 加密
            String md5Hex1 = DigestUtil.md5Hex(String.valueOf(radomNumber));
            String registerNo = md5Hex1.substring(0, 6);

            CollectLock lock = new CollectLock();
            lock.setLockNo(header.getSimId());
            lock.setSimId(jt0100.getPlateNo());
            lock.setMac(jt0100.getTerminalId());
            lock.setDeviceNo(message.getSimNo());

            try {
                //kafkaTemplate.send(COLLECT_LOCK_TOPIC, JSON.toJSONString(lock)).get(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.info("网关发送注册消息（0x0100）到kafka报错:" + e.getMessage());
                e.printStackTrace();
            }
            // 2 注册应答8100并发鉴权码
            JT_8100 echoData = new JT_8100();
            echoData.setRegisterNo(registerNoTest);
            echoData.setRegisterResponseMessageSerialNo(message
                    .getHeader().getMessageSerialNo());
            echoData.setRegisterResponseResult((byte) result);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8100);
        }
        //  0x0109 时间同步
        else if (T808Message.REQUEST_TIME_SYN.equals(type)) {
            JT_8109 timeData = new JT_8109();
            timeData.setTimeString(DateUtil.now());
            response.setMessageContents(timeData);
            response.getHeader().setMessageType(0x8109);
        }
        //  0x0102 终端授权
        else if (T808Message.REQUEST_AUTHENTIFICATION.equals(type)) {
            // 应答结果
            int result = 0;
            // 获取 消息体
            JT_0102 jt_0102 = (JT_0102) message.getMessageContents();
            // 获取鉴权码
            String registerNo = jt_0102.getRegisterNo();

            // TODO TEST
            if (registerNo.equals(registerNoTest)) {
                // 比对鉴权码，如果相同则返回应答结果0
                result = 0;
            }
            log.info("终端鉴权：" + message.getHeader().getMessageSerialNo());
            logsService.addPointNormalLog(deviceNo, " 设备鉴权");

            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) result);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);
        }
        //  0x0200 心跳
        else if (T808Message.REQUEST_HEARTBEAT.equals(type)) {
            /* T808Message */
            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo((short) 0);
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) 0);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);
            // 将设备心跳数据放入到redis中 过期时间为300秒
            Boolean aBoolean = redisUtil.addRedis(RedisKey.TRACKER_HEARTBEAT_KEY + message.getSimNo(), message.getSimNo(), 600);
            log.info("将设备心跳数据放入到redis中 过期时间为300秒==>{}", aBoolean);
//            boolean b = redisUtil.hasKey(RedisKey.DEVICE_HEARTBEAT_KEY + message.getSimNo());
//            if (!b) {
//                redisUtil.addRedis(RedisKey.DEVICE_HEARTBEAT_KEY + message.getSimNo(),message.getSimNo(),600);
//            }

            JT_0002 jt0002 = new JT_0002();
            log.info("心跳: " + message.getSimNo());
            logsService.addPointKeepAlive(message.getSimNo());
        }
        //  0x0001 终端通用应答
        else if (T808Message.COMMON_RESPONSE.equals(type)) {

            // 应答结果
            int result = 0;
            // 获取 消息体
            JT_0001 jt0001 = (JT_0001) message.getMessageContents();
            // 应答消息流水号(用作判断应答状态)
            short msgSn = jt0001.getResponseMessageSerialNo();

            String responseStatus = String.format("%04x", msgSn);

            // 应答消息ID
            short responseId = jt0001.getResponseMessageId();

            // 应答结果
            byte responseResult = jt0001.getResponseResult();

            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            //  收到应答后通知 controller

            if (responseId == (short) 0x8500) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("应答时间：" + simpleDateFormat.format(new Date()));
                System.out.println("responseStatus：" + responseStatus);

            } else if (responseId == (short) 0x0001 && msgSn == (short) 0x55aa) {
                // 平台通用应答
                System.out.println("================最后一次发送确认开锁================");
                echoData.setResponseMessageSerialNo((short) 0x55aa);
                echoData.setResponseMessageId((short) 0x0001);
                echoData.setResponseResult((byte) 0);
            }
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);

        }
        //  0x9100 自定义反控请求 START
        else if (T808Message.REQUEST_COUNTERCONTROL.equals(type)) {
            try {
                // 获取 消息体
                JT_9100 jt_9100 = (JT_9100) message.getMessageContents();
                int msgId = jt_9100.getMsgId();
                String msgCmd = jt_9100.getMsgCmd();

                // 设置返回deviceNo
                response.getHeader().setSimId(jt_9100.getDeviceNo());

                //  解析请求类型
                /*  查询参数请求 */
                if (T808Message.REQUEST_SEARCH_PARAM.equals(msgId)) {
                    //  查询参数请求
                    JT_8104 echoData = new JT_8104();
                    // TODO 不知道此处逻辑是否正确?
                    byte[] cmdBytes = BitConverter.getBytes(msgCmd);
                    ArrayList<Integer> ids = new ArrayList<>();
                    for (byte c : cmdBytes) {
                        ids.add((int) c);
                    }
                    echoData.setParametersCount((byte) cmdBytes.length);
                    echoData.setParametersIDs(ids);

                    /* T808Message */
                    response.setMessageContents(echoData);
                    response.getHeader().setMessageType(msgId);
                }
                /*  TODO 查询属性请求 */
                if (T808Message.REQUEST_SEARCH_ATTRIBUTE.equals(msgId)) {


                }
                /*   控制终端请求 */
                if (T808Message.REQUEST_APP_CONTROL.equals(msgId)) {
                    //  查询属性请求
                    JT_8500 echoData = new JT_8500();
                    byte[] cmdBytes = msgCmd.getBytes();
                    // TODO 取第一个?
                    echoData.setFlag(cmdBytes[0]);
                    /* T808Message */
                    response.setMessageContents(echoData);
                    response.getHeader().setMessageType(msgId);
                }
                /*  TODO 修改参数请求 */
                if (T808Message.REQUEST_EDIT_PARAM.equals(msgId)) {

                }
                // 发送成功
            } catch (Exception e) {
                e.printStackTrace();
                // 发送失败
                // 平台通用应答
                JT_8001 echoData = new JT_8001();
                echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
                echoData.setResponseMessageId((short) type);
                echoData.setResponseResult((byte) 1);
                /* T808Message */
                response.setMessageContents(echoData);
                response.getHeader().setMessageType(0x8001);
            }
        }
        //  0x9100 自定义反控请求 END

        //  0x0104 查询参数应答
        else if (T808Message.RESPONSE_SEARCH_PARAM.equals(type)) {

            // TODO 收到应答后通知controller
            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) 0);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);

            JT_0104 jt_0104 = (JT_0104) message.getMessageContents();
            log.info("设备参数上报: " + jt_0104.toString());
            logsService.addPointNormalLog(deviceNo, "设备参数上报: " + jt_0104.toString());

            report0104(message.getSimNo(), jt_0104);
        }
        //  0x0103 修改参数应答
        else if (T808Message.RESPONSE_EDIT_PARAM.equals(type)) {
            log.info("修改参数应答");
            logsService.addPointNormalLog(deviceNo, "修改参数应答");

            // TODO 收到应答后通知controller

            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) 0);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);
        }
        //  0x0107 查询属性应答
        else if (T808Message.RESPONSE_SEARCH_ATTRIBUTE.equals(type)) {
            // 应答结果
            int result = 0;
            // 获取 消息体
            JT_0107 jt_0107 = (JT_0107) message.getMessageContents();
            // TODO 收到应答后通知controller
            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) 0);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);
            log.info("设备属性上报: " + echoData.toString());
            logsService.addPointNormalLog(deviceNo, "设备属性上报: " + echoData.toString());

            //发送给业务服务器
            report0107(message.getSimNo(), jt_0107);

        }
        //升级结果
        else if (T808Message.RESPONSE_UPGRADE_RESULT.equals(type)) {
            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) 0);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);

            JT_0108 jt_0108 = (JT_0108) message.getMessageContents();
            log.info("升级结果上报: " + jt_0108.toString());
            logsService.addPointNormalLog(deviceNo, "升级结果上报:: " + jt_0108.toString());

            reportOTA(message.getSimNo(), jt_0108);
        }
        //  0x0500 控制终端应答
        else if (T808Message.RESPONSE_APP_CONTROL.equals(type)) {
            System.out.println("***********************  控制应答 **************************");
            log.info("收到控制应答包0500,systemUrl=" + systemUrl);
            // 应答结果
            int result = 0;
            // 获取 消息体
            JT_0500 jt_0500 = (JT_0500) message.getMessageContents();
            // 位置信息
            JT_0200 positionReport = jt_0500.getPositionReport();
            System.out.println(jt_0500.toString());
            System.out.println("***********************  控制应答 **************************");
            //  收到应答后通知controller
            if (!deviceNo.startsWith("c3")) {//不以c3开头暂定为思科尔特设备
                String packetDescr = message.getPacketDescr();
                packetDescr = packetDescr.substring(27, 30);
                System.out.println("***********************  packetDescr **************************" + packetDescr);
                if ("979".equals(packetDescr)) {//通电
                    /*String flagStr = StringUtil.leftPad(positionReport.getStrStatus(), 32, '0');
                    boolean flag = flagStr.substring(31, 32).equals("1");
                    if(flag){//通电成功*/
                    requestSystem("8500", true, "0100", deviceNo);
                    /*}else {//通电失败
                        requestSystem("8500",false,"0100",deviceNo);
                    }*/
                } else {
                    /*String flagStr = StringUtil.leftPad(positionReport.getStrStatus(), 32, '0');
                    boolean flag = flagStr.substring(31, 32).equals("0");
                    if(flag){//断电成功*/
                    requestSystem("8500", true, "0101", deviceNo);
                    /*}else {//断电失败
                        requestSystem("8500",false,"0101",deviceNo);
                    }*/
                }
            } else {

                if (positionReport != null) {
                    /* 锁状态上报处理 */
                    PositionAdditional_LockState lockState = positionReport.getLockState();
                    if (lockState != null) {
                        lockStateHandle(lockState, deviceNo);
                    } else {
                        log.error("锁状态上报处理: lockState信息为空！");
                    }
                } else {
                    // 失败
                    log.error("0x0500 控制终端应答未包含位置信息！ ");
                }
            }

            log.info("锁梁结果上报: " + jt_0500.toString());
            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(jt_0500.getResponseMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) result);

            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);
        }
        // 0x0200 位置信息汇报
        else if (T808Message.REQUEST_LOCATION.equals(type)) {
            deviceNo = message.getSimNo();
            System.out.println("￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥  ※ 编号：" + deviceNo + "位置信息汇报 ※ ￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
            JT_0200 jt0200 = (JT_0200) message.getMessageContents();

            locationInfoHandle(deviceNo, jt0200);
            int result = 0;
            // 平台通用应答
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            echoData.setResponseResult((byte) result);
            /* T808Message */
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);
        } else if (T808Message.REQUEST_MULTI_LOCATIONS.equals(type)) {
            JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(message.getHeader().getMessageSerialNo());
            echoData.setResponseMessageId((short) type);
            System.out.println("￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥  ※ 编号：" + deviceNo + "批量位置信息汇报 ※ ￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
            //如果是是分包形式，不解析而先进行分包缓存处理
            if (message.getHeader().getIsPackage()) {//如果是分包模式，先将各分包放置redis缓存
                String key = message.getHeader().getMessageSerialNo() + "";
                if (redisUtil.hasKey(key)) {
                    if (message.getHeader().getMessageTotalPacketsCount() != message.getHeader().getMessagePacketNo()) {
                        //如果不是最后一个分包，则先把数据缓存到redis依次把分包累加，时间有效期位1000秒，
                        //注意，硬件那边等待对包传完整回复，最大等待时间也是1000秒，
                        //如果硬件在1000秒内，还没有收到确认回复，则需要重新零开始重传，
                        // 同时redis这边有效期是1000秒，所以也相应的清空了，这样两端一致
                        String bufStr = Tools.ToHexFormatString(message.getChildPacket());
                        String value = redisUtil.getData(key) + bufStr;
                        redisUtil.addRedis(message.getHeader().getMessageSerialNo() + "", value, 1000);
                        echoData.getResponseResult();
                        echoData.setResponseMessageId((short) type);
                    } else {
                        //如果是最后一个分包，则开始直接解析
                        String bufStr = Tools.ToHexFormatString(message.getChildPacket());
                        String value = redisUtil.getData(key) + bufStr;
                        byte[] messageContents = Tools.HexString2Bytes(value);//完全的报文
                        JT_0202 jt_0202 = (JT_0202) T808MessageFactory.create(header.getMessageType(), messageContents);
                        LinkedList<JT_0200> list = jt_0202.getJt_0200List();
                        for (JT_0200 jt_0200 : list) {
                            locationInfoHandle(deviceNo, jt_0200);
                        }
                    }
                } else {
                    //如果redis里没有找到流水号
                    String bufStr = Tools.ToHexFormatString(message.getChildPacket());
                    redisUtil.addRedis(message.getHeader().getMessageSerialNo() + "", bufStr);
                }
            } else {//如果不是分包模式
                deviceNo = message.getSimNo();
                JT_0202 jt0202 = (JT_0202) message.getMessageContents();
                //JT_0202 jt_0202 = (JT_0202)T808MessageFactory.create(header.getMessageType(), messageContents);
                LinkedList<JT_0200> list = jt0202.getJt_0200List();
                for (JT_0200 jt_0200 : list) {
                    locationInfoHandle(deviceNo, jt_0200);
                }
            }
            // 平台通用应答
            int result = 0;
            echoData.setResponseResult((byte) result);
            /* T808Message */
            //noted: 客户端怎么区分分包是否传成功，客户端需要在回复包里的包头里，查看有没有分包总数和分包序号，如果有则代表分包成功，然后传下一个
            response.setMessageContents(echoData);
            response.getHeader().setMessageType(0x8001);
        } else if (T808Message.REQUEST_LOCATION_WiFi.equals(type)) {
            String messageStr = JSON.toJSONString(message);
            log.info("WiFi基站信息上报:  " + messageStr);
            logsService.addPointNormalLog(deviceNo, "WiFi原始数据:" + messageStr);
            try {
                kafkaTemplate.send(LOCALPOSITION_TOPIC_WIFLLBS, messageStr).get(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.info("网关发送定位信息上报（0x0106）到kafka报错:" + e.getMessage());
                e.printStackTrace();
            }
        }

        byte[] sendByte = response.writeToBytes();
        String pResponse =
                String.format("发送到 deviceNo：%1$s , type：%2$s  的数据包",
                        response.getHeader().getSimId(), String.format("%04x", response.getHeader().getMessageType()));
        log.info("" + pResponse);
        logsService.addPointNormalLog(deviceNo, "通用应答：  " + pResponse);

        return response;
    }

    private void locationInfoHandle(final String deviceNo, final JT_0200 jt0200) {
        /* 锁状态上报处理 */
        PositionAdditional_LockState lockState = jt0200.getLockState();
        if (lockState != null) {
            lockStateHandle(lockState, deviceNo);
        } else {
            log.info("锁状态上报处理: lockState信息为空");
        }
        // 将协议的内容转换为一个对象
        CollectMessage msg = new CollectMessage(deviceNo, jt0200);
        // 将消息对象放入消息队列中
        String messageStr = JSON.toJSONString(msg);
        log.info("0200位置信息：  " + messageStr);
        logsService.addPointNormalLog(deviceNo, "位置信息：  " + messageStr);
        try {
            kafkaTemplate.send(LOCALPOSITION_TOPIC, messageStr).get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("网关发送位置消息（0x0200）到kafka报错:" + e.getMessage());
            e.printStackTrace();
        }
        logsService.addPointForLoction(msg.getTime(), deviceNo, Double.valueOf(msg.getLatitude()), Double.valueOf(msg.getLongitude()));
        logsService.addPointSpeed(deviceNo, Double.valueOf(msg.getSpeed()));
        logsService.addPointOutSideVotal(deviceNo, Double.valueOf((msg.getOutCellPower()=="" || msg.getOutCellPower()== null) ? "0":msg.getOutCellPower()));
        logsService.addPointInSideVotal(deviceNo, Double.valueOf((msg.getCellPower()=="" || msg.getCellPower()== null) ? "0":msg.getCellPower()));
        logsService.addPointSignal(deviceNo, Integer.valueOf((msg.getSimSignal()=="" || msg.getSimSignal()== null) ? "0":msg.getSimSignal()));
        logsService.addPointLockState(deviceNo, Boolean.valueOf(msg.getBeamState()));

        // 将设备数据放入到redis中算作心跳，设备在线 过期时间为600秒
        Boolean aBoolean = redisUtil.addRedis(RedisKey.TRACKER_HEARTBEAT_KEY + deviceNo, deviceNo, 600);
        log.info("将设备数据放入到redis中算作心跳，设备在线 过期时间为600秒==>{}", aBoolean);

    }

    // 锁状态变化处理
    private void lockStateHandle(PositionAdditional_LockState lockState, String deviceNo) {
        // 1： 锁梁开启，0 锁梁闭合
        Boolean isLock = lockState.getLock().equals("0");
        // 0001 表示开锁上传
        if ("0001".equals(lockState.getPoiupState())) {
            if (isLock) {
                // 开锁上传 且 锁梁闭合为开锁失败
                log.info("***********************  编号：" + deviceNo + "开锁上传 且 锁梁闭合为开锁失败 **************************");
                logsService.addPointNormalLog(deviceNo, "开锁上传 且 锁梁闭合为开锁失败");

                //临时http开锁判断
                String checkOpen = redisUtil.getData(HTTP_OPENLOCK + deviceNo);
                if (null != checkOpen && !"".equals(checkOpen)) {
                    redisUtil.updateRedis(HTTP_OPENLOCK + deviceNo, "false");
                } else {
                    redisUtil.addRedis(HTTP_OPENLOCK + deviceNo, "false");
                }
                requestSystem("8500", false, "0100", deviceNo);
            } else {
                // 开锁上传 且 锁梁开启为开锁失败
                log.info("***********************  编号：" + deviceNo + "开锁上传 且 锁梁开启为开锁成功 **************************");
                logsService.addPointNormalLog(deviceNo, "开锁上传 且 锁梁开启为开锁失败");
                requestSystem("8500", true, "0100", deviceNo);
            }
        }
        // 0010 表示关锁上传
        if ("0010".equals(lockState.getPoiupState())) {
            if (isLock) {
                // 关锁上传 且 锁梁闭合为关锁成功
                log.info("***********************  编号：" + deviceNo + "关锁上传 且 锁梁闭合为关锁成功 **************************");
                logsService.addPointNormalLog(deviceNo, "关锁上传 且 锁梁闭合为关锁成功");
                requestSystem("8500", true, "0101", deviceNo);
            } else {
                // 关锁上传 且 锁梁开启为关锁失败
                log.info("***********************  编号：" + deviceNo + "关锁上传 且 锁梁开启为关锁失败 **************************");
                logsService.addPointNormalLog(deviceNo, "关锁上传 且 锁梁开启为关锁失败");
                requestSystem("8500", false, "0101", deviceNo);
            }
        }
        // 0011 表示低电上传
        if ("0011".equals(lockState.getPoiupState())) {
            logsService.addPointNormalLog(deviceNo, "低电上传");
            log.info("***********************  编号：" + deviceNo + "低电上传 **************************");
        }
        // 0100 表示移动上传
        if ("0100".equals(lockState.getPoiupState())) {
            logsService.addPointNormalLog(deviceNo, "移动上传");
            log.info("***********************  编号：" + deviceNo + "移动上传 **************************");
        }
        // 0101 表示撞击或者震动上传
        if ("0101".equals(lockState.getPoiupState())) {
            logsService.addPointNormalLog(deviceNo, "撞击或者震动上传");
            log.info("***********************  编号：" + deviceNo + "撞击或者震动上传 **************************");
        }
    }

    private void requestSystem(String message, Boolean isSuccess, String type, String deviceNo) {
        CmdResult cmdResult = new CmdResult();
        cmdResult.setDeviceNo(deviceNo);
        cmdResult.setMessage(message);
        // 暂定反控
        cmdResult.setType(type);
        // 0：成功，1：失败
        if (isSuccess) {
            cmdResult.setResult("0");
        } else {
            cmdResult.setResult("1");
        }
        String result = HttpRequest.post(systemUrl + "/maSocket/sendToUser")
                .body(JSON.toJSONString(cmdResult))
                .execute().body();
        log.info("控制车锁 提交业务服务器:cmd{} 结果 {}", JSON.toJSONString(cmdResult), result);
        logsService.addPointNormalLog(deviceNo, "控制车锁 提交业务服务器: " + JSON.toJSONString(cmdResult));
    }


    /**
     * 根据设备NO 查询BIKE
     *
     * @param deviceNo
     * @return
     */
    private Bike getBikeFromRedis(String deviceNo) {
        //  判断系统是否有这个信息（deviceNo）为key 查询
        Bike bike = null;
        String deviceInfo = redisUtil.getData(RedisKey.TRACKER_KEY + deviceNo);
        if (deviceInfo != null) {
            try {
                bike = JSON.parseObject(deviceInfo, new TypeReference<Bike>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bike;
    }


    public void sendMessage(String number, T808Message message) {
        try {
            log.info("sendMessage网关发送消息，number=" + number + "," + JSON.toJSONString(message));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("发送时间：" + simpleDateFormat.format(new Date()));
            Session session = manager.getByMobileNumber(number);
            if (session == null || session.getChannel() == null) {
                System.out.println("无法建立长连接！");
                log.info("无法建立长连接," + number);
            } else {
                byte[] datas = message.writeToBytes();
                final String str = Tools.ToHexString(datas);
                System.out.println("===========================>sendMessage: ");
                Channel channel = session.getChannel();
                System.out.println("isActive : " + channel.isActive());
                System.out.println("isWritable : " + channel.isWritable());
                System.out.println("isOpen : " + channel.isOpen());
                System.out.println("isRegistered : " + channel.isRegistered());
                ChannelFuture cf = channel.writeAndFlush(Unpooled.copiedBuffer(datas)).sync();
                //添加ChannelFutureListener以便在写操作完成后接收通知
                cf.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        //写操作完成，并没有错误发生
                        if (future.isSuccess()) {
                            log.info("下发指令：" + str + " 发送成功！");
                            logsService.addPointNormalLog(number, "下发指令：" + str + " 发送成功！");
                        } else {
                            //记录错误
                            log.info("error");
                            future.cause().printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(message);
        }
    }

    public void sendUpgradeMessage(String number, String urlString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("发送时间：" + simpleDateFormat.format(new Date()));
            Session session = manager.getByMobileNumber(number);
            if (session == null || session.getChannel() == null) {
                System.out.println("无法建立长连接！");
            } else {
                System.out.println("===========================>sendMessage: ");
                Channel channel = session.getChannel();
                System.out.println("isActive : " + channel.isActive());
                System.out.println("isWritable : " + channel.isWritable());
                System.out.println("isOpen : " + channel.isOpen());
                System.out.println("isRegistered : " + channel.isRegistered());
                ChannelFuture cf = channel.writeAndFlush(Unpooled.copiedBuffer(urlString.getBytes())).sync();
                //添加ChannelFutureListener以便在写操作完成后接收通知
                cf.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        //写操作完成，并没有错误发生
                        if (future.isSuccess()) {
                            System.out.println("下发指令：" + number + "===>" + urlString + " 发送成功！");
                        } else {
                            //记录错误
                            System.out.println("error");
                            future.cause().printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    private void closeOrder(String deviceNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("lockBarcode", deviceNo);
        String result = HttpRequest.post(systemUrl + "/ma/closeOrderByDeviceNo")
                .body(JSON.toJSONString(param))
                .execute().body();
        log.info("关闭订单 提交业务服务器" + deviceNo);
    }

    /**
     * 终端参数应答 提交业务服务器
     *
     * @param deviceNo
     * @param object
     */
    private void report0107(String deviceNo, JT_0107 object) {
        Map<String, Object> param = new HashMap<>();
        param.put("devcieNo", deviceNo);
        param.put("object", object);
        String result = HttpRequest.post(systemUrl + "/ma/cmd107")
                .body(JSON.toJSONString(param))
                .execute().body();
        logsService.addPointNormalLog(deviceNo, "终端参数应答 提交业务服务器: {} 结果 {}" + JSON.toJSONString(param) + result);
        log.info("终端参数应答 提交业务服务器: {} 结果 {}", JSON.toJSONString(param), result);
    }

    /**
     * 终端参数上报业务服务器
     *
     * @param deviceNo 设备NO
     * @param jt_0104
     */
    private void report0104(String deviceNo, JT_0104 jt_0104) {
        Map<String, Object> param = new HashMap<>();
        param.put("devcieNo", deviceNo);
        param.put("object", jt_0104);
        String result = HttpRequest.post(systemUrl + "/ma/cmd104")
                .body(JSON.toJSONString(param))
                .execute().body();
        logsService.addPointNormalLog(deviceNo, "终端参数 提交业务服务器:{} 结果 {}" + JSON.toJSONString(param) + result);
        log.info("终端参数 提交业务服务器:{} 结果 {}", JSON.toJSONString(param), result);
    }

    /**
     * 设备OTA升级结果上报
     *
     * @param deviceNo
     * @param jt_0108
     */
    private void reportOTA(String deviceNo, JT_0108 jt_0108) {
        System.out.println("=============================设备OTA升级结果上报===========================");
        Map<String, Object> param = new HashMap<>();
        param.put("deviceNo", deviceNo);
        param.put("object", jt_0108);
        String result = HttpRequest.post(systemUrl + "/ma/cmd108")
                .body(JSON.toJSONString(param))
                .execute().body();
        log.info("升级结果 提交业务服务器: {} 结果: {}" + JSON.toJSONString(param), result);
        String url2 = "http://115.159.71.171:8181/notify";
        Map<String, Object> param2 = new HashMap<>();
        param2.put("deviceNo", deviceNo);
        param2.put("object", jt_0108);
        String result2 = HttpRequest.post(url2)
                .body(JSON.toJSONString(param))
                .execute().body();
        log.info("升级结果 提交业务服务器: {} 结果: {}" + JSON.toJSONString(param2), result2);
        logsService.addPointNormalLog(deviceNo, "升级结果 提交业务服务器: {} 结果: {}" + JSON.toJSONString(param) + result);
    }

    /**
     * 测试提交给高德地图
     *
     * @param messageJson
     */
    @KafkaListener(topics = LOCALPOSITION_TOPIC_WIFLLBS)
    private void listherWiFiLBS(String messageJson) {
        if (messageJson == null) return;
        JSONObject object = JSON.parseObject(messageJson);
        try {
            String Url = "http://apilocate.amap.com/position?accesstype=0&cdma=0"
                    + "&bts=" + object.getJSONObject("messageContents").getString("bts")
                    + "&macs=" + object.getJSONObject("messageContents").getString("macs")
                    + "&nearbts=" + object.getJSONObject("messageContents").getString("nearbts")
                    + "&output=json&key=22626ec48d4e5fc418b58349f677c4b2";
            String result = HttpRequest.get(Url)
                    .contentType("application/json")
                    .execute().body();
            JSONObject resultObj = JSON.parseObject(result);

            if (resultObj.getString("info").equals("OK")) {
                JSONObject locObj = resultObj.getJSONObject("result");
                if (locObj == null) {
                    log.info("无法从高德地图换取经伟度信息");
                    return;
                }
                String[] split = locObj.getString("location").split(",");

                CollectMessage collectMessage = new CollectMessage();
                collectMessage.setTime(object.getJSONObject("messageContents").getString("time"));
                collectMessage.setSimSignal(object.getJSONObject("messageContents").getString("signalStrength"));
                collectMessage.setAltitude("0");
                collectMessage.setBeamState(object.getJSONObject("messageContents").getString("lockerStatus"));
//                collectMessage.setCellPower(object.getJSONObject("messageContents").getString("inSideBatteryVol"));
//                collectMessage.setCellVoltage(object.getJSONObject("messageContents").getString("inSideBatteryVol"));
//                collectMessage.setOutCellPower(object.getJSONObject("messageContents").getString("outSideBatteryVol"));
//                collectMessage.setOutCellVoltage(object.getJSONObject("messageContents").getString("outSideBatteryVol"));
                collectMessage.setCellPower("100");
                collectMessage.setCellVoltage("10");
                collectMessage.setOutCellPower("100");
                collectMessage.setOutCellVoltage("100");
                collectMessage.setCourse("6117");
                collectMessage.setDeviceNo(object.getString("simNo"));
                collectMessage.setLatitude(split[1]);
                collectMessage.setLongitude(split[0]);
                collectMessage.setPoiupState("0000");
                collectMessage.setSpeed("15");
                collectMessage.setSatellites("0");
                collectMessage.setStatus("0");
                logsService.addPointNormalLog(object.getString("simNo"), "WiFi定位解析结果" + result);
                logsService.addPointForLoction(collectMessage.getTime(), collectMessage.getDeviceNo(),
                        Double.valueOf(collectMessage.getLatitude()), Double.valueOf(collectMessage.getLongitude()));
                log.info("WiFi定位解析结果: " + result);
                try {
                    log.info("WiFi定位发送到业务服务器:" + JSON.toJSONString(collectMessage));
                    kafkaTemplate.send(LOCALPOSITION_TOPIC, JSON.toJSONString(collectMessage)).get(2, TimeUnit.SECONDS);
                } catch (Exception e) {
                    log.info("网关listherWiFiLBS发送消息到kafka报错:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.info("WiFi  解析出错");
        }

    }
}

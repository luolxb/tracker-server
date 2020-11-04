package com.nts.iot.rest;

import com.alibaba.fastjson.JSON;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.dto.CmdPackageEx;
import com.nts.iot.dto.DeviceAttribute;
import com.nts.iot.dto.OtaPackage;
import com.nts.iot.util.SpringContextHolder;
import com.nts.iot.command.MessageCommand;
import com.nts.iot.influxService.LogsService;
import com.nts.iot.jt808.protocol.JT_8103;
import com.nts.iot.jt808.protocol.JT_8108;
import com.nts.iot.jt808.protocol.JT_8500;
import com.nts.iot.jt808.protocol.ParameterItem;
import com.nts.iot.jt808.protocol.constant.MiniAppConstantsEx;
import com.nts.iot.jt808.protocol.message.T808Message;
import com.nts.iot.jt808.protocol.message.T808MessageHeader;
import com.nts.iot.session.Session;
import com.nts.iot.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-05-02 21:40
 */
@Slf4j
@RestController
@RequestMapping("api")
public class ApiController {
    private SessionManager manager = SessionManager.INSTANCE;

    @Autowired
    private LogsService logsService;

    @PostMapping("counterControl")
    public Map<String, Object> counterControl(@RequestBody CmdPackageEx messageFromSys) {
        Map<String, Object> result = new HashMap<>();

        log.info("业务服务器: " + JSON.toJSONString(messageFromSys));

        // 消息头
        String header = messageFromSys.getHeader();
        // 设备编号
        String deviceNo = messageFromSys.getDeviceNo();
        logsService.addPointNormalLog(deviceNo,"业务服务器指令: "+JSON.toJSONString(messageFromSys));

        // 消息内容（开锁、关锁等）
        String type = messageFromSys.getType();

        if (deviceNo == null || type == null) {
            return null;
        }
        // TODO 判断不在线情况
        Session session = manager.getByMobileNumber(deviceNo);
        if (session == null) {
            result.put("code", 400);
            result.put("message", "设备不在线");
            log.info("设备不在线," + JSON.toJSONString(messageFromSys));
            return result;
        }
        T808Message message = new T808Message();
        message.setHeader(new T808MessageHeader());
        message.getHeader().setSimId(deviceNo);
        message.getHeader().setIsPackage(false);
        message.getHeader().setMessageType(0x8500);

        JT_8500 echoData = new JT_8500();

        if (MiniAppConstants.CONTENT_CONTROL_OPEN_LOCK.equals(type)) {
            if (!deviceNo.startsWith("c3")) {//通电，不以c3开头暂定为思科尔特设备
                message.getHeader().setMessageSerialNo((short) 0x0979);
                echoData.setFlag((byte) 0b00100000);
                echoData.setPrivateCmd((byte) 0);
            } else {
                // 0100 远程开锁
                message.getHeader().setMessageSerialNo((short) 0x0100);
                //  控制门 第0位是1
                echoData.setFlag((byte) 0b00000001);
                echoData.setPrivateCmd((byte) 0);
                echoData.setTimestamp(Integer.valueOf(messageFromSys.getTimestamp()));
            }
        } else if (MiniAppConstants.CONTENT_CONTROL_CLOSE_LOCK.equals(type)) {
            if (!deviceNo.startsWith("c3")) {//断电，不以c3开头暂定为思科尔特设备
                message.getHeader().setMessageSerialNo((short) 0x097c);
                echoData.setFlag((byte) 0b00010000);
                echoData.setPrivateCmd((byte) 0);
            } else {
                // ※ 用消息流水号
                // 0101 远程关锁应答
                message.getHeader().setMessageSerialNo((short) 0x0101);
                //  控制门 第0位是1
                echoData.setFlag((byte) 0b00000001);
                echoData.setPrivateCmd((byte) 1);
                echoData.setTimestamp(Integer.valueOf(messageFromSys.getTimestamp()));
            }
        } else if (MiniAppConstants.CONTENT_CONTROL_OPEN_BUZZER.equals(type)) {
            //  控制铃声 第1位是1
            message.getHeader().setMessageSerialNo((short) 0x0201);
            echoData.setFlag((byte) 0b00000010);
            echoData.setPrivateCmd((byte) 1);
        } else if (MiniAppConstants.CONTENT_CONTROL_CLOSE_BUZZER.equals(type)) {
            //  控制铃声 第1位是1
            message.getHeader().setMessageSerialNo((short) 0x0200);
            echoData.setFlag((byte) 0b00000010);
            echoData.setPrivateCmd((byte) 0);
        } else if (MiniAppConstantsEx.CONTENT_CONTROL_OTA_CONFIG.equals(type)) {
            // todo 设置终端OTA升级配置 测试用，正式环境不用
            OtaPackage otaPackage = new OtaPackage();
            List<String> list = new ArrayList<>();
            list.add(deviceNo);
            otaPackage.setDeviceNos(list);
            otaPackage.setPort(80);
            otaPackage.setSw_version("v1.1.1");
            otaPackage.setType(0);
            otaPackage.setUrl("http://119.23.201.94/60.bin");
            return Upgrade(otaPackage);

        } else if (MiniAppConstantsEx.CONTENT_CONTROL_CHECK.equals(type)) {
            // todo 查询终端参数
            message.getHeader().setMessageSerialNo((short) 0x0500);
            message.getHeader().setMessageType(0x8104);
        } else if (MiniAppConstantsEx.CONTENT_CONTROL_CHECK_ATTRIBUTE.equals(type)) {
            // todo 查询终端属性
            message.getHeader().setMessageSerialNo((short) 0x0600);
            message.getHeader().setMessageType(0x8107);
        } else {
            result.put("code", 400);
            result.put("message", "未识别的指令");
        }
        /* T808Message */
        message.setMessageContents(echoData);
        // 拼接开启
        MessageCommand command = SpringContextHolder.getBean(MessageCommand.class);
        try {
            command.sendMessage(deviceNo, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("code", 200);
        result.put("message", "状态发送成功");

        return result;
    }

    /**
     * 设置设备参数
     *
     * @param attribute
     * @return
     */
    @PostMapping("/deviceconfig")
    public Map<String, Object> deviceConfig(@NotNull @RequestBody DeviceAttribute attribute) {
        Map<String, Object> result = new HashMap<>();
        log.info("deviceconfig :" + attribute.toString());
        logsService.addPointNormalLog(attribute.getDeviceNo()," 业务服务器指令: "+attribute.toString());
        // TODO 判断不在线情况
        if (manager.getByMobileNumber(attribute.getDeviceNo()) == null) {
            result.put("code", 400);
            result.put("message", "设备不在线");
            logsService.addPointNormalLog(attribute.getDeviceNo(),"设备不在线");
            return result;
        }

        T808Message message = new T808Message();
        message.setHeader(new T808MessageHeader());
        message.getHeader().setSimId(attribute.getDeviceNo());
        message.getHeader().setIsPackage(false);
        message.getHeader().setMessageType(0x8103);
        // todo 设置终端属性
        message.getHeader().setMessageSerialNo((short) 0x0300);
        JT_8103 jt_8103 = new JT_8103();
        jt_8103.setParametersCount((byte) 5);
        ArrayList<ParameterItem> parameters = new ArrayList<>();
        // todo APN
        ParameterItem parameterItem_apn = new ParameterItem();
        parameterItem_apn.setParameterId(0x0010);
        parameterItem_apn.setParameterLength((byte) attribute.getApn().length());
        parameterItem_apn.setParameterValue(attribute.getApn());
        parameters.add(parameterItem_apn);
        // todo ip地址
        ParameterItem parameterItem_ip = new ParameterItem();
        parameterItem_ip.setParameterId(0x0013);
        parameterItem_ip.setParameterLength((byte) attribute.getIpAddress().length());
        parameterItem_ip.setParameterValue(attribute.getIpAddress());
        parameters.add(parameterItem_ip);
        //todo 端口
        ParameterItem parameterItem_port = new ParameterItem();
        parameterItem_port.setParameterId(0x0018);
        parameterItem_port.setParameterLength((byte) 4);
        parameterItem_port.setParameterValue(attribute.getPort());
        parameters.add(parameterItem_port);

        //todo 正常时间汇报间隔
        ParameterItem parameterItem_worktime = new ParameterItem();
        parameterItem_worktime.setParameterId(0x0029);
        parameterItem_worktime.setParameterLength((byte) 4);
        parameterItem_worktime.setParameterValue(attribute.getWork_fre());
        parameters.add(parameterItem_worktime);

        //todo 休眠时间汇报间隔
        ParameterItem parameterItem_sleeptime = new ParameterItem();
        parameterItem_sleeptime.setParameterId(0x0027);
        parameterItem_sleeptime.setParameterLength((byte) 4);
        parameterItem_sleeptime.setParameterValue(attribute.getSavemode_fre());
        parameters.add(parameterItem_sleeptime);

        jt_8103.setParameters(parameters);
        message.setMessageContents(jt_8103);
        // 拼接开启
        MessageCommand command = SpringContextHolder.getBean(MessageCommand.class);
        try {
            command.sendMessage(attribute.getDeviceNo(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("code", 200);
        result.put("message", "状态发送成功");

        return result;
    }


    /**
     * 下发OTA升级命令
     *
     * @param messageFromSys
     * @return
     */
    @PostMapping("/upgrade")
    public Map<String, Object> Upgrade(@NotNull @RequestBody OtaPackage messageFromSys) {
        log.info("upgrade" + messageFromSys.toString());
        logsService.addPointNormalLog(messageFromSys.getDeviceNos().get(0),"OTA在线升级");
        Map<String, Object> result = new HashMap<>();
        // 消息内容（OTA升级）
        String url = messageFromSys.getUrl();
        if (url == null) {
            return null;
        }
        List<String> Offlinelist = new ArrayList<>();
        List<String> Onlinelist = new ArrayList<>();
        boolean sendCount = false;

        for (String deviceNo : messageFromSys.getDeviceNos()) {
            Session session = manager.getByMobileNumber(deviceNo);
            if (session == null) {
                Offlinelist.add(deviceNo);
                log.info("====> " + deviceNo + "不在线。。无法下发升级命令");
                logsService.addPointNormalLog(deviceNo,"不在线。。无法下发升级命令");
                continue;
            }
            sendCount = true;
            T808Message message = new T808Message();
            message.setHeader(new T808MessageHeader());
            message.getHeader().setSimId(deviceNo);
            message.getHeader().setIsPackage(false);
            message.getHeader().setMessageType(0x8108);
            JT_8108 echoData = new JT_8108();
            echoData.setManufactureId("00000");
            echoData.setPort(messageFromSys.getPort());
            echoData.setType((byte) messageFromSys.getType());
            echoData.setUrl(messageFromSys.getUrl());
            echoData.setUrl_lenth((byte) messageFromSys.getUrl().length());
            echoData.setSw_lenth((byte) messageFromSys.getSw_version().length());
            echoData.setSw_version(messageFromSys.getSw_version());
            message.setMessageContents(echoData);
            MessageCommand command = SpringContextHolder.getBean(MessageCommand.class);
            try {
                command.sendMessage(deviceNo, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Onlinelist.add(session.getTerminalId());
            result.put("sucess", Onlinelist);
            result.put("fail", Offlinelist);
            result.put("code", 200);
        }
        if (sendCount == false) {
            result.put("sucess", Onlinelist);
            result.put("fail", messageFromSys.getDeviceNos());
            result.put("code", 200);
        }
        log.info("OTA设置返回: " + result);
        return result;
    }

    /**
     * 给硬件测试用，勿用于生产环境
     *
     * @param deviceNo
     * @param cmd
     * @return
     */
    @GetMapping("/cmd")
    public Map<String, Object> cmdline(@NotNull String deviceNo, String cmd) {
        CmdPackageEx cmdPackageEx = new CmdPackageEx();
        cmdPackageEx.setHeader("8500");
        cmdPackageEx.setDeviceNo(deviceNo);
        cmdPackageEx.setType(cmd);
        long timestamp = System.currentTimeMillis() / 1000;
        cmdPackageEx.setTimestamp(String.valueOf(timestamp));


        Map<String, Object> result = new HashMap<>();

        Session session = manager.getByMobileNumber(deviceNo);
        if (session == null) {
            result.put("code", 400);
            result.put("message", "设备不在线");
            return result;
        }

        if (cmd.equals("0300")) {
            DeviceAttribute deviceAttribute = new DeviceAttribute();
            deviceAttribute.setApn("cmcc");
            deviceAttribute.setDeviceNo(deviceNo);
            deviceAttribute.setPort(9501);
            deviceAttribute.setIpAddress("sapi.ntsiot.com");
            deviceAttribute.setSavemode_fre(80);
            deviceAttribute.setWork_fre(60);
            deviceConfig(deviceAttribute);
            result.put("code", 200);
            result.put("message", "状态发送成功");
            result.put("data", "{}");
            return result;
        }
        counterControl(cmdPackageEx);

        result.put("code", 200);
        result.put("message", "状态发送成功");
        result.put("data", "{}");
        return result;
    }

    /**
     * 给硬件测试用。勿用于生产环境
     *
     * @param cmd
     * @param deviceNo
     * @return
     */
    @GetMapping("/upgradeTest")
    public Map<String, Object> upgradeCmd(@NotNull String cmd, @NotNull String deviceNo) {
        Map<String, Object> result = new HashMap<>();
        OtaPackage otaPackage = new OtaPackage();
        List<String> deviceNos = new ArrayList<>();
        deviceNos.add(deviceNo);
        otaPackage.setType(Integer.valueOf(cmd));
        otaPackage.setDeviceNos(deviceNos);
        otaPackage.setPort(80);
        otaPackage.setSw_version("ver1.1.1");
        switch (cmd) {
            case "00": {
                otaPackage.setUrl("http://119.23.201.94/MCU_APP.bin");
            }
            break;
            case "49": {
                otaPackage.setUrl("http://119.23.201.94/BLE_OTA_ServiceManager.bin");

            }
            break;
            case "50": {
                otaPackage.setUrl("http://119.23.201.94/BLE_Chat_Server_Use_OTA_ServiceManager.bin");
            }
            break;
            default: {
                result.put("result", "命令有误");
                return result;
            }
        }
        Upgrade(otaPackage);

        Session session = manager.getByMobileNumber(deviceNo);
        if (session == null) {
            log.info("====> " + deviceNo + "不在线。。无法下发升级命令");
            result.put("result", deviceNo + " 不在线");
            return result;
        }
        result.put("result", "成功");
        return result;
    }
}

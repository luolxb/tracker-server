/*******************************************************************************
 * @(#)OtaCommandController.java 2019-08-06
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.system.rest;

import cn.hutool.http.HttpRequest;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.dto.CmdPackage;
import com.nts.iot.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.nts.iot.constant.MiniAppConstants.CONTENT_CONTROL_SETTING;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-08-06 20:08
 */
@RestController
@RequestMapping("ma")
public class OtaCommandController {

    @Value("${jt808ServerUrl}")
    private String jt808ServerUrl;

    @Value("${jt808ScottServerUrl}")
    private String jt808ScottServerUrl;

    /**
     * 设置终端参数（未对接）
     * @param cmdPackage
     * @return
     */
    @PostMapping("/settingValue")
    public Map<String, Object> settingValue(@RequestBody CmdPackage cmdPackage) {
        Map<String, Object> result = new HashMap<>();
        Long timesteamp = System.currentTimeMillis() / 1000;
        cmdPackage.setTimestamp(String.valueOf(timesteamp));
        cmdPackage.setType(CONTENT_CONTROL_SETTING);
        String result2;
//        if(!cmdPackage.getDeviceNo().startsWith("c3")){
//            result2 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//        }else {
            result2 = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//        }
//        System.out.println(result2);
        result.put("code", 200);
        result.put("message", "参数设置成功");
        return result;
    }

    /**
     * 查询终端属性
     * @param cmdPackage
     * @return
     */
    @PostMapping("/getSettingValue")
    public Map<String, Object> getSettingValue(@RequestBody CmdPackage cmdPackage) {
        Map<String, Object> result = new HashMap<>();
        String result2;
//        if(!cmdPackage.getDeviceNo().startsWith("c3")){
//            result2 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//        }else {
            result2 = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//        }
//        System.out.println(result2);
        result.put("code", 200);
        result.put("message", "参数获取成功");
        return result;
    }

    /**
     * 设置OTA升级配置
     * @param cmdPackage
     * @return
     */
    @PostMapping("/upgrade")
    public Map<String, Object> upgrade(@RequestBody CmdPackage cmdPackage) {
        Map<String, Object> result = new HashMap<>();
        String result2;
//        if(!cmdPackage.getDeviceNo().startsWith("c3")){
//            result2 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//        }else {
            result2 = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//        }
//        System.out.println(result2);
        result.put("code", 200);
        result.put("message", "关闭成功");
        return result;
    }

    /**
     * 设置反馈请求
     * @param body
     * @return
     */
    @PostMapping("/cmd104")
    public Map<String, Object> getSettingValueCallBack(@RequestBody  Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "cmd104应答成功");
        return result;
    }


    /**
     * 参数查询反馈请求
     * @param body
     * @return
     */
    @PostMapping("/cmd107")
    public Map<String, Object> putSettingValueCallBack(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "cmd104应答成功");
        return result;
    }


    /**
     * OTA升级结果反馈
     * @param body
     * @return
     */
    @PostMapping("/cmd108")
    public Map<String, Object> getUpdateInfoCallBack(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "cmd108应答成功");
        return result;
    }


    // 开锁
    public static final String MINI_OPEN_LOCK = "open";

    // 关锁
    public static final String MINI_OPEN_UNLOCK = "close";

    /**
     * 接收客户端发来的消息(开锁页面)
     */
    @PostMapping("/sendOpen")
    public void sendOpen(@RequestBody CmdPackage cmdPackage) {
        Long timesteamp = System.currentTimeMillis() / 1000;
        cmdPackage.setTimestamp(String.valueOf(timesteamp));
        // 消息头 反控
        cmdPackage.setHeader(MiniAppConstants.MESSAGE_HEAD_CONTROL);
        if (MINI_OPEN_LOCK.equals(cmdPackage.getType())) {
            // 消息类型 开锁
            cmdPackage.setType(MiniAppConstants.CONTENT_CONTROL_OPEN_LOCK);
        } else if (MINI_OPEN_UNLOCK.equals(cmdPackage.getType())) {
            // 消息类型 开锁
            cmdPackage.setType(MiniAppConstants.CONTENT_CONTROL_CLOSE_LOCK);
        {
            System.out.println("closed 预留");
        }
            String result1;
//            if(!cmdPackage.getDeviceNo().startsWith("c3")){
//                result1 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//            }else {
                result1 = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//            }
//        System.out.println(result1);
        }
    }
}

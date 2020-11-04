package com.nts.iot.modules.miniApp.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CmdPackage;
import com.nts.iot.dto.CmdResult;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.model.OpenLock;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.model.Order;
import com.nts.iot.modules.system.service.BikeManagerService;
import com.nts.iot.modules.system.service.OrderManagerService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("maSocket")
public class MaSocketController {

    //private static final Logger log = getLogger(MaSocketController.class.getName());

    @Autowired
    SimpMessageSendingOperations simpMessageSendingOperations;


    @Autowired
    MaUserService userService;

    @Autowired
    BikeManagerService bikeManagerService;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    OrderManagerService orderManagerService;

    @Value("${jt808ServerUrl}")
    private String jt808ServerUrl;

    @Value("${jt808ScottServerUrl}")
    private String jt808ScottServerUrl;

    // 开锁
    private static final String MINI_OPEN_LOCK = "open";

    private static final String MINI_CLOSE_LOCK = "close";

    /**
     * 接收客户端发来的消息(开锁页面)
     */
    @MessageMapping("/message")
    public void handleSubscribe(String msg) {
        log.info("MaSocketController.handleSubscribe：" + msg);

        JSONObject msgFromApp = JSONObject.parseObject(msg);
        String message = msgFromApp.getString("msg");
        String deviceNo = msgFromApp.getString("deviceNo");
        String openId = msgFromApp.getString("openId");
        String bikeBarCode = msgFromApp.getString("bikeBarCode");
        msg = JSONObject.parseObject(msg).getString("msg");
        //System.out.println("客户端发来消息：" + msg);

        // 加入到缓存中，待使用

        /* 判断信息是否正确 */
        if (MINI_OPEN_LOCK.equals(message)) {
            Map<String, Object> res = new HashMap<>();
            if (checkOpenLock(bikeBarCode, deviceNo, openId, res)) {
                saveOrUpdataToRedis(deviceNo, openId, bikeBarCode);

                CmdPackage cmdPackage = new CmdPackage(MiniAppConstants.MESSAGE_HEAD_CONTROL,
                        deviceNo, MiniAppConstants.CONTENT_CONTROL_OPEN_LOCK, null, String.valueOf(System.currentTimeMillis() / 1000));
                String result1;
//                if(!cmdPackage.getDeviceNo().startsWith("c3")){
//                    result1 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//                }else {
                result1 = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//                }
//                System.out.println(result1);
            } else {
                //如果不能够开锁，直接发送消息回去
                simpMessageSendingOperations.convertAndSendToUser(deviceNo, "/message", JSON.toJSONString(res));
            }
        } else if (MINI_CLOSE_LOCK.equals(message)) {
            CmdPackage cmdPackage = new CmdPackage(MiniAppConstants.MESSAGE_HEAD_CONTROL,
                    deviceNo, MiniAppConstants.CONTENT_CONTROL_CLOSE_LOCK, null, String.valueOf(System.currentTimeMillis() / 1000));
            String result1;
//            if(!cmdPackage.getDeviceNo().startsWith("c3")){
//                result1 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//            }else {
            result1 = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//            }
//            System.out.println(result1);
        } else {
            System.out.println(" 预留 message");
        }
    }

    @PostMapping("/http/openlock")
    public Map httpOpenLock(@RequestBody OpenLock openLock){
        log.info("MaSocketController.handleSubscribe：" + openLock.toString());

        String message = openLock.getMessage();
        String deviceNo = openLock.getDeviceNo();
        String openId = openLock.getOpenId();
        String bikeBarCode = openLock.getBikeBarCode();
        //System.out.println("客户端发来消息：" + msg);

        // 加入到缓存中，待使用

        /* 判断信息是否正确 */
        if (MINI_OPEN_LOCK.equals(message)) {
            Map<String, Object> res = new HashMap<>();
            if (checkOpenLock(bikeBarCode, deviceNo, openId, res)) {
                saveOrUpdataToRedis(deviceNo, openId, bikeBarCode);

                CmdPackage cmdPackage = new CmdPackage(MiniAppConstants.MESSAGE_HEAD_CONTROL,
                        deviceNo, MiniAppConstants.CONTENT_CONTROL_OPEN_LOCK, null, String.valueOf(System.currentTimeMillis() / 1000));
                Map<String, Object> result1 = new HashMap<>();
//                if(!cmdPackage.getDeviceNo().startsWith("c3")){
//                    result1 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//                }else {
                String a = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
                result1 = JSONObject.parseObject(a, new TypeReference<Map<String, Object>>(){});
//                }
                return result1;
            } else {
                //如果不能够开锁，直接发送消息回去
                return res;
            }
        } else if (MINI_CLOSE_LOCK.equals(message)) {
            CmdPackage cmdPackage = new CmdPackage(MiniAppConstants.MESSAGE_HEAD_CONTROL,
                    deviceNo, MiniAppConstants.CONTENT_CONTROL_CLOSE_LOCK, null, String.valueOf(System.currentTimeMillis() / 1000));
            String a;
            Map<String, Object> result1 = new HashMap<>();
//            if(!cmdPackage.getDeviceNo().startsWith("c3")){
//                result1 = HttpRequest.post(jt808ScottServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
//            }else {
            a = HttpRequest.post(jt808ServerUrl + "/api/counterControl").body(JsonUtil.getJson(cmdPackage)).execute().body();
            result1 = JSONObject.parseObject(a, new TypeReference<Map<String, Object>>(){});
//            }
            return result1;
        } else {
            System.out.println(" 预留 message");
        }
        return null;
    }

    public boolean checkOpenLock(String bikeBarCode, String deviceNo, String openId, Map<String, Object> res) {
        QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
        // 订单进行中
        queryWrapper2.eq("status", 2L);
        queryWrapper2.eq("bike_barcode", bikeBarCode);
        List<Order> orders = orderManagerService.list(queryWrapper2);
        if (orders != null && orders.size() > 0 && orders.get(0) != null) {
            res.put("state", "false");
            res.put("message", "该车辆正在被使用!");
            return false;
        }
        //限制一个用户同时只能骑一个车，已经有订单则无法开锁.
        QueryWrapper<MaUser> maUserQueryWrapper = new QueryWrapper<>();
        maUserQueryWrapper.eq("openid", openId);
        maUserQueryWrapper.eq("user_type", 0);
        MaUser maUser = userService.getOne(maUserQueryWrapper);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("status", 2L);
        orderQueryWrapper.eq("user_id", maUser.getId());
        List<Order> orderByUser = orderManagerService.list(orderQueryWrapper);
        if (orderByUser != null && !orderByUser.isEmpty()) {
            res.put("state", "false");
            res.put("message", "已经有在骑行的车辆，无法同时使用多辆车");
            return false;
        }
        //TODO 限行围栏判断
        if (bikeManagerService.checkOpenLock(deviceNo)) {

        } else {
            res.put("state", "false");
            res.put("message", "未在指定区域内开锁！");
            return false;
        }
        return true;
    }

    @GetMapping("/blueCheckOpen")
    public Map<String, Object> blueCheckOpen(String bikeBarCode, String deviceNo) {
        Map<String, Object> res = new HashMap<String, Object>();
        QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
        // 订单进行中
        queryWrapper2.eq("status", 2L);
        queryWrapper2.eq("bike_barcode", bikeBarCode);
        List<Order> orders = orderManagerService.list(queryWrapper2);
        if (orders != null && orders.size() > 0 && orders.get(0) != null) {
            res.put("state", "false");
            res.put("message", "该车辆正在被使用!");
            return res;
        }
        //TODO 限行围栏判断
        if (bikeManagerService.checkOpenLock(deviceNo)) {

        } else {
            res.put("state", "false");
            res.put("message", "未在指定区域内开锁！");
            return res;
        }
        res.put("state", "true");
        res.put("message", "允许开锁！");
        return res;
    }

    /**
     * 接收客户端发来的消息(开锁页面)
     */
    @MessageMapping("/payment")
    public void payHandleSubscribe(String msg) {
        JSONObject msgObj = JSONObject.parseObject(msg);
        System.out.println("客户端发来消息：" + msg);
        log.info("客户端发来消息：" + msg);
    }

    /**
     * 接收客户端发来的消息(开锁页面)
     */
    /*@MessageMapping("/check")
    public void handleSubscribes(String msg){
        JSONObject msgFromApp = JSONObject.parseObject(msg);
        String message = msgFromApp.getString("msg");
        String deviceNo = msgFromApp.getString("deviceNo");
        String openId = msgFromApp.getString("openId");
        String bikeBarCode = msgFromApp.getString("bikeBarCode");
        msg = JSONObject.parseObject(msg).getString("msg");
        System.out.println("客户端发来消息：" + msg);
//        // 加入到缓存中，待使用
//        saveOrUpdataToRedis( deviceNo,  openId,  bikeBarCode);
        //判断车是否被使用，是否能继续开锁
        Map<String, Object> res = new HashMap<>();
        MaUser maUser=userService.getUserByWxId(openId);
        QueryWrapper<Order> queryWrapper2 = new QueryWrapper<>();
        // 订单进行中
        queryWrapper2.eq("status", 2L);
        queryWrapper2.eq("bike_barcode", bikeBarCode);
        List<Order> orders = orderManagerService.list(queryWrapper2);
        if (orders==null || orders.size() ==0){
            res.put("state","true");
            simpMessageSendingOperations.convertAndSendToUser(openId, "/message", JSON.toJSONString(res));
        }else{
            res.put("state","false");
            simpMessageSendingOperations.convertAndSendToUser(openId, "/message", JSON.toJSONString(res));
        }

    }*/

    /**
     * 测试对指定用户发送消息方法
     * 浏览器  访问https://xcx.dcssn.com/sendToUser?user=123456 前端小程序会收到信息
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id是deviceNo
     *
     * @return 发送的消息内容
     */
    @PostMapping("/sendToUser")
    public Map<String, Object> sendToUser(@RequestBody CmdResult cmdResult) {
        Map<String, Object> res = new HashMap<>();
        try {
            String jsonStr = JSONUtil.toJsonStr(cmdResult);
            log.info("========================收到jt808 发来的数据=========================" + jsonStr);
            System.out.println("========================收到jt808 发来的数据=========================" + jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String type = cmdResult.getType();
        String deviceNo = cmdResult.getDeviceNo();
        String message = cmdResult.getMessage();
        String stateResult = cmdResult.getResult();
        if (deviceNo == null || type == null || stateResult == null) {
            System.out.println("接收到存在空的消息");
            return null;
        }
        /* 反控流程 */
        if ("8500".equals(message)) {
            String state = "ok";
            res.put("type", type);
            /* 判断 0100 远程开锁  0101 远程关锁*/

            if ("0100".equals(type)) {
                if (MiniAppConstants.OPEN_LOCK_FINISH.equals(stateResult)) {
                    /* 创建订单相关操作 */
                    res = orderManagerService.createByDeviceNo(deviceNo);
                    res.put("message", "解锁成功");
                    log.info("====================================解锁成功=====================================");
                    //System.out.println("====================================解锁成功=====================================");
                } else if (MiniAppConstants.OPEN_LOCK_FAILED.equals(stateResult)) {
                    state = "failed";
                    res.put("message", "解锁失败");
                    log.info("====================================解锁失败=====================================");
                    //System.out.println("====================================解锁失败=====================================");
                }
            } else if ("0101".equals(type)) {
                if (MiniAppConstants.CLOSE_LOCK_FINISH.equals(stateResult)) {
                    /* 关闭订单相关操作 */
                    Map<String, Object> closeResponse = orderManagerService.closeOrderByDeviceNo(deviceNo);
                    log.info("MaSocketController.sendToUser:" + JSON.toJSONString(closeResponse));
                    if ((int) closeResponse.get("code") == 200) {
                        res.put("message", "关锁成功");
                        log.info("====================================关锁成功=====================================");
                        //System.out.println("====================================关锁成功=====================================");
                    } else {
                        state = "failed";
                        res.put("message", "关锁失败");
                        log.info("====================================关锁失败=====================================");
                        //System.out.println("====================================关锁失败=====================================");
                    }
                } else if (MiniAppConstants.CLOSE_LOCK_FAILED.equals(stateResult)) {
                    state = "failed";
                    res.put("message", "关锁失败");
                    log.info("====================================关锁失败=====================================");
                    // System.out.println("====================================关锁失败=====================================");
                }
            }
            res.put("state", state);
            res.put("type", type);
            log.info("========================发送websocket消息=========================" + JSON.toJSONString(res));

            //http开锁暂时注释
            //simpMessageSendingOperations.convertAndSendToUser(deviceNo, "/message", JSON.toJSONString(res));
        }
        return res;
    }

    /**
     * 群发消息 订阅/topic/greetings 会收到
     * 浏览器  访问https://xcx.dcssn.com/sendToAll 前端小程序会收到信息
     *
     * @return 发送的消息内容
     */
    @RequestMapping(path = "/sendToAll", method = RequestMethod.GET)
    public String sendToUser() {
        String payload = "群发消息" + LocalTime.now();
        simpMessageSendingOperations.convertAndSend("/topic/greetings", payload);
        return payload;
    }

    private void saveOrUpdataToRedis(String deviceNo, String openId, String bikeBarCode) {
        String stateJson = redisUtil.getData(RedisKey.PRE_ORDER_KEY + deviceNo);
        // 车辆状态存在
        if (stateJson != null && !"".equals(stateJson)) {
            // 更新缓存
            redisUtil.updateRedis(RedisKey.PRE_ORDER_KEY + deviceNo, deviceNo + "," + openId + "," + bikeBarCode);
        } else {
            //车辆状态不存在
            redisUtil.addRedis(RedisKey.PRE_ORDER_KEY + deviceNo, deviceNo + "," + openId + "," + bikeBarCode);
        }
    }


}


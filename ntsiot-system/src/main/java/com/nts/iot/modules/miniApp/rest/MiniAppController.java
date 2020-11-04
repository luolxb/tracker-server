/*******************************************************************************
 * @(#)EquipmentController.java 2017-11-08
 *
 * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.rest;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.system.service.*;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.nts.iot.modules.miniApp.dto.DeptDto;
import com.nts.iot.modules.miniApp.dto.MaRequestBody;
import com.nts.iot.modules.system.dto.AppModuleDto;
import com.nts.iot.modules.system.model.AppConfig;
import com.nts.iot.modules.system.model.AppDeptRela;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Message;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nts.iot.constant.MiniAppConstants.USER_TYPE_INNER;
import static com.nts.iot.constant.MiniAppConstants.USER_TYPE_OUTER;


/**
 * <p>
 * </p>
 * 小程序登录等接口
 *
 * @author <a href="mailto:zzm@rnstec.com">zzm</a>
 * @version dls-api 1.0 $ 2017-11-08
 */
@RestController
@RequestMapping("ma")
public class MiniAppController {

    /* 2019/07/05 开始拆分用户表 */
    @Autowired
    MaUserService userService;

    @Autowired
    DeptService deptService;

    @Autowired
    BikeManagerService bikeManagerService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    BikeLicenseService bikeLicenseService;

    @Autowired
    private CheckPointService checkPointService;

    @Autowired
    private VirtualPileService virtualPileService;

    @Autowired
    private JurisdictionConfigurationService deptConfigurationService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AppDeptRelaService appDeptRelaService;

    @Autowired
    private DeptAppRelaService deptAppRelaService;

    @Autowired
    private AppModuleService appModuleService;

    @Autowired
    private MessageService messageService;

    @Value("${engineServerUrl}")
    private String engineServerUrl;

    @Value("${jt808ServerUrl}")
    private String jt808ServerUrl;

    @Value("${jt808ScottServerUrl}")
    private String jt808ScottServerUrl;

    @Value("${rechargeNotifyUrl}")
    private String rechargeNotifyUrl;

    @Value("${wechat.miniapp.appId}")
    private String maAppId;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucketName}")
    private String bucket;

    @Value("${qiniu.url}")
    private String remoteSrcUrl;

    /*============================================微信登录等初始化相关 START ==========================================================*/

    /**
     * 根据CODE取出OPEN ID 等
     *
     * @param requestBody MaRequestBody
     * @return result Map<String, Object>
     * @throws WxErrorException WxErrorException
     */
    @PostMapping("getUnionId")
    public Map<String, Object> getUnionId(@RequestBody MaRequestBody requestBody) throws WxErrorException {
        Map<String, Object> result = new HashMap<>();
        String appId = requestBody.getAppId();
        if (appId == null) {
            result.put("code", 500);
            result.put("message", "微信版本过低");
            return result;
        }
        WxMaService wxMaService =  getWxMaService(appId);
        if (wxMaService == null) {
            result.put("code", 500);
            result.put("message", "配置文件出错");
            return result;
        }
        WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.getUserService().getSessionInfo(requestBody.getCode());
        if (StringUtils.isEmpty(wxMaJscode2SessionResult.getOpenid())) {
            result.put("openId", "no");
            result.put("code", 500);
            result.put("message", "获取openID失败，请重试");
            return result;
        }
        String openId = wxMaJscode2SessionResult.getOpenid();
        String sessionKey = wxMaJscode2SessionResult.getSessionKey();
        result.put("openId", openId);
        result.put("sessionKey", sessionKey);
        Dept dept = null;
        // 查询通过openId 查询用户DeptId 如果 有辖区，则内部人员，用内部人员的
        MaUser user = userService.getUserByWxId(openId);
        result.put("user", user);
        if (openId != null && user != null) {
            if (user.getDeptId() != null) {
                // 内部人员
                dept = deptService.getById(user.getDeptId());
            }
        }
        if (dept != null) {
            result.put("dept", deptConfigurationService.getDeptInfoById(dept));
            // 根据辖区id 查询app 权限 appPermission
            List<AppModuleDto> appModuleList = appModuleService.getModuleDtosByDeptId(dept.getId());
            List<String> appPermissions = new ArrayList<>();
            appModuleList.stream().filter(p->p.getCode()!=null).forEach(p->{
                // 加入权限code
                appPermissions.add(p.getCode());
            });
            result.put("buttonList",appModuleList);
            result.put("appPermission", appPermissions);
        } else {
            result.put("code", 500);
            result.put("message", "该辖区不存在");
            result.put("dept", null);
        }
        return result;
    }

    /**
     * 根据appId 和 当前坐标 获取外部人员的辖区
     * @param requestBody
     * @return
     */
    @PostMapping("getDeptByAppid")
    public Map<String, Object> getDeptByAppid(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String appId = requestBody.getAppId();
        // 经度
        String longitude = requestBody.getLongitude();
        // 纬度
        String latitude = requestBody.getLatitude();
        if (appId == null) {
            result.put("code",500);
            result.put("message","微信版本过低");
            return result;
        }
        Dept dept;
        // TODO 如果没有定位，暂定设置吴泾为默认辖区
        if (longitude == null || latitude ==null ){
            dept = deptService.getById(2L);
        }else {
            // 根据经纬度取辖区
            dept = getJurisdiction(requestBody);
        }
        // TODO 如果取不到辖区，暂定设置吴泾为默认辖区
        if(dept==null){
            dept = deptService.getById(2L);
        }
        List<AppModuleDto> appModuleList = appModuleService.getModuleDtosByDeptId(dept.getId());
        result.put("buttonList",appModuleList);
        result.put("dept", deptConfigurationService.getDeptInfoById(dept));
        return result;
    }

    /**
     * 根据appId 获取外部人员的辖区list
     * @param requestBody
     * @return
     */
    @PostMapping("getDeptListByAppid")
    public Map<String, Object> getDeptListByAppid(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> listResult = new HashMap<>();
        String appId = requestBody.getAppId();
        if (appId == null) {
            listResult.put("code",500);
            listResult.put("message","微信版本过低");
            return listResult;
        }
        QueryWrapper<AppConfig> queryWrapper = new QueryWrapper<>();
        // 利用appId 查询 小程序配置表，取出idList
        queryWrapper.eq("app_id", appId);
        List<AppConfig> appConfigs = appConfigService.list(queryWrapper);
        List<Long> cIds = new ArrayList<>();
        appConfigs.forEach(p-> cIds.add(p.getId()));
        if (cIds.size() > 0){
            // 利用小程序配置表的idlist 取出关联表List
            QueryWrapper<AppDeptRela> relaWrapper = new QueryWrapper<>();
            relaWrapper.in("app_id",cIds);
            List<AppDeptRela> relaList = appDeptRelaService.list(relaWrapper);
            List<Long> deptIds = new ArrayList<>();
            relaList.forEach(p-> deptIds.add(p.getDeptId()));
            if (deptIds.size()>0){
                // 利用辖区表idList 取出实体list
                QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();
//                deptWrapper.in("id",deptIds);
                List<Dept> deptList = deptService.getDeptAllAndJurisdictionConfig(deptIds);
                if (deptList.size()>0){
                    listResult.put("deptList",deptList);
                    // 查询总辖区下的大区
                    QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<>();
                    deptWrapper.in("pid",1L);
                    List<Dept> parentDepts = deptService.list(deptQueryWrapper);
                    // 获取辖区选择中用到的关系表结构
                    List<Map<String,Object>> areasTemp = new ArrayList<>();
                    parentDepts.forEach(p->{
                        Long pId = p.getId();
                        Map<String,Object> area = new HashMap<>();
                        area.put("id",p.getId());
                        DeptDto deptDto = deptConfigurationService.getDeptInfoById(p);
                        area.put("title",deptDto.getName());
                        List<Map<String,Object>> subList = new ArrayList<>();
                        for (Dept subDept :deptList) {
                            // 判断sub 的 父类id
                            if (subDept.getPid().equals(pId)){
                                Map<String,Object> sub = new HashMap<>();
                                sub.put("id",subDept.getId());
                                DeptDto deptDto2 = deptConfigurationService.getDeptInfoById(subDept);
                                sub.put("title",deptDto2.getName());
                                subList.add(sub);
                            }
                        }
                        area.put("sub",subList);
                        areasTemp.add(area);
                    });
                    List<Map<String,Object>> areas = new ArrayList<>();
                    areasTemp.forEach(p->{
                        List<Map<String,Object>> subList = (List<Map<String,Object>>) p.get("sub");
                        if (subList!=null && subList.size()>0){
                            areas.add(p);
                        }
                    });
                    listResult.put("areaList",areas);
                }else {
                    listResult.put("deptList",null);
                }
            }else {
                listResult.put("deptList",null);
            }
        } else {
            listResult.put("deptList",null);
        }
        return listResult;
    }





    @PostMapping("getWxPhone")
    public Map<String, Object> getWxPhone(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        if (ToolUtil.isOneEmpty(requestBody.getSessionKey(), requestBody.getEncryptedData(), requestBody.getIvStr())) {
            result.put("code", 500);
            result.put("phoneInfo", null);
            return result;
        }
        String phone = WxMaCryptUtils.decrypt(requestBody.getSessionKey(), requestBody.getEncryptedData(), requestBody.getIvStr());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> m = mapper.readValue(phone, Map.class);
            result.put("code", 200);
            result.put("phoneInfo", m);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("phoneInfo", null);
        }
        return result;
    }

    /**
     * 内部人员注册
     * @param requestBody
     * @return
     */
    @PostMapping("registerInner")
    public Map<String, Object> checkWxPhone(@RequestBody MaRequestBody requestBody) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//        String accessKey = "tObp3kCEGJ2Lvf7EWt7oGifrATth-P-QI6aMH9HT";
//        String secretKey = "j4EpTqXwCbp2MoPzNsR6LdI_Kae9p69rskEPvfDK";
//        String bucket = "images";
        String key = requestBody.getOpenId()+".jpg";
//        String remoteSrcUrl = "http://images.cyoubike.com";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        String avatarUrl="";
        //抓取网络资源到空间
        try {
            FetchRet fetchRet = bucketManager.fetch(requestBody.getAvatar(), bucket, key);
            avatarUrl=fetchRet.key;
//            System.out.println(fetchRet.hash);
//            System.out.println(fetchRet.key);
//            System.out.println(fetchRet.mimeType);
//            System.out.println(fetchRet.fsize);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        Map<String, Object> result = new HashMap<>();
        /* check 传参*/
        if (ToolUtil.isOneEmpty(requestBody.getPhoneNum(), requestBody.getUsername(), requestBody.getOpenId(),requestBody.getAppId())) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        // 传入的辖区ID
        Dept dept = deptService.getById(requestBody.getDeptId());
        if (dept == null) {
            result.put("code", 500);
            result.put("message", "查询不到辖区");
            return result;
        }
        // 通过openId查询用户
        MaUser maUser = userService.getUserByWxId(requestBody.getOpenId());
        // 如果没有查询到该用户
        if (maUser == null) {
            // 新建用户
            // 如果查询到用户 1 个 ，则更新该用户的ma_open_id 字段和 open id 字段,然后再返回成功
            MaUser user = new MaUser();
            user.setName(requestBody.getUsername());
            user.setPhone(requestBody.getPhoneNum());
            user.setDeptId(Long.valueOf(requestBody.getDeptId()));
            // 外部人员
            user.setUserType(USER_TYPE_INNER);
            // 小程序 opendId
            user.setOpenid(requestBody.getOpenId());
            user.setAppid(requestBody.getAppId());
            user.setCreateTime(System.currentTimeMillis());
            user.setAvatar(remoteSrcUrl+"/"+avatarUrl);
            userService.save(user);
            result.put("user", user);
            result.put("dept", dept);
            result.put("code", 200);
            result.put("message", "成功");
        } else {
            // 如果查询到一个用户，更新用户数据
            // 如果已经绑定过辖区
            if (maUser.getDeptId() != null) {
                result.put("code", 500);
                result.put("message", "您已绑定辖区，请联系管理员解绑再注册");
            } else if(requestBody.getPhoneNum().equals(maUser.getPhone())){
                result.put("code", 500);
                result.put("message", "您的手机号码已经被注册");
            } else {
                // 如果查询到用户 1 个 ，则更新该用户的ma_open_id 字段和 open id 字段,然后再返回成功
                // 小程序 opendId
                maUser.setName(requestBody.getUsername());
                maUser.setPhone(requestBody.getPhoneNum());
                // 外部人员
                maUser.setUserType(USER_TYPE_INNER);
                // 小程序 opendId
                maUser.setOpenid(requestBody.getOpenId());
                maUser.setDeptId(Long.valueOf(requestBody.getDeptId()));
                maUser.setAppid(requestBody.getAppId());
                maUser.setAvatar(remoteSrcUrl+"/"+avatarUrl);
                userService.saveOrUpdate(maUser);
                result.put("user", maUser);
                result.put("dept", dept);
                result.put("code", 200);
                result.put("message", "成功");
            }
        }
        return result;
    }


    /**
     * 注册外部人员
     * @param requestBody
     * @return
     */
    @PostMapping("registerOuter")
    public Map<String, Object> registerOuter(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String openId = requestBody.getOpenId();
        if (openId==null || "".equals(openId) ) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        /* check 传参*/
        if (ToolUtil.isOneEmpty( requestBody.getAppId(), requestBody.getOpenId())) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        /* 查询是否有该openId的用户 */
        MaUser oldUser = userService.getUserByWxId(openId);
        if (oldUser!=null){
            result.put("code", 201);
            result.put("message", "已经存在该用户");
            return result;
        }
        /* 新增用户 */
        MaUser user = new MaUser();
        user.setName(null);
        user.setPhone(null);
        user.setDeptId(null);
        // 外部人员
        user.setUserType(USER_TYPE_OUTER);
        // 小程序 opendId
        user.setOpenid(requestBody.getOpenId());
        user.setAppid(requestBody.getAppId());
        user.setCreateTime(System.currentTimeMillis());
        userService.saveOrUpdate(user);
        result.put("code", 200);
        result.put("message", "成功");
        return result;
    }



    // 获取消息列表
    @PostMapping("/getMessageList")
    public Map<String, Object> getMessageList(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String userId = requestBody.getUserId();
        if (ToolUtil.isOneEmpty(userId)) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        List<Message> messageList = messageService.getByUserId(userId);
        result.put("messageList", messageList);
        result.put("code", 200);
        result.put("message", "查询成功");
        return result;
    }

    // 标记已读
    @PostMapping("/readMessage")
    public Map<String, Object> readMessage(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String messageId = requestBody.getMessageId();
        String userId = requestBody.getUserId();
        if (ToolUtil.isOneEmpty(messageId,userId)) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        Message message = messageService.getById(Long.valueOf(messageId));
        message.setIsRead(true);
        messageService.updateById(message);
        List<Message> messageList = messageService.getByUserId(userId);
        result.put("messageList", messageList);
        result.put("code", 200);
        result.put("message", "修改成功");
        return result;
    }


    // 删除消息列表
    @PostMapping("/deleteMessage")
    public Map<String, Object> deleteMessage(@RequestBody MaRequestBody requestBody) {
        Map<String, Object> result = new HashMap<>();
        String messageDelId = requestBody.getMessageId();
        String userId = requestBody.getUserId();
        if (ToolUtil.isOneEmpty(messageDelId,userId)) {
            result.put("code", 500);
            result.put("message", "参数有误");
            return result;
        }
        messageService.removeById(Long.valueOf(messageDelId));
        List<Message> messageList = messageService.getByUserId(userId);
        result.put("messageList", messageList);
        result.put("code", 200);
        result.put("message", "删除成功");
        return result;
    }


    /**
     *
     * 根据用户的地理位置信息获得辖区信息
     * @param requestBody
     *          地址位置坐标
     * @return  辖区信息
     */
    public Dept getJurisdiction(MaRequestBody requestBody) {
        Dept result = null;
        // 获取所有辖区信息
        List<Dept> depts = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.LIST_JURISDICTION_KEY), Dept.class);

        // 获得限行围栏的缓存数据
        boolean breakFlag = false;

        for (Dept dept : depts) {
            String key = RedisKey.RESTRICTIONS_FENCE + dept.getId();
            List<String> fences = JsonUtil.jsonConvertList(redisUtil.getData(key), String.class);
            if (fences!=null){
                for(String fence: fences) {
                    String[] fenceArr = fence.split(";");
                    Point2D.Double pd = new Point2D.Double();
                    pd.setLocation(Double.parseDouble(requestBody.getLongitude()), Double.parseDouble(requestBody.getLatitude()));
                    List<Point2D.Double> list = EnclosureUtil.getPDList(fenceArr);
                    // 如果在限行围栏中，那么就说明找到了
                    if (EnclosureUtil.isInPolygon(pd, list)) {
                        breakFlag = true;
                        break;
                    }
                }
            }
            // 只要找到一个能匹配上的，就跳出循环
            if(breakFlag) {
                result = dept;
                break;
            }
        }
        return result;

    }

    // 根据辖区号取openId
    private WxMaService getWxMaService(String appId) {
        String redisData = redisUtil.getData(RedisKey.WX_MA_KEY + appId);
        if (redisData != null) {
            JSONObject jsonObject = JSONUtil.parseObj(redisData);
            WxMaInMemoryConfig config = new WxMaInMemoryConfig();
            config.setAppid(String.valueOf(jsonObject.get("appId")));
            config.setSecret(String.valueOf(jsonObject.get("secret")));
            config.setToken(String.valueOf(jsonObject.get("token")));
            config.setAesKey(String.valueOf(jsonObject.get("aesKey")));
            config.setMsgDataFormat("JSON");
            WxMaService service = new WxMaServiceImpl();
            service.setWxMaConfig(config);
            return service;
        } else {
            return null;
        }
    }

}

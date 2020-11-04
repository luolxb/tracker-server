///*******************************************************************************
// * @(#)WechatMpConfiguration.java 2018-03-02
// *
// * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.config;
//
//
//import WechatMpProperties;
//import com.rnstec.cyoubike.modules.miniApp.handler.*;
//import me.chanjar.weixin.common.api.WxConsts;
//import me.chanjar.weixin.common.error.WxErrorException;
//import me.chanjar.weixin.mp.api.WxMpConfigStorage;
//import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
//import me.chanjar.weixin.mp.api.WxMpMessageRouter;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.result.WxMpUser;
//import me.chanjar.weixin.mp.bean.result.WxMpUserList;
//import me.chanjar.weixin.mp.constant.WxMpEventConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <p>
// * </p>
// *
// * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
// * @version igs 1.0 $ 2018-03-02 11:17
// */
////@Configuration
////@ConditionalOnClass(WxMpService.class)
//public class WxMpConfiguration {
//
//    @Autowired
//    protected LogHandler logHandler;
//    @Autowired
//    protected NullHandler nullHandler;
//    @Autowired
//    protected KfSessionHandler kfSessionHandler;
//    @Autowired
//    protected StoreCheckNotifyHandler storeCheckNotifyHandler;
//    @Autowired
//    private WechatMpProperties properties;
//    @Autowired
//    private LocationHandler locationHandler;
//    @Autowired
//    private MenuHandler menuHandler;
//    @Autowired
//    private MsgHandler msgHandler;
//    @Autowired
//    private UnsubscribeHandler unsubscribeHandler;
//    @Autowired
//    private SubscribeHandler subscribeHandler;
//
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WxMpConfigStorage configStorage() {
//        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
//        configStorage.setAppId(this.properties.getAppId());
//        configStorage.setSecret(this.properties.getSecret());
//        configStorage.setToken(this.properties.getToken());
//        configStorage.setAesKey(this.properties.getAesKey());
//        return configStorage;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(configStorage);
//        return wxMpService;
//    }
//
//    @Bean
//    public WxMpMessageRouter mpRouter(WxMpService wxMpService) throws WxErrorException {
//        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
//
//        // 获取所有已关注用户的用户信息
//        // 查询如果没有改用户则进行插入操作
//        WxMpUserList wxUserList = wxMpService.getUserService().userList(null);
//        if (wxUserList!=null){
////            WxMpUserQuery query = new WxMpUserQuery();
////            query.add(wxUserList.getOpenids());
//
//            List<WxMpUser> userList = new ArrayList<>();
//            int maxSize = wxUserList.getOpenids().size();
//            int step = 50;
//            int queryTimes = (wxUserList.getOpenids().size()) / step + 1;
//            int startIndex = 0;
//            for (int times = 0; times< queryTimes;times++) {
//                startIndex = times * step;
//                List<String> qOpenIdList = new ArrayList<>();
//                for (int i = startIndex; i < startIndex + step; i++) {
//                    if (i < maxSize){
//                        qOpenIdList.add(wxUserList.getOpenids().get(i));
//                    }
//                }
//                List<WxMpUser> innerUserList = wxMpService.getUserService()
//                        .userInfoList(qOpenIdList);
//                userList.addAll(innerUserList);
//            }
//            // 关注事件
//            // userService.updateUsersInSetup(userList);
//        }
//
//        // 记录所有事件的日志 （异步执行）
//        newRouter.rule().handler(this.logHandler).next();
//
//        // 接收客服会话管理事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION).handler(this.kfSessionHandler).end();
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION).handler(this.kfSessionHandler).end();
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION).handler(this.kfSessionHandler).end();
//
//        // 门店审核事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();
//
//        // 自定义菜单事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.CLICK).handler(this.getMenuHandler()).end();
//
//        // 点击菜单连接事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.VIEW).handler(this.nullHandler).end();
//
//        // 关注事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(this.getSubscribeHandler()).end();
//
//        // 取消关注事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE).handler(this.getUnsubscribeHandler()).end();
//
//        // 上报地理位置事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.LOCATION).handler(this.getLocationHandler()).end();
//
//        // 接收地理位置消息
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(this.getLocationHandler()).end();
//
//        // 扫码事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(this.getScanHandler()).end();
//
//        // 默认
//        newRouter.rule().async(false).handler(this.getMsgHandler()).end();
//
//        return newRouter;
//    }
//
//    protected MenuHandler getMenuHandler() {
//        return this.menuHandler;
//    }
//
//    protected SubscribeHandler getSubscribeHandler() {
//        return this.subscribeHandler;
//    }
//
//    protected UnsubscribeHandler getUnsubscribeHandler() {
//        return this.unsubscribeHandler;
//    }
//
//    protected AbstractHandler getLocationHandler() {
//        return this.locationHandler;
//    }
//
//    protected MsgHandler getMsgHandler() {
//        return this.msgHandler;
//    }
//
//    protected AbstractHandler getScanHandler() {
//        return null;
//    }
//}

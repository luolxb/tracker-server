///*******************************************************************************
// * @(#)SubscribeHandler.java 2018-03-02
// *
// * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.handler;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import TextBuilder;
//import Dict;
//import me.chanjar.weixin.common.api.WxConsts;
//import me.chanjar.weixin.common.error.WxErrorException;
//import me.chanjar.weixin.common.session.WxSessionManager;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
//import me.chanjar.weixin.mp.bean.result.WxMpUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * <p>
// *     关注事件处理
// * </p>
// *
// * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
// * @version igs 1.0 $ 2018-03-02 11:40
// */
//@Component
//public class SubscribeHandler extends AbstractHandler {
//
//
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
//
//        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());
//
//        // 获取微信用户基本信息
//        WxMpUser userWxInfo = wxMpService.getUserService()
//                .userInfo(wxMessage.getFromUser(), null);
//
//        // wxMpService.getRequestHttp().getRequestType()
//        // wxMessage.getVerifyCode()
//
//
//        // WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
//
//        // WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
//
//        if (userWxInfo != null) {
//            // 搜索是否已经存在该用户
//            QueryWrapper<MaUser> dictWrapper = new QueryWrapper<>();
//            dictWrapper.eq("union_id",userWxInfo.getUnionId());
//            List<MaUser> users = userService.list(dictWrapper);
//
//            // 不存在的时候，新建一个用户
//            if (users == null || users.size() == 0) {
//                if (userWxInfo.getUnionId() != null) {
//                    MaUser newUser = new MaUser();
//                    newUser.setUnionId(userWxInfo.getUnionId());
//                    newUser.setMpOpenId(userWxInfo.getOpenId());
////                    newUser.setName(userWxInfo.getNickname());
//                    newUser.setIcon(userWxInfo.getHeadImgUrl());
//                    // 新建用户
//                    userService.saveOrUpdate(newUser);
//                } else {
////                    reGetUnionId();
//                    // 弹出授权
//                    String redirectUri = "http://rnstec.mynatapp.cc/igs/wechat/portal/auth";
//                    String href = "<a href=\"" + wxMpService.oauth2buildAuthorizationUrl(
//                            redirectUri,
//                            WxConsts.OAuth2Scope.SNSAPI_USERINFO, null) + "\">点击获取授权</a>";
//                    return WxMpXmlOutMessage.TEXT().content(href)
//                            .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
//                            .build();
//                }
//            }
//        } else {
//            return new TextBuilder().build("获取微信用户基本信息失败，请重新关注并联系客服！", wxMessage, wxMpService);
//        }
//
//        WxMpXmlOutMessage responseResult = null;
//        try {
//            responseResult = handleSpecial(wxMessage);
//        } catch (Exception e) {
//            this.logger.error(e.getMessage(), e);
//        }
//
//        if (responseResult != null) {
//            return responseResult;
//        }
//
//        try {
//            return new TextBuilder().build("欢迎关注辽宁瑞内森", wxMessage, wxMpService);
//        } catch (Exception e) {
//            this.logger.error(e.getMessage(), e);
//        }
//
//        return null;
//    }
//
//
//
//    /**
//     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
//     */
//    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
//        //TODO
//        return null;
//    }
//
//
//
//}
///*******************************************************************************
// * @(#)UnsubscribeHandler.java 2018-03-02
// *
// * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.handler;
//
//import me.chanjar.weixin.common.error.WxErrorException;
//import me.chanjar.weixin.common.session.WxSessionManager;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * <p>
// *     取消关注事件处理
// * </p>
// *
// * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
// * @version igs 1.0 $ 2018-03-02 11:41
// */
//@Component
//public class UnsubscribeHandler extends AbstractHandler {
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
//
//        String openId = wxMpXmlMessage.getFromUser();
//        this.logger.info("取消关注用户 OPENID: " + openId);
//        // TODO 可以更新本地数据库为取消关注状态
//
//        return null;
//    }
//}

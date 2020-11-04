///*******************************************************************************
// * @(#)MenuHandler.java 2018-03-02
// *
// * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.handler;
//
//import me.chanjar.weixin.common.api.WxConsts;
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
// * </p>
// *
// * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
// * @version igs 1.0 $ 2018-03-02 11:28
// */
//@Component
//public class MenuHandler extends AbstractHandler {
//
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
//        String msg = String.format("type:%s, event:%s, key:%s",
//                wxMpXmlMessage.getMsgType(), wxMpXmlMessage.getEvent(),
//                wxMpXmlMessage.getEventKey());
//        if (WxConsts.MenuButtonType.VIEW.equals(wxMpXmlMessage.getEvent())) {
//            return null;
//        }
//
//        return WxMpXmlOutMessage.TEXT().content(msg).fromUser(wxMpXmlMessage.getToUser()).toUser(wxMpXmlMessage.getFromUser()).build();
//    }
//
//}

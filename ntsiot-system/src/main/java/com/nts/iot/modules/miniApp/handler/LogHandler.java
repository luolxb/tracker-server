///*******************************************************************************
// * @(#)LogHandler.java 2018-03-02
// *
// * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.handler;
//
//import JsonUtil;
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
// * @version igs 1.0 $ 2018-03-02 11:26
// */
//@Component
//public class LogHandler extends AbstractHandler {
//
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
//        this.logger.info("\n接收到请求消息，内容：{}", JsonUtil.toJson(wxMpXmlMessage));
//        return null;
//    }
//
//}

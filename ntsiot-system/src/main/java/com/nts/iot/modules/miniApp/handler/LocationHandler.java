///*******************************************************************************
// * @(#)LocationHandler.java 2018-03-02
// *
// * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.handler;
//
//import TextBuilder;
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
// * @version igs 1.0 $ 2018-03-02 11:22
// */
//@Component
//public class LocationHandler extends AbstractHandler {
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
//
//        if (wxMpXmlMessage.getMsgType().equals(WxConsts.XmlMsgType.LOCATION)) {
//            //TODO 接收处理用户发送的地理位置消息
//            try {
//                String content = "感谢反馈，您的的地理位置已收到！";
//                return new TextBuilder().build(content, wxMpXmlMessage, null);
//            } catch (Exception e) {
//                this.logger.error("位置消息接收处理失败", e);
//                return null;
//            }
//        }
//
//        //上报地理位置事件
//        this.logger.info("\n上报地理位置 。。。 ");
//        this.logger.info("\n纬度 : " + wxMpXmlMessage.getLatitude());
//        this.logger.info("\n经度 : " + wxMpXmlMessage.getLongitude());
//        this.logger.info("\n精度 : " + String.valueOf(wxMpXmlMessage.getPrecision()));
//
//        //TODO  可以将用户地理位置信息保存到本地数据库，以便以后使用
//
//        return null;
//    }
//}

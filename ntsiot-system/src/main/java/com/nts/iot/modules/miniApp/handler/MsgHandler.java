///*******************************************************************************
// * @(#)MsgHandler.java 2018-03-02
// *
// * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.handler;
//
//import TextBuilder;
//import JsonUtil;
//import me.chanjar.weixin.common.api.WxConsts;
//import me.chanjar.weixin.common.error.WxErrorException;
//import me.chanjar.weixin.common.session.WxSessionManager;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
//import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * <p>
// * </p>
// *
// * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
// * @version igs 1.0 $ 2018-03-02 11:34
// */
//@Component
//public class MsgHandler extends AbstractHandler {
//    @Override
//    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService weixinService, WxSessionManager wxSessionManager) throws WxErrorException {
//        if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
//            //TODO 可以选择将消息保存到本地
//        }
//
//        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
//        try {
//            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服") && weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
//                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
//            }else if (StringUtils.startsWithAny(wxMessage.getContent(), "test")){
//                return new TextBuilder().build("说 ", wxMessage, weixinService);
//            }
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//
//        //TODO 组装回复消息
//        String content = "收到信息内容：" + JsonUtil.toJson(wxMessage);
//        return new TextBuilder().build(content, wxMessage, weixinService);
//    }
//}

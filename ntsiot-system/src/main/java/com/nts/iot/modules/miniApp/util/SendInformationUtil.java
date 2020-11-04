///*******************************************************************************
// * @(#)SendInformationUtil.java 2017-12-16
// *
// * Copyright 2017 Liaoning RNS Technology Co., Ltd.All rights reserved.
// * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *******************************************************************************/
//package com.rnstec.cyoubike.modules.miniApp.util;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.Random;
//
///**
// * <p>
// * 发送验证码
// * </p>
// * @author <a href="mailto:cej@rnstec.com">张晓宇</a>
// * @version xxl-job 1.0 $ 2018-03-08
// */
//@Configuration
//public class SendInformationUtil {
//
//    @Autowired
//    private SendMessage sendMessage;
//
//
//    /**
//     * <p>
//     * 发送验证码
//     * </p>
//     * @param  phone 手机号
//     * @param  verification 验证码
//     */
//    public  void sendInformation(String phone,String verification) {
//        RestTemplate restTemplate = new RestTemplate();
//        Sms sms = new Sms();
//        // 设置手机号
//        sms.addMobile(phone);
//        // 设置短信标题
//        sms.setSignature("【智能加油站】");
//        // 设置验证码信息
//        sms.setMsg("验证码："+ verification);
//        // 短信发送服务url
//        sms.setUrl(sendMessage.getUrl());
//        // 服务账号
//        sms.setAccount(sendMessage.getAccount());
//        // 服务密码
//        sms.setPswd(sendMessage.getPwd());
//        // 值设置
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.set("account", sms.getAccount());
//        params.set("pswd", sms.getPswd());
//        params.set("mobile", sms.getStrMobile());
//        params.set("msg", sms.getFullMsg());
//        params.set("needstatus", "" + false);
//        // 发送验证码
//        UriComponents components = UriComponentsBuilder.fromHttpUrl(sms.getUrl()).queryParams(params).build();
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(components.toUri().toString(), String.class);
//
//    }
//
//    /*  *
//     * <p>
//     * 发送验证码测试main方法
//     * </p>
//     */
//    public static void main(String[] args){
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 验证码
//        int code;
//        Random ne= new  Random();
//        // 为变量赋随机值1000-9999
//        code=ne.nextInt(9999-1000+1)+1000;
//
//        Sms sms = new Sms();
//        sms.addMobile("XXXXXXXXX");
//        sms.setSignature("【智能加油站】");
//        sms.setMsg("验证码："+ code);
//        sms.setUrl("http://222.73.117.169/msg/HttpBatchSendSM");
//        sms.setAccount("N5133041");
//        sms.setPswd("Psdc1a02");
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.set("account", sms.getAccount());
//        params.set("pswd", sms.getPswd());
//        params.set("mobile", sms.getStrMobile());
//        params.set("msg", sms.getFullMsg());
//        params.set("needstatus", "" + false);
//        // 发送验证码
//        UriComponents components = UriComponentsBuilder.fromHttpUrl(sms.getUrl()).queryParams(params).build();
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(components.toUri().toString(), String.class);
//    }
//
//}
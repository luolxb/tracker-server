/*******************************************************************************
 * @(#)WechatPayProperties.java 2018-03-02
 *
 * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.config.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version igs 1.0 $ 2018-03-02 11:12
 */
//@Configuration
@ConfigurationProperties(prefix = "wechat.pay")
public class WechatPayProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

//    /**
//     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
//     */
//    private String subAppId;
//
//    /**
//     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
//     */
//    private String subMchId;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

//    public String getSubAppId() {
//        return subAppId;
//    }
//
//    public void setSubAppId(String subAppId) {
//        this.subAppId = subAppId;
//    }
//
//    public String getSubMchId() {
//        return subMchId;
//    }
//
//    public void setSubMchId(String subMchId) {
//        this.subMchId = subMchId;
//    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

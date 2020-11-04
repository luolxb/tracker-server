/*******************************************************************************
 * @(#)ResultCode.java 2018-03-08
 *
 * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.message;

/**
 * <p>
 *     响应码枚举，参考HTTP状态码的语义
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version igs 1.0 $ 2018-03-08 10:20
 */
public enum ResultCode {

    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500),//服务器内部错误
    DATA_REPEAT(701),//数据库中已存在，数据重复。
    CODE_WRONG(702),//输入验证码错误
    UNIONID_WRONG(703),//通过unionId没有查询到正确的用户信息，或者查询到多条匹配信息。
    CODE_SUCCESS(704),//验证码发送成功
    CODE_FAIL(705),//验证码发送失败
    PARAM_NULL(706),//参数为空。
    PARAM_NOTFORMAT(707),//参数格式不对。
    DATA_NOT_EXIST(708),//数据不存在
    WX_PAYORDER_SUCCESS(709),//微信支付统一下单成功
    SIGN_FAIL(710);//通信加密签名验证错误
    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

}

/*******************************************************************************
 * @(#)SHA1Util.java 2018-05-21
 *
 * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.util;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * SHA1Util前后台加密算法
 * </p>
 *
 * @author <a href="mailto:cej@rnstec.com">迟恩军</a>
 * @version igs 1.0 $ 2018-05-21 10:35
 */
@Component
public class SHA1Util {

    @Autowired
    //private SignService signService;

    private static final String  PRIVATE_KEY = "d16616e795f44458b211f29fe989098e";
    /**
     * 根据key验证签名
     *
     * @param sign 签名
     * @param code 唯一标识
     * @param time 时间戳
     * @param publicKey  公钥
     * @return 0 成功，1 重复提交 ，2 操作失败
     */
    public Integer checkSign(String sign, String code, String time, String publicKey) {
        // 先根据code和time验证是否重复提交
        Map<String, Object> data = new HashMap<>();
        data.put("code", code);
        data.put("time", time);
        //Boolean flag = signService.find(data);

        // flag=true 重复提交，  flag=false  通过
//        if (flag) {
//            return 1;
//        }
        // 获得生成签名的key
        String keyStr = code + time + publicKey + PRIVATE_KEY;

        // sha1进行加密
        String thisSign = SecureUtil.sha1(keyStr);

        // 验证签名是否一致
        if (!thisSign.equals(sign)) {
            return 2;
        }

//        SignEntity signEntity = new SignEntity();
//        signEntity.setCode(code);
//        signEntity.setTime(time);
//        // 验证成功插入数据库
//        signService.insert(signEntity);

        return 0;
    }
}
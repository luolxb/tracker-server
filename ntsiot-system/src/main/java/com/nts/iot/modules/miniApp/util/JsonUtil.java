/*******************************************************************************
 * @(#)JsonUtil.java 2018-03-02
 *
 * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.util;

import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version igs 1.0 $ 2018-03-02 11:28
 */
public class JsonUtil {

    public static String toJson(Object obj) {
        return WxMpGsonBuilder.create().toJson(obj);
    }

}

package com.nts.iot.util;

import cn.hutool.cache.impl.FIFOCache;

/**
 * @program: ntsiot-jt808-server
 * @description: 缓存
 * @author: wongshan
 * @create: 2019-08-01 16:30
 **/
public class MyCacheUtil {
    /**
     * 初始化10000容量
     */
    public static FIFOCache<String, String> fifoCache = cn.hutool.cache.CacheUtil.newFIFOCache(10000);
}

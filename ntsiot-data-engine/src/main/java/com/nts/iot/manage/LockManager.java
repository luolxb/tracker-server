/*******************************************************************************
 * @(#)LockManager.java 2019-06-18
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.manage;

import cn.hutool.http.HttpRequest;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectLock;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-06-18 14:17
 */
@Component
public class LockManager {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${api.server.url}")
    private String url;

    /**
     * 从kafka中获得消息体
     *
     * @param message
     */
    public void addLock(CollectLock message) {

        Map<String, Object> param = new HashMap<>();
        param.put("deviceNo", message.getDeviceNo());
        param.put("simId", message.getSimId());
        param.put("mac", message.getMac());
        param.put("lockNo", message.getLockNo());
        param.put("key", "QAZwsxQWEasd");

        System.out.println(url + "/ma/lock/add");

        HttpRequest.post(url + "/ma/lock/add").form(param).execute().body();

        // 从缓存中获得锁的编码
        String locks = redisUtil.getData(RedisKey.TRACKER_LIST_KEY);

        if (locks != null && !"".equals(locks)) {
            // 转换成集合结构
            List<String> lockList = JsonUtil.jsonConvertList(locks, String.class);
            // 如果消息队列中锁没有在这个集合中，标明是新增加的设备，所以需要插入到数据库中
            if (!lockList.contains(message.getDeviceNo())) {
                // 将新增的锁的编号加入缓存
                lockList.add(message.getDeviceNo());
                redisUtil.addRedis(RedisKey.TRACKER_LIST_KEY, JsonUtil.getJson(lockList));
            }
        } else {
            List<String> lockList = new ArrayList<>();
            lockList.add(message.getDeviceNo());
            redisUtil.addRedis(RedisKey.TRACKER_LIST_KEY, JsonUtil.getJson(lockList));
        }
    }

}

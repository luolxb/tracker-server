package com.nts.iot.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 增删改查模板
 */
@Component
public class RedisUtil {
    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 添加缓存
     *
     * @param key
     * @return
     */
    public Boolean addRedis(String key, String value) {
        Boolean flag = true;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            System.out.println("添加redis失败！");
        }
        return flag;
    }


    /**
     * 添加缓存（加上有效期）
     *
     * @param key
     * @return
     */
    public Boolean addRedis(String key, String value,  long time) {
        Boolean flag = true;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value,time,TimeUnit.SECONDS);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            System.out.println("添加redis失败！");
        }
        return flag;
    }


    /**
     * 根据key，删除缓存
     *
     * @param key 主键
     */
    public Boolean deleteByKey(String key) {
        Boolean flag = true;
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            System.out.println("根据redisKey，删除redis失败！");
        }
        return flag;
    }

    /**
     * 修改缓存
     *
     * @param key
     * @return
     */
    public Boolean updateRedis(String key, String value) {
        Boolean flag = true;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            System.out.println("修改redis失败！");
        }
        return flag;
    }

    /**
     * 修改缓存(加有效期)
     *
     * @param key
     * @return
     */
    public Boolean updateRedis(String key, String value,long time) {
        Boolean flag = true;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value,time,TimeUnit.SECONDS);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            System.out.println("修改redis失败！");
        }
        return flag;
    }

    /**
     * 获得缓存中的数据
     *
     * @param key
     * @return
     */
    public String getData(String key) {
        String data = "";
        try {
            //根据接口名称去缓存中查询
            data = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis获取数据失败！");
        }
        return data;
    }
    /**
     * 获得缓存中的数据
     *
     * @param key
     * @return
     */
    public Object getObject(String key) {
        return key == null ? null : JSON.parseObject(redisTemplate.opsForValue().get(key));
    }

    /**
     * 模糊查询key
     * @param prefix
     * @return
     */
    public Set<String> keys(String prefix){
        Set sets = redisTemplate.keys(prefix + "*");
        return sets;
    }

    /**
     * 通过keys查找
     * @param keys
     * @return
     */
    public Set<Object> getObjectsByKeys(Set<String> keys){
        Set<Object> sets = new HashSet<>();
        for (String key : keys) {
            Object obj = JSON.parseObject(redisTemplate.opsForValue().get(key));
            sets.add(obj);
        }
        return sets;
    }
    /**
     * 查询是否有key
     * @param prefix
     * @return
     */
    public boolean hasKey(String prefix){
        return redisTemplate.hasKey(prefix);
    }



}

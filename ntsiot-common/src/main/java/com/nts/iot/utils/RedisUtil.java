package com.nts.iot.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
     * 添加缓存
     *
     * @param key
     * @param value
     * @param timeout 缓存时间，单位秒
     * @return
     */
    public Boolean addRedis(String key, String value, int timeout) {
        Boolean flag = true;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value, Duration.ofSeconds(timeout));
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
     * 模糊查询redis 中的值
     *
     * @param key
     * @return
     */
    public List<String> fuzzySearchRedis(String key) {
        List<String> list = null;
        try {
            Set<String> keys = redisTemplate.keys(key);
            if (keys != null && keys.size() > 0) {
                list = new ArrayList<>(keys);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis模糊查询数据失败！");
        }
        return list;
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
     *
     * @param prefix
     * @return
     */
    public Set<String> keys(String prefix) {
        Set sets = redisTemplate.keys(prefix + "*");
        return sets;
    }

    /**
     * 通过keys查找
     *
     * @param keys
     * @return
     */
    public Set<Object> getObjectsByKeys(Set<String> keys) {
        Set<Object> sets = new HashSet<>();
        for (String key : keys) {
            Object obj = JSON.parseObject(redisTemplate.opsForValue().get(key));
            sets.add(obj);
        }
        return sets;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间
     * @param timeUnit 类型
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, (String) value, time, timeUnit);
            } else {
                //set(key, (String)value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, (String)value, time, TimeUnit.SECONDS);
            } else {
                //
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

package com.nts.iot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis 队列
 */
@Component
public class ListUtil {
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    public String get(String key, int index) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        return operations.index(key, index);
    }

    public List<String> get(String key, long start, long end) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        return operations.range(key, start, end);
    }

    public void set(String key, int index, String val) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        operations.set(key, index, val);
    }

    public void leftPush(String key, String... vals) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        operations.leftPushAll(key, vals);
    }

    public void rightPush(String key, String... vals) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        operations.rightPushAll(key, vals);
    }

    public void remove(String key, int index, String val) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        operations.remove(key, index, val);
    }

    public void trim(String key, long start, long end) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        operations.trim(key, start, end);
    }

    public String leftPop(String key) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        return operations.leftPop(key);
    }

    public String leftPop(String key, long maxTime, TimeUnit unit) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        return operations.leftPop(key, maxTime, unit);
    }

    public String rightPop(String key) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        return operations.rightPop(key);
    }

    public String rightPop(String key, long maxTime, TimeUnit unit) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        return operations.rightPop(key, maxTime, unit);
    }

    public long size(String key) {
        ListOperations<String, String> operations = redisTemplate.opsForList();
        return operations.size(key);
    }
}
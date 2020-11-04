package com.nts.iot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-03 10:21
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
        try {
            redisTemplate.opsForValue().set(key, value);
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
        try {
            redisTemplate.opsForValue().set(key, value);
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
        try{
            Set<String> keys = redisTemplate.keys(key);
            if (keys != null && keys.size() > 0) {
                list = new ArrayList<>(keys);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("redis模糊查询数据失败！");
        }
        return list;
    }
}

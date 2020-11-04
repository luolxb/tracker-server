package com.nts.iot.modules.monitor.service.impl;

import com.nts.iot.modules.monitor.service.RedisService;
import com.nts.iot.modules.monitor.domain.vo.RedisVo;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-10
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    JedisPool pool;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Page findByKey(String key, Pageable pageable){
//        Jedis jedis = null;
        try{
//            jedis = pool.getResource();
            List<RedisVo> redisVos = new ArrayList<>();

            if(!key.equals("*")){
                key = "*" + key + "*";
            }
            for (String s : redisUtil.keys(key)) {
                RedisVo redisVo = new RedisVo(s,redisUtil.getData(s));
                redisVos.add(redisVo);
            }
            Page<RedisVo> page = new PageImpl<RedisVo>(
                    PageUtil.toPage(pageable.getPageNumber(),pageable.getPageSize(),redisVos),
                    pageable,
                    redisVos.size());
            return page;
        }finally{
//            if(null != jedis){
//                jedis.close(); // 释放资源还给连接池
//            }
        }
    }

    @Override
    public void save(RedisVo redisVo) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.set(redisVo.getKey(),redisVo.getValue());
        }finally{
            if(null != jedis){
                jedis.close(); // 释放资源还给连接池
            }
        }
    }

    @Override
    public void delete(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.del(key);
        }finally{
            if(null != jedis){
                jedis.close(); // 释放资源还给连接池
            }
        }

    }

    @Override
    public void flushdb() {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.flushAll();
        }finally{
            if(null != jedis){
                jedis.close(); // 释放资源还给连接池
            }
        }

    }
}

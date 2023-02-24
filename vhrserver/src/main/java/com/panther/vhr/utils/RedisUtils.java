package com.panther.vhr.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 22:21
 **/
public class RedisUtils {

    @Resource
    private static RedisTemplate<String,Object> redisTemplate;

    public static void addCache(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public static Object getCache(String key){
        if(!StringUtils.hasText(key)){
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

}

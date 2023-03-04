package com.panther.vhr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 22:40
 **/
@Configuration
public class MyRedisConfig {

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        // 设置key的序列化方式
//        template.setKeySerializer(RedisSerializer.string());
//        // 设置value的序列化方式
//        template.setValueSerializer(RedisSerializer.json());
//        // 设置hash的key的序列化方式
//        template.setHashKeySerializer(RedisSerializer.string());
//        // 设置hash的value的序列化方式
//        template.setHashValueSerializer(RedisSerializer.json());
//        template.afterPropertiesSet();
//        return template;
//    }

}

package com.qmlx.usercenter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testRedis(){
        //操作redis,这里只操作字符串类型
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //增
        valueOperations.set("username","狗");
        //修改
        valueOperations.set("username","dog");
        //查询
        String username = (String)valueOperations.get("username");
        System.out.println(username);

    }
}

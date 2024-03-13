package com.qmlx.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qmlx.usercenter.mapper.UserMapper;
import com.qmlx.usercenter.model.domain.User;
import com.qmlx.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 定时向redis中增加缓存-缓存预热
 * @author QMLX9999
 */
@Component
public class PreCacheJob {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private UserMapper userMapper;
    @Scheduled(cron = "0 10 8,14 * * ? ")//每天的八点10和中午二点10预热一次
    public void doCacheRecommendUser(){
        //这个功能 只针对特殊用户，因为大多数用户为虚假的
        long userId=1;
        String redisKey=String.format("soulmatch:qmlx:recommend:%s",userId);
        Page<User> page = new Page<>(1,20);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        Page<User> userPageResult = userMapper.selectPage(page, wrapper);

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(redisKey,userPageResult,2, TimeUnit.HOURS);
    }

}

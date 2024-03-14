package com.qmlx.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qmlx.usercenter.mapper.UserMapper;
import com.qmlx.usercenter.model.domain.User;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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

    @Autowired
    private RedissonClient redissonClient;

    @Scheduled(cron = "0 10 8,14 * * ? ")//每天的八点10和中午二点10预热一次
    public void doCacheRecommendUser(){
        //实现分布式锁
        //实现枪锁机制
        RLock lock = redissonClient.getLock("soulmatch:qmlx:precachejob:docache:lock");

        try {
            //如果抢到了锁，执行定时任务，这里设置等待时间为0，就是说只能抢一次
            if(lock.tryLock(0,30000,TimeUnit.MICROSECONDS)){
                //这个功能 只针对特殊用户，因为大多数用户为虚假的
                long userId=1;
                String redisKey=String.format("soulmatch:qmlx:recommend:%s",userId);
                Page<User> page = new Page<>(1,20);
                LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
                Page<User> userPageResult = userMapper.selectPage(page, wrapper);
                ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                try {
                    //将数据写入缓存
                    valueOperations.set(redisKey,userPageResult,2, TimeUnit.HOURS);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            //注意 释放锁的逻辑一定要加到finally中，不然中途程序出问题的时候，锁就不会被释放
            //并且需要注意只能释放自己的锁,判断是自己的锁在释放
            //还有关于续期机制也就是说任务没执行完但是锁被释放了，这部分redis的看门狗机制会自动实现
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }


    }

}

package com.qmlx.usercenter.service;


import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedissionTest {
    @Autowired
    private RedissonClient redissonClient;
    @Test
    void test() {
        ArrayList<String> list = new ArrayList<>();
        list.add("qmlx");
        System.out.println(list.get(0));

        //这里其实就相当于指定一个redis的key
        RList<String> rList = redissonClient.getList("test-rlist");
        rList.add("dog");
        rList.add("dogs");
        List<String> stringList = rList.get(0,1);
        System.out.println(stringList);
        rList.remove(0);
        List<String> stringList1 = rList.readAll();

    }


}

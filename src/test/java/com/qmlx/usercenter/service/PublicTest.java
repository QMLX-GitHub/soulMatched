package com.qmlx.usercenter.service;

import com.qmlx.usercenter.mapper.UserMapper;
import com.qmlx.usercenter.model.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class PublicTest {

    @Resource
    private UserMapper userMapper;
    @Test
    void test(){
        User user = userMapper.selectById(1);
        System.out.println(user.getSelfIntroduction());
    }

    @Test
    void tests(){
        String sr="";
        boolean notBlank = StringUtils.isNotBlank(sr);
        System.out.println(notBlank);
    }

}

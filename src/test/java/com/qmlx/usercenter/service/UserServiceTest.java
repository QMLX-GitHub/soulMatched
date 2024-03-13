package com.qmlx.usercenter.service;

// [编程学习交流圈](https://www.code-nav.cn/) 连接万名编程爱好者，一起优秀！20000+ 小伙伴交流分享、40+ 大厂嘉宾一对一答疑、100+ 各方向编程交流群、4000+ 编程问答参考

import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.qmlx.usercenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 用户服务测试
 *
 * @author <a href="https://github.com/liqmlx">程序员鱼皮</a>
 * @from <a href="https://qmlx.icu">编程导航知识星球</a>
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void test(){
        //["java","python","女"]
        List<String> stringList=new ArrayList<>();
        String[] arr={"java","python","女"};
        for (String s : arr) {
            stringList.add(s);
        }
        System.out.println(stringList.toString());
        System.out.println(arr.toString());
    }
    @Test
    public void testSearchUserByTags(){
        List<String> tagsName= Arrays.asList("java","python");
        List<User> users = userService.searchUserByTags(tagsName);
        Assert.assertNotNull(users);
    }
    /**
     * 测试添加用户
     */
    @Test
    public void testAddUser() {
        List<User> userList=new ArrayList<>();
        Random random = new Random();
        List<String> imageUrlList = new ArrayList<>();
        // Read image URLs from a file and add them to the list
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\桌面\\output.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                imageUrlList.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        //["java","python","女","考研","工作"]
        // Print all image URLs from the list
        for (String imageUrl : imageUrlList) {
            User user = new User();
            user.setUsername("dogqmlx");
            user.setUserAccount("qmlx");
            user.setAvatarUrl(imageUrl);
            user.setGender(0);
            user.setUserPassword("ed5427105cfde31c2d965716304a6066");
            user.setPhone("17194598767");
            user.setEmail("77777777@163.com");
            user.setTags("[\"java\",\"python\",\"女\",\"考研\",\"工作\"]");
            int nextInt = random.nextInt(100);
            user.setPlanetCode(nextInt+"-"+11);
            userList.add(user);
            userService.save(user);
        }

    }

    // https://www.code-nav.cn/

    /**
     * 测试更新用户
     */
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("dogqmlx");
        user.setUserAccount("123");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/logo.png");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    /**
     * 测试删除用户
     */
    @Test
    public void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    // https://space.bilibili.com/12890453/

    /**
     * 测试获取用户
     */
    @Test
    public void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    /**
     * 测试用户注册
     */
    @Test
    void userRegister() {
        String userAccount = "qmlx";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "qmlx";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "dogqmlx";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "qmlx";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
    }
}
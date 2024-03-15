package com.qmlx.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qmlx.usercenter.model.domain.Team;
import com.qmlx.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qmlx.usercenter.model.dto.TeamQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 * @author <a href="https://github.com/liqmlx">程序员鱼皮</a>
 * @from <a href="https://qmlx.icu">编程导航知识星球</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码

     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     * @param tagNameList 用户标签
     * @return
     */
    List<User> searchUserByTags(List<String> tagNameList);


    /**
     * 根据用户信息更新用户
     * @param user
     * @return
     */
    Integer updateUser(User user,HttpServletRequest request);

    /**
     * 获取当前用户登录信息
     * @param request
     * @return
     */
    User getCurrentLoginUser(HttpServletRequest request);

    /**
     * 分页查询用户信息
     * @param pageSize
     * @param pageNum
     * @param request
     * @return
     */
    Page<User> recommendUser(long pageNum,long pageSize,HttpServletRequest request);








}

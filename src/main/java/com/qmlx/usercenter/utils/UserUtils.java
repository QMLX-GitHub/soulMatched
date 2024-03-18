package com.qmlx.usercenter.utils;

import com.qmlx.usercenter.common.ErrorCode;
import com.qmlx.usercenter.contant.UserConstant;
import com.qmlx.usercenter.exception.BusinessException;
import com.qmlx.usercenter.model.domain.User;
import com.qmlx.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class UserUtils {

    private static UserService userService;


    @Autowired
    public UserUtils(UserService userService) {
        UserUtils.userService = userService;
    }

    public static long getCurrentUserId(HttpServletRequest request){
        Object objUser = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user=(User) objUser;
        if(user==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return user.getId();
    }

    public static User getCurrentUser(HttpServletRequest request){
        Object objUser = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user=(User) objUser;
        Long id = user.getId();
        User loginUser = userService.getById(id);
        if(loginUser==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return loginUser;
    }
}

package com.qmlx.usercenter.utils;

import com.qmlx.usercenter.common.ErrorCode;
import com.qmlx.usercenter.contant.UserConstant;
import com.qmlx.usercenter.exception.BusinessException;
import com.qmlx.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

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
        if(user==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return user;
    }
}

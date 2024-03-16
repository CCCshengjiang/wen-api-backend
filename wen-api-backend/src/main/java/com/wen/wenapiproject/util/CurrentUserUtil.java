package com.wen.wenapiproject.util;

import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.constant.UserConstant;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 得到当前登录用户信息工具类
 *
 * @author wen
 */
public class CurrentUserUtil {
    /**
     * 构造方法私有化
     */
    private CurrentUserUtil(){}

    /**
     * 从缓存中得到当前登录用户的完整信息
     *
     * @param request 请求信息
     * @return 返回完整的用户信息
     */
    public static User getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "请求信息为空");
        }
        // 获取当前登录的用户信息
        User currentUser = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATUS);
        if (currentUser == null) {
            throw new BusinessException(BaseCode.AUTH_FAILURE, "未登录或登录过期");
        }
        return currentUser;
    }

}

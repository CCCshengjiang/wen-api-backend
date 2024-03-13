package com.wen.wenapiproject.constant;

/**
 * 用户常量
 *
 * @author wen
 */
public class UserConstant {
    // 不允许类被实例化
    private UserConstant() {}
    /**
     * 盐值：混淆密码
     */
    public static final String SALT = "wen";

    /**
     * 用户登录态键
     */
    public static final String USER_LOGIN_STATUS = "userLoginStatus";

    /**
     * 管理员角色
     */
    public static final int ADMIN_ROLE = 1;

    /**
     * 普通用户角色
     */
    public static final int DEFAULT_ROLE = 0;

    /**
     * 用户已经被删除
     */
    public static final int USER_DELETED = 1;
}

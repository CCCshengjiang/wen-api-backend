package com.wen.wenapiproject.model.request.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
/**
 * 用户更新的请求参数包装类
 *
 * @author wen
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像，url地址存储
     */
    private String avatarUrl;

    /**
     * 性别，0-女，1-男
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态，0-正常，1-不正常
     */
    private Integer userStatus;

    /**
     * 用户角色，0-普通用户，1-管理员
     */
    private Integer userRole;

    /**
     * 用户标签 JSON
     */
    private String tags;

    /**
     * 用户自我介绍
     */
    private String userProfile;


    @Serial
    private static final long serialVersionUID = -891131171820974786L;
}

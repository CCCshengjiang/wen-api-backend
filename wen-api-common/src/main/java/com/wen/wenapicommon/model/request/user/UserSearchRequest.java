package com.wen.wenapicommon.model.request.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求体
 *
 * @author wen
 */
@Data
public class UserSearchRequest implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 性别，0-女，1-男
     */
    private Integer gender;

    /**
     * 账号
     */
    private String userAccount;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户角色，0-普通用户，1-管理员
     */
    private Integer userRole;

    /**
     * 用户自我介绍
     */
    private String userProfile;

    @Serial
    private static final long serialVersionUID = 7595114131736591728L;
}

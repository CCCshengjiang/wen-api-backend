package com.wen.wenapiproject.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 脱敏的用户信息封装类
 *
 * @author wen
 */
@Data
public class SafetyUserVO implements Serializable {
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
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户角色，0-普通用户，1-管理员
     */
    private Integer userRole;

    @Serial
    private static final long serialVersionUID = 3552735414898353474L;
}

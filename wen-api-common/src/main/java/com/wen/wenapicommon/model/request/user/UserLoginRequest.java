package com.wen.wenapicommon.model.request.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author wen
 */
@Data
public class UserLoginRequest implements Serializable {
    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    @Serial
    private static final long serialVersionUID = -1369512677071505755L;
}

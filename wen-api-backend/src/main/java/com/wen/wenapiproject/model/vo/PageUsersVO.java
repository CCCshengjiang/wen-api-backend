package com.wen.wenapiproject.model.vo;

import com.wen.wenapicommon.model.domain.User;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 *
 * 分页展示数据的封装类
 *
 * @author wen
 */
@Data
public class PageUsersVO implements Serializable {
    /**
     * 脱敏的用户信息
     */
    private List<SafetyUserVO> safetyUsers;

    /**
     * 用户总量
     */
    private int totalUsers;

    @Serial
    private static final long serialVersionUID = -5845720343836527986L;
}

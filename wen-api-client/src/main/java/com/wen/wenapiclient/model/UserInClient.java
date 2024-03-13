package com.wen.wenapiclient.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author wen
 */
@Data
public class UserInClient implements Serializable {

    /**
     * 用户名
     */
    private String username;

    @Serial
    private static final long serialVersionUID = -8839137331228143193L;
}
package com.wen.wenapicommon.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T> 返回的数据
 * @author wen
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }
    public BaseResponse(int code, String message, String description) {
        this(code, null,message, description);
    }
    public BaseResponse(BaseCode baseCode) {
        this(baseCode.getCode(), null, baseCode.getMessage(), baseCode.getDescription() );
    }

    @Serial
    private static final long serialVersionUID = 4149308429815787164L;
}


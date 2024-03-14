package com.wen.wenapicommon.exception;


import com.wen.wenapicommon.common.BaseCode;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 业务异常类
 *
 * @author wen
 */
@Getter
public class BusinessException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 6702132533487487021L;
    private final int code;
    private final String description;

    /**
     * 业务异常
     *
     * @param baseCode 通用业务状态类
     */
    public BusinessException(BaseCode baseCode) {
        super(baseCode.getMessage());
        code = baseCode.getCode();
        description = baseCode.getDescription();
    }

    /**
     * 业务异常
     *
     * @param code 业务状态码
     * @param message 信息
     * @param description 描述
     */
    public BusinessException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    /**
     * 业务异常
     *
     * @param baseCode 业务状态码类
     * @param description 描述
     */
    public BusinessException(BaseCode baseCode, String description) {
        super(baseCode.getMessage());
        code = baseCode.getCode();
        this.description = description;
    }
}

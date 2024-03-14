package com.wen.wenapicommon.common;

import lombok.Getter;

/**
 * 通用业务状态码
 *
 * @author wen
 */
@Getter
public enum BaseCode {
    SUCCESS(20000, "Success", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    PARAMS_NULL_ERROR(40001, "请求参数为空", ""),
    AUTH_FAILURE(40100, "登陆状态异常", ""),
    INVALID_PASSWORD_ERROR(40102, "无效密码", ""),
    ACCESS_DENIED(40300, "非管理员用户或权限不足", ""),
    RESOURCE_NOT_FOUND(40400, "请求的资源不存在", ""),
    INTERNAL_ERROR(50000, "服务器遇到了一个未知的错误而无法处理请求", ""),
    SERVICE_UNAVAILABLE(50300, "服务器正在维护或者过载。", ""),
    RATE_LIMIT_EXCEEDED(42900, "请求频率超过了预设的阈值。", "");


    private final int code;
    private final String message;
    private final String description;

    BaseCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}

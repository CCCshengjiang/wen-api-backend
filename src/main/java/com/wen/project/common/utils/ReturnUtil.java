package com.wen.project.common.utils;


import com.wen.project.common.BaseCode;
import com.wen.project.common.BaseResponse;

/**
 * 返回工具类
 *
 * @author wen
 */
public class ReturnUtil {
    // 不允许实例化 ReturnUtil 对象
    private ReturnUtil(){}

    /**
     * 成功返回
     *
     * @param data 返回的数据体
     * @return 通用返回类
     * @param <T> 数据泛型
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(20000, data, "success");
    }

    /**
     * 失败返回
     *
     * @param baseCode 返回的业务状态码
     * @return 通用返回类
     * @param <T> 指定泛型
     */
    public static <T> BaseResponse<T> error(BaseCode baseCode) {
        return new BaseResponse<>(baseCode);
    }

    /**
     * 全局异常处理中的失败返回
     *
     * @param code 业务状态吗
     * @param message 信息
     * @param description 描述
     * @return 通用返回类
     */
    public static <T> BaseResponse<T> error(int code, String message, String description) {
        return new BaseResponse<>(code, message, description);
    }

    public static <T> BaseResponse<T> error(BaseCode baseCode, String message) {
        return new BaseResponse<>(baseCode.getCode(), message, baseCode.getDescription());
    }
}

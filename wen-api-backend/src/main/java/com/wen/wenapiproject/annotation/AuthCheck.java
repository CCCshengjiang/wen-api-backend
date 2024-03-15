package com.wen.wenapiproject.annotation;

import java.lang.annotation.*;


/**
 * 自定义管理员权限
 * 运行时、在方法上执行
 *
 * @author wen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {
    int adminRole() default 1;
}

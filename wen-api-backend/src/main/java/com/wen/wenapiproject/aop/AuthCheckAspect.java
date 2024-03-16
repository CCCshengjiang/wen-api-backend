package com.wen.wenapiproject.aop;

import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapiproject.annotation.AuthCheck;
import com.wen.wenapiproject.util.CurrentUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 权限校验 AOP
 *
 * @author wen
 */
@Aspect
@Component
public class AuthCheckAspect {

    /**
     * 校验当前用户是否为管理员
     *
     * @param joinPoint 切点
     * @param authCheck 用户权限检查
     * @return 校验通过就放行
     */
    @Around("@annotation(authCheck)")
    public Object authCheckAdvice(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        HttpServletRequest request;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        } catch (Exception e) {
            throw new BusinessException(BaseCode.RESOURCE_NOT_FOUND, "请求信息为空");
        }
        User currentUser = CurrentUserUtil.getCurrentUser(request);
        if (!currentUser.getUserRole().equals(authCheck.adminRole())) {
            throw new BusinessException(BaseCode.ACCESS_DENIED, "非管理员，无权限");
        }
        // 权限校验通过，放行
        return joinPoint.proceed();
    }

}

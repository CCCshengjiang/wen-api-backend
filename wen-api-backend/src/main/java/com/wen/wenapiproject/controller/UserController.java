package com.wen.wenapiproject.controller;

import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.common.BaseResponse;
import com.wen.wenapicommon.common.utils.ReturnUtil;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.model.request.user.UserLoginRequest;
import com.wen.wenapicommon.model.request.user.UserRegisterRequest;
import com.wen.wenapicommon.model.request.user.UserSearchRequest;
import com.wen.wenapicommon.model.request.user.UserUpdateRequest;
import com.wen.wenapiproject.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户接口
 *
 * @author wen
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:8000/" }, allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求体
     * @return 注册后的用户id
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "输入为空");
        }
        Long id = userService.userRegister(userRegisterRequest);
        return ReturnUtil.success(id);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求体
     * @param request          HTTP请求
     * @return 返回脱敏用户信息
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "输入为空");
        }
        User user = userService.userLogin(userLoginRequest, request);
        return ReturnUtil.success(user);
    }

    /**
     * 获取当前登录的用户信息
     *
     * @param request 前端传递的http请求
     * @return 返回脱敏的用户信息
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        return ReturnUtil.success(currentUser);
    }

    /**
     * 用户退出登录
     *
     * @param request 前端HTTP请求
     * @return 返回
     */
    @PostMapping("logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        Integer i = userService.userLogout(request);
        return ReturnUtil.success(i);
    }

    /**
     * 查询用户
     *
     * @param userSearchRequest 用户查询请求体
     * @return 查询到的脱敏用户列表
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> userSearch(UserSearchRequest userSearchRequest, HttpServletRequest request) {
        if (userSearchRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        // 鉴权
        if (!userService.isAdmin(request)) {
            throw new BusinessException(BaseCode.ACCESS_DENIED);
        }
        // 查询用户
        List<User> users = userService.userSearch(userSearchRequest);
        return ReturnUtil.success(users);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody long id, HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        // 鉴权
        if (userService.isAdmin(request)) {
            throw new BusinessException(BaseCode.ACCESS_DENIED);
        }
        if (id <= 0) {
            throw new BusinessException(BaseCode.RESOURCE_NOT_FOUND, "用户不存在");
        }
        return ReturnUtil.success(userService.removeById(id));
    }

    /**
     * 更新用户信息
     *
     * @param userUpdateRequest 要更新的用户
     * @param request http请求
     * @return 返回更新的用户数量
     */
    @PostMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        // 判断参数是否为空
        if (userUpdateRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        // 修改用户
        int result = userService.updateUser(userUpdateRequest, request);
        return ReturnUtil.success(result);
    }
}

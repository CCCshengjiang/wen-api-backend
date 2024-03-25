package com.wen.wenapiproject.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.common.BaseResponse;
import com.wen.wenapicommon.common.request.DeleteRequest;
import com.wen.wenapicommon.common.utils.ReturnUtil;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.model.request.user.UserLoginRequest;
import com.wen.wenapicommon.model.request.user.UserRegisterRequest;
import com.wen.wenapicommon.model.request.user.UserSearchRequest;
import com.wen.wenapicommon.model.request.user.UserUpdateRequest;
import com.wen.wenapiproject.annotation.AuthCheck;
import com.wen.wenapiproject.model.vo.ApiKeyVO;
import com.wen.wenapiproject.model.vo.SafetyUserVO;
import com.wen.wenapiproject.service.UserService;
import com.wen.wenapiproject.util.ApiKey;
import com.wen.wenapiproject.util.CurrentUserUtil;
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
// @CrossOrigin(origins = { "https://wen-api.cwblue.cn/" }, allowCredentials = "true")
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
    public BaseResponse<SafetyUserVO> userRegister(@RequestBody UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        if (userRegisterRequest == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "输入为空");
        }
        SafetyUserVO user = userService.userRegister(userRegisterRequest, request);
        return ReturnUtil.success(user);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求体
     * @param request          HTTP请求
     * @return 返回脱敏用户信息
     */
    @PostMapping("/login")
    public BaseResponse<SafetyUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "输入为空");
        }
        SafetyUserVO safetyUserVO = userService.userLogin(userLoginRequest, request);
        return ReturnUtil.success(safetyUserVO);
    }

    /**
     * 获取当前登录的用户信息
     *
     * @param request 前端传递的http请求
     * @return 返回脱敏的用户信息
     */
    @GetMapping("/current")
    public BaseResponse<SafetyUserVO> getCurrentUser(HttpServletRequest request) {
        SafetyUserVO currentUser = userService.getCurrentUser(request);
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
    @AuthCheck
    public BaseResponse<List<SafetyUserVO>> userSearch(UserSearchRequest userSearchRequest, HttpServletRequest request) {
        if (userSearchRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        // 查询用户
        List<SafetyUserVO> users = userService.userSearch(userSearchRequest);
        return ReturnUtil.success(users);
    }

    /**
     * 删除用户
     *
     * @param deleteRequest 删除请求参数信息
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    @AuthCheck
    public BaseResponse<Boolean> userDelete(DeleteRequest deleteRequest, HttpServletRequest request) {
        if (request == null || deleteRequest == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        Long id = deleteRequest.getId();
        if (id == null || id <= 0) {
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
    public BaseResponse<Integer> userUpdate(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        // 判断参数是否为空
        if (userUpdateRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        // 修改用户
        int result = userService.updateUser(userUpdateRequest, request);
        return ReturnUtil.success(result);
    }

    /**
     * 当前用户的密钥
     *
     * @param request 请求信息
     * @return 密钥信息
     */
    @GetMapping("/apikey")
    public BaseResponse<ApiKeyVO> getApikey(HttpServletRequest request) {
        // 判断参数是否为空
        if (request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        User user = CurrentUserUtil.getCurrentUser(request);
        // 更新用户信息
        User currentUser = userService.getById(user.getId());
        ApiKeyVO apiKeyVO = new ApiKeyVO();
        apiKeyVO.setAccessKey(currentUser.getAccessKey());
        apiKeyVO.setSecretKey(currentUser.getSecretKey());
        // 修改用户
        return ReturnUtil.success(apiKeyVO);
    }

    /**
     * 重新生成 API 签名
     *
     * @param request 请求信息
     * @return 重新生成的密钥
     */
    @PostMapping("/apikey/change")
    public BaseResponse<ApiKeyVO> changeApikey(HttpServletRequest request) {
        // 判断参数是否为空
        if (request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        User currentUser = CurrentUserUtil.getCurrentUser(request);
        // 重新生成 API 签名
        ApiKeyVO apiKey = ApiKey.getApiKey(currentUser.getUserAccount());
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", currentUser.getId())
                .set("access_key", apiKey.getAccessKey())
                .set("secret_key", apiKey.getSecretKey());
        boolean update = userService.update(updateWrapper);
        if (!update) {
            throw new BusinessException(BaseCode.INTERNAL_ERROR, "用户密钥更新失败");
        }
        return ReturnUtil.success(apiKey);
    }

}

package com.wen.wenapiproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.model.request.user.UserLoginRequest;
import com.wen.wenapicommon.model.request.user.UserRegisterRequest;
import com.wen.wenapicommon.model.request.user.UserSearchRequest;
import com.wen.wenapicommon.model.request.user.UserUpdateRequest;
import com.wen.wenapiproject.model.vo.SafetyUserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户服务接口类
 *
 * @author wen
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册业务层接口
     *
     * @param userRegisterRequest 前端输入的注册信息
     * @return 返回注册的用户id
     */
    SafetyUserVO userRegister(UserRegisterRequest userRegisterRequest, HttpServletRequest request);

    /**
     * 用户登陆的业务层接口
     *
     * @param userLoginRequest 前端输入的用户登录信息
     * @param request 请求
     * @return 返回脱敏的用户信息
     */
    SafetyUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 获取当前登陆的用户信息
     *
     * @param request http请求
     * @return 脱敏的用户信息
     */
    SafetyUserVO getCurrentUser(HttpServletRequest request);

    /**
     * 用户退出登录
     *
     * @param request 请求
     * @return 返回用户id
     */
    Integer userLogout(HttpServletRequest request);


    /**
     * 查询用户接口
     *
     * @param userSearchRequest 用户查询请求体
     * @return 返回脱敏的用户列表
     */
    List<SafetyUserVO> userSearch(UserSearchRequest userSearchRequest);

    /**
     * 修改用户信息的业务接口
     *
     * @param userUpdateRequest 要修改的用户
     * @param request           前端请求
     * @return 返回更新的用户数量
     */
    int updateUser(UserUpdateRequest userUpdateRequest, HttpServletRequest request);

    /**
     * 用户脱敏的业务层接口
     *
     * @param userList 用户列表
     * @return 脱敏的用户信息
     */
    List<SafetyUserVO> getSafetyUser(List<User> userList);

    /**
     * 用户脱敏的业务层接口
     *
     * @param originUser 原始的用户信息
     * @return 脱敏的用户
     */
    SafetyUserVO getSafetyUser(User originUser);
}

package com.wen.wenapiproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.constant.UserConstant;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.model.request.user.UserLoginRequest;
import com.wen.wenapicommon.model.request.user.UserRegisterRequest;
import com.wen.wenapicommon.model.request.user.UserSearchRequest;
import com.wen.wenapicommon.model.request.user.UserUpdateRequest;
import com.wen.wenapiproject.mapper.UserMapper;
import com.wen.wenapiproject.model.vo.ApiKeyVO;
import com.wen.wenapiproject.model.vo.SafetyUserVO;
import com.wen.wenapiproject.service.UserService;
import com.wen.wenapiproject.util.ApiKey;
import com.wen.wenapiproject.util.CurrentUserUtil;
import com.wen.wenapiproject.util.RedissonLockUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * 用户服务实现类
 *
 * @author wen
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    public static final String USER_ACCOUNT = "user_account";
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedissonLockUtil redissonLockUtil;

    /**
     * 用户注册实现类
     *
     * @param userRegisterRequest 前端输入的注册信息
     * @return 返回注册的用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SafetyUserVO userRegister(UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        // 校验输入是否为空
        if (userRegisterRequest == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "注册输入参数为空");
        }
        // 拿到信息
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        // 1.校验输入信息是否完整
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "请求参数为空");
        }
        // 2.长度校验
        if (userAccount.length() < 4 || userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(BaseCode.PARAMS_ERROR, "输入长度不符合要求");
        }
        // 2.账号校验
        // 不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(USER_ACCOUNT, userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(BaseCode.PARAMS_ERROR, "账号已经存在");
        }
        // 不包含特殊字符
        if (!userAccount.matches("^[0-9a-zA-Z]{4,}$")) {
            throw new BusinessException(BaseCode.PARAMS_ERROR);
        }
        // 3.密码
        // 两次密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(BaseCode.PARAMS_ERROR, "两次密码不同");
        }
        String lockName = ("user_register_" + userAccount).intern();
        return redissonLockUtil.redissonDistributedLock(lockName, () -> {
            // 4.对密码进行加密
            String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());
            // 5.向数据库插入用户数据
            User user = new User();
            user.setUserPassword(encryptPassword);
            user.setUserAccount(userAccount);
            // 初始化密钥
            ApiKeyVO apiKey = ApiKey.getApiKey(userAccount);
            user.setAccessKey(apiKey.getAccessKey());
            user.setSecretKey(apiKey.getSecretKey());
            // 设置初始头像和初始用户名
            user.setAvatarUrl("https://img1.baidu.com/it/u=2985396150,1670050748&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1710003600&t=5293756f92c3a6922e0540ee28503bfd");
            user.setUsername(userAccount);
            boolean res = this.save(user);
            if (!res) {
                throw new BusinessException(BaseCode.INTERNAL_ERROR, "注册失败");
            }
            // 注册完之后，自动登录
            UserLoginRequest userLoginRequest = new UserLoginRequest();
            userLoginRequest.setUserAccount(userAccount);
            userLoginRequest.setUserPassword(userPassword);
            return this.userLogin(userLoginRequest, request);
        }, BaseCode.INTERNAL_ERROR, "注册失败");

    }

    /**
     * 用户登陆的实现类
     *
     * @param userLoginRequest 前端输入的用户登录信息
     * @param request          请求
     * @return 返回脱敏的用户信息
     */
    @Override
    public SafetyUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 校验登陆参数是否为空
        if (userLoginRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "输入为空");
        }
        // 1.校验
        //校验参数是否完整
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "请求参数为空");
        }
        //长度校验（账号不小于4位，密码不小于8位）
        if (userAccount.length() < 4 || userPassword.length() < 8) {
            throw new BusinessException(BaseCode.PARAMS_ERROR, "输入长度不符合要求");
        }
        //数据库查询是否有账号
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(USER_ACCOUNT, userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "账号不存在");
        }
        //密码是否正确（密码加密之后相比）
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());
        if (!user.getUserPassword().equals(encryptPassword)) {
            throw new BusinessException(BaseCode.INVALID_PASSWORD_ERROR, "密码错误");
        }
        // 3.记录用户登录态
        CurrentUserUtil.saveUserInSession(user, request);
        // 4.返回脱敏后的用户信息
        return getSafetyUser(user);
    }

    /**
     * 获取当前登录的用户信息
     *
     * @param request http请求
     * @return 返回脱敏的用户信息
     */
    @Override
    public SafetyUserVO getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "请求信息为空");
        }
        User currentUser = CurrentUserUtil.getCurrentUser(request);
        // 防止更新用户之后，返回的还是缓存中的用户信息
        currentUser = userMapper.selectById(currentUser.getId());
        return getSafetyUser(currentUser);
    }

    @Override
    public Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATUS);
        return 1;
    }

    /**
     * 查询用户实现类
     *
     * @param userSearchRequest 用户查询请求体
     * @return 返回脱敏的用户信息
     */
    @Override
    public List<SafetyUserVO> userSearch(UserSearchRequest userSearchRequest) {
        if (userSearchRequest == null) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 根据信息查询,如果信息全部为空，返回所有用户列表
        String userAccount = userSearchRequest.getUserAccount();
        if (userAccount != null) {
            queryWrapper.eq(USER_ACCOUNT, userAccount);
        }
        Integer userRole = userSearchRequest.getUserRole();
        if (userRole != null) {
            queryWrapper.eq("user_role", userRole);
        }
        Integer userStatus = userSearchRequest.getUserStatus();
        if (userStatus != null) {
            queryWrapper.eq("user_status", userStatus);
        }
        String phone = userSearchRequest.getPhone();
        if (phone != null) {
            queryWrapper.like("phone", phone);
        }
        String email = userSearchRequest.getEmail();
        if (email != null) {
            queryWrapper.like("email", email);
        }
        Integer gender = userSearchRequest.getGender();
        if (gender != null) {
            queryWrapper.eq("gender", gender);
        }
        String username = userSearchRequest.getUsername();
        if (username != null) {
            queryWrapper.like("username", username);
        }
        Date createTime = userSearchRequest.getCreateTime();
        if (createTime != null) {
            queryWrapper.lt("create_time", createTime);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        // 用户信息脱敏
        return getSafetyUser(users);
    }

    /**
     * 更新用户的实现
     *
     * @param userUpdateRequest 要修改的用户
     * @param request           前端请求
     * @return 更新的用户数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        // 判空
        if (userUpdateRequest == null || request == null) {
            throw new BusinessException(BaseCode.PARAMS_ERROR);
        }
        // 查询要修改的用户在数据库中是否存在
        User oldUser = userMapper.selectById(userUpdateRequest.getId());
        if (oldUser == null || oldUser.getIsDelete() == UserConstant.USER_DELETED) {
            throw new BusinessException(BaseCode.RESOURCE_NOT_FOUND, "要修改的用户不存在");
        }
        String lockName = ("update_user_" + oldUser.getUserAccount()).intern();
        return redissonLockUtil.redissonDistributedLock(lockName, () ->{
            User updateUser = new User();
            BeanUtils.copyProperties(userUpdateRequest, updateUser);
            int res = userMapper.updateById(updateUser);
            if (res <= 0) {
                throw new BusinessException(BaseCode.INTERNAL_ERROR, "用户信息更新失败");
            }
            // 将 Session 中的用户信息也更新
            CurrentUserUtil.saveUserInSession(updateUser, request);
            return res;
        }, BaseCode.INTERNAL_ERROR, "用户更新失败");
    }

    /**
     * 用户脱敏的实现
     *
     * @param userList 用户列表
     * @return 脱敏的用户信息
     */
    @Override
    public List<SafetyUserVO> getSafetyUser(List<User> userList) {
        if (userList == null) {
            return new ArrayList<>();
        }
        List<SafetyUserVO> safetyUserList = new ArrayList<>();
        for (User userListRecord : userList) {
            safetyUserList.add(getSafetyUser(userListRecord));
        }
        return safetyUserList;
    }

    /**
     * 返回脱敏的用户信息
     *
     * @param originUser 原始的用户信息
     * @return 脱敏的用户
     */
    @Override
    public SafetyUserVO getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        SafetyUserVO safetyUserVO = new SafetyUserVO();
        BeanUtils.copyProperties(originUser, safetyUserVO);
        return safetyUserVO;
    }
}





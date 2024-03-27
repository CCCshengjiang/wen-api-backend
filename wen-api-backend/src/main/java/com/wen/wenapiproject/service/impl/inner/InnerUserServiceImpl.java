package com.wen.wenapiproject.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.service.InnerUserService;
import com.wen.wenapiproject.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户服务实现类
 *
 * @author wen
 */
@DubboService
public class InnerUserServiceImpl extends ServiceImpl<UserMapper, User>
        implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据 accessKey 查询用户信息
     *
     * @param accessKey 用户标识签名
     * @return 用户信息
     */
    @Override
    public User getInvokeUser(String accessKey) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("access_key", accessKey);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            throw new BusinessException(BaseCode.RESOURCE_NOT_FOUND);
        }
        return user;
    }

}





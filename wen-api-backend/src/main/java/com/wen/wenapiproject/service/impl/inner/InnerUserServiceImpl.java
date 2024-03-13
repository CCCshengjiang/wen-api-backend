package com.wen.wenapiproject.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.service.InnerUserService;
import com.wen.wenapiproject.common.BaseCode;
import com.wen.wenapiproject.exception.BusinessException;
import com.wen.wenapiproject.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author wen
 */
@Service
public class InnerUserServiceImpl extends ServiceImpl<UserMapper, User>
        implements InnerUserService {

    @Resource
    private UserMapper userMapper;

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





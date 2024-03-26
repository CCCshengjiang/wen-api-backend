package com.wen.wenapiinterface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapiinterface.domain.AvatarUrl;
import com.wen.wenapiinterface.service.AvatarUrlService;
import com.wen.wenapiinterface.mapper.AvatarUrlMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【avatar_url(精选头像表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class AvatarUrlServiceImpl extends ServiceImpl<AvatarUrlMapper, AvatarUrl>
    implements AvatarUrlService {

}





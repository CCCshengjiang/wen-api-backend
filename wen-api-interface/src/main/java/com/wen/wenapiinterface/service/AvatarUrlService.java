package com.wen.wenapiinterface.service;

import cn.hutool.json.JSON;
import com.wen.wenapiinterface.domain.AvatarUrl;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 针对表【avatar_url(精选头像表)】的数据库操作Service
 *
 * @author wen
 */
public interface AvatarUrlService extends IService<AvatarUrl> {

    /**
     * 精选头像
     *
     * @return JSON 格式数据
     */
    JSON getAvatar();

}

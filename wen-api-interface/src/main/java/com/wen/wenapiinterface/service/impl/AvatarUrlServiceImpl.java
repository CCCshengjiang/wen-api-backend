package com.wen.wenapiinterface.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapiinterface.domain.AvatarUrl;
import com.wen.wenapiinterface.service.AvatarUrlService;
import com.wen.wenapiinterface.mapper.AvatarUrlMapper;
import com.wen.wenapiinterface.util.JsonReturn;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 针对表【avatar_url(精选头像表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class AvatarUrlServiceImpl extends ServiceImpl<AvatarUrlMapper, AvatarUrl>
    implements AvatarUrlService {

    @Resource
    private AvatarUrlMapper avatarUrlMapper;


    /**
     * 精选头像
     *
     * @return JSON 格式数据
     */
    @Override
    public JSON getAvatar() {
        // 随机取值
        int id = RandomUtil.randomInt(1000);
        AvatarUrl avatarUrl = avatarUrlMapper.selectById(id);
        // 提升容错
        if (avatarUrl == null) {
            id = RandomUtil.randomInt(10);
            avatarUrl = avatarUrlMapper.selectById(id);
        }
        return JsonReturn.success("精选头像", "url", avatarUrl.getDataInfo());
    }
}





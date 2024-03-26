package com.wen.wenapiinterface.controller;

import cn.hutool.json.JSON;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapiinterface.service.AvatarUrlService;
import com.wen.wenapiinterface.service.LoveWordsService;
import com.wen.wenapiinterface.service.PoisonChickenSoupService;
import com.wen.wenapiinterface.util.RequestUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 名称接口
 *
 * @author wen
 */
@RestController
@RequestMapping("/")
public class InterfaceController {

    @Resource
    private PoisonChickenSoupService poisonChickenSoupService;

    @Resource
    private AvatarUrlService avatarUrlService;

    @Resource
    private LoveWordsService loveWordsService;

    /**
     * 毒鸡汤
     *
     * @return 得到一句随机毒鸡汤
     */
    @GetMapping("/poison-chicken-soup")
    public JSON getPoisonChickenSoup() {
        return null;
    }

    /**
     * 情话
     *
     * @return 得到一句随机情话
     */
    @GetMapping("/love-word")
    public JSON getLoveWords() {
        return null;
    }

    /**
     * 随机头像
     *
     * @return JSON 格式的头像
     */
    @GetMapping("/avatar")
    public JSON getAvatar() {
        return null;
    }

}

package com.wen.wenapiinterface.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.wen.wenapiinterface.baidu.TransApi;
import com.wen.wenapiinterface.constant.InterfaceConstant;
import com.wen.wenapiinterface.service.AvatarUrlService;
import com.wen.wenapiinterface.service.LoveWordsService;
import com.wen.wenapiinterface.service.PoisonChickenSoupService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


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
        return poisonChickenSoupService.getPoisonChickenSoup();
    }

    /**
     * 情话
     *
     * @return 得到一句随机情话
     */
    @GetMapping("/love-word")
    public JSON getLoveWords() {
        return loveWordsService.getLoveWords();
    }

    /**
     * 随机头像
     *
     * @return JSON 格式的头像
     */
    @GetMapping("/avatar")
    public JSON getAvatar() {
        return avatarUrlService.getAvatar();
    }

    /**
     * 调用百度翻译 API
     *
     * @param words 要翻译成英语的字符串
     * @return JSON 格式数据
     */
    @GetMapping("/translation")
    public JSON getAnswer(@RequestParam(required = false) String words) {
        // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
        TransApi api = new TransApi(InterfaceConstant.APP_ID, InterfaceConstant.SECURITY_KEY);
        if (StringUtils.isAnyBlank(words)) {
            words = "欢迎来到 WEN-API 接口开放平台";
        }
        String transResult = api.getTransResult(words, "auto", "en");
        return JSONUtil.parse(transResult);
    }

}

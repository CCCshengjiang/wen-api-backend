package com.wen.wenapiinterface.controller;

import cn.hutool.json.JSON;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapiinterface.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 名称接口
 *
 * @author wen
 */
@RestController
@RequestMapping("/")
public class NameController {

    /**
     * 得到当前用户的用户名
     *
     * @param user 当前用户
     * @return 当前用户的用户名
     */
    @PostMapping("/name")
    public String getUsernameByPost(@RequestBody User user) {
        return "POST(JSON) 名字是：" + user.getUsername();
    }

    /**
     * 毒鸡汤
     *
     * @return 得到一句随机毒鸡汤
     */
    @GetMapping("/poison-chicken-soup")
    public JSON getPoisonChickenSoup() {
        return RequestUtils.getJson("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }

    /**
     * 情话
     *
     * @return 得到一句随机情话
     */
    @GetMapping("/love-word")
    public JSON getLoveWords() {
        return RequestUtils.getJson("https://api.vvhan.com/api/text/love?type=json");
    }

    /**
     * 翻译
     *
     * @param words 前端传递的句子
     * @return 翻译后的 JSON 格式句子
     */
    @PostMapping("/translation")
    public JSON getLoveWords(@RequestBody String words) {
        String url = "https://api.vvhan.com/api/fy";
        if (StringUtils.isNotBlank(words)) {
            url = url + "?text=" + words;
        }
        return RequestUtils.getJson(url);
    }

    /**
     * 随机头像
     *
     * @return JSON 格式的头像
     */
    @GetMapping("/avatar")
    public JSON getAvatar() {
        return RequestUtils.getJson("https://api.vvhan.com/api/avatar/rand?type=json");
    }

}

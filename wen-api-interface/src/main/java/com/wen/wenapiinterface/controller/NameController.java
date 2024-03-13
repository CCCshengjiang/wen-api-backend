package com.wen.wenapiinterface.controller;

import com.wen.wenapiclient.model.UserInClient;
import com.wen.wenapiclient.util.SignUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 名称接口
 *
 * @author wen
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(@RequestParam String name) {
        return "GET 名字是：" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "POST 名字是：" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody UserInClient user, HttpServletRequest request) {
        String accessKey = request.getHeader("accessKey");
        if (!"wen-api".equals(accessKey)) {
            throw new RuntimeException("无权限调用改接口");
        }
        // 实际情况是应该去数据库中查找 secretKey
        String sign = SignUtil.getSign(request.getHeader("body"), "admin");
        String serveSign = request.getHeader("sign");
        if (!sign.equals(serveSign)) {
            throw new RuntimeException("无权限调用改接口");
        }
        return "POST(JSON) 名字是：" + user.getUsername();
    }

}

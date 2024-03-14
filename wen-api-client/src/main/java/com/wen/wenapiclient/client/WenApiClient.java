package com.wen.wenapiclient.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wen.wenapiclient.util.SignUtil;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Http 客户端
 *
 * @author wen
 */
@Slf4j
public class WenApiClient {

    private final String accessKey;
    private final String secretKey;

    private static final String GATEWAY_HOST = "http://localhost:8090";

    public WenApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get("/api/name/", paramMap);
        System.out.println("客户端：" + result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println("客户端：" + result);
        return result;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        if (httpResponse.getStatus() == 500) {
            throw new BusinessException(BaseCode.RATE_LIMIT_EXCEEDED, "接口调用次数已用完，请联系管理员！");
        }
        if (httpResponse.getStatus() == 403) {
            throw new BusinessException(BaseCode.ACCESS_DENIED, "用户权限不足！");
        }
        log.info("当前调用接口：getUsernameByPost");
        log.info("客户端响应：" + httpResponse);
        String res = httpResponse.body();
        log.info("客户端返回：" + res);
        return res;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        headerMap.put("body", body);
        headerMap.put("nonce", RandomUtil.randomNumbers(5));
        headerMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        headerMap.put("sign", SignUtil.getSign(body, secretKey));
        return headerMap;
    }

}

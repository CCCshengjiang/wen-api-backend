package com.wen.wenapiclient.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wen.wenapiclient.model.UserInClient;
import com.wen.wenapiclient.util.SignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Http 客户端
 *
 * @author wen
 */
public class WenApiClient {

    private String accessKey;
    private String secretKey;

    public WenApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println("客户端：" + result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println("客户端：" + result);
        return result;
    }

    public String getUsernameByPost(UserInClient user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println("客户端响应：" + httpResponse);
        String res = httpResponse.body();
        System.out.println("客户端返回：" + res);
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

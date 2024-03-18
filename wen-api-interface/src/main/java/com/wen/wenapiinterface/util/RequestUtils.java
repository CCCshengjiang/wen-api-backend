package com.wen.wenapiinterface.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 返回数据工具类
 *
 * @author wen
 */
@Slf4j
public class RequestUtils {

    /**
     * 不允许创建类的实例
     */
    private RequestUtils() {
    }

    /**
     * GET 根据地址请求
     *
     * @param url 请求地址
     * @return JSON 格式的返回值
     */
    public static JSON getJson(String url) {
        String words = HttpRequest.get(url).execute().body();
        //是否是JSON格式？
        if (!JSONUtil.isTypeJSON(words)) {
            words = "{\"text\":" + "\"" +words + "\"}";
        }
        log.info("请求地址：{}，响应数据：{}", url, words);
        return JSONUtil.parse(words);
    }

}

package com.wen.wenapiinterface.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 返回 JSON 格式数据工具类
 *
 * @author wen
 */
@Slf4j
public class JsonReturn {

    /**
     * 不允许创建类的实例
     */
    private JsonReturn() {
    }

    /**
     * 返回成功的 JSON 格式数据
     *
     * @param interfaceName 当前接口名称
     * @param key 数据类型
     * @param value 数据值
     * @return 封装的JSON格式
     */
    public static JSON success(String interfaceName, String key, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("code", 200);
        jsonObject.set("type", interfaceName);
        jsonObject.set(key, value);
        return jsonObject;
    }

}

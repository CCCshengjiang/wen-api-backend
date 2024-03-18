package com.wen.wenapiclient.service;

import cn.hutool.json.JSON;
import com.wen.wenapiclient.request.GetInvokeRequest;

/**
 * 根据请求类型，选择调用方法
 *
 * @author wen
 */
public interface MethodInvoke {

    /**
     * get 请求
     *
     * @param getInvokeRequest 请求参数
     * @return JSON 格式数据
     */
    JSON get(GetInvokeRequest getInvokeRequest);

    /**
     * post 请求
     *
     * @param getInvokeRequest 请求参数
     * @return JSON 格式数据
     */
    JSON post(GetInvokeRequest getInvokeRequest);

}

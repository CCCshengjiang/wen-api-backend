package com.wen.wenapiclient.client;

import cn.hutool.json.JSON;
import com.wen.wenapiclient.request.GetInvokeRequest;
import com.wen.wenapiclient.service.MethodInvoke;
import com.wen.wenapiclient.service.impl.MethodInvokeImpl;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.request.interfaceinfo.InterfaceInvokeRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Http 客户端
 *
 * @author wen
 */
@Slf4j
public class WenApiClient {

    private final String accessKey;
    private final String secretKey;

    public WenApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public JSON getInvoke(InterfaceInvokeRequest invokeRequest) {
        String params = invokeRequest.getUserRequestParams();
        String url = invokeRequest.getInterfaceUrl();
        if (StringUtils.isAnyBlank(url)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "请求地址为空");
        }
        String method = invokeRequest.getInterfaceMethod();
        if (StringUtils.isAnyBlank(method)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, "请求类型为空");
        }
        // 封装要发送给网关的请求参数
        JSON jsonData = getJsonData(url, params, method);
        log.info("客户端响应：" + jsonData);
        return jsonData;
    }

    private JSON getJsonData(String url, String params, String method) {
        GetInvokeRequest getInvokeRequest = new GetInvokeRequest();
        getInvokeRequest.setUrl(url);
        getInvokeRequest.setParams(params);
        getInvokeRequest.setAccessKey(accessKey);
        getInvokeRequest.setSecretKey(secretKey);
        // 将根据请求方法决定调用哪种接口
        JSON jsonData;
        MethodInvoke methodInvoke = new MethodInvokeImpl();
        if ("GET".equals(method)) {
            jsonData = methodInvoke.get(getInvokeRequest);
        }else {
            jsonData = methodInvoke.post(getInvokeRequest);
        }
        return jsonData;
    }

}

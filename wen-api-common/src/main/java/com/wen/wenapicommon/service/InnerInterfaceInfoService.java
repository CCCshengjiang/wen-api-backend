package com.wen.wenapicommon.service;

import com.wen.wenapicommon.model.domain.InterfaceInfo;

/**
 * 内部服务：接口信息操作
 *
 * @author wen
 */
public interface InnerInterfaceInfoService{

    /**
     * 根据路径和接口类型查询接口是否存在
     *
     * @param url 接口地址
     * @param method 接口类型
     * @return 接口信息
     */
    InterfaceInfo getInterfaceInfo(String url, String method);

}

package com.wen.wenapicommon.service;

import com.wen.wenapicommon.model.domain.User;

/**
 * 内部服务：用户信息操作
 *
 * @author wen
 */
public interface InnerUserService{

    /**
     * 通过标识签名查询对应的用户
     *
     * @param accessKey 用户标识签名
     * @return 用户
     */
    User getInvokeUser(String accessKey);

}

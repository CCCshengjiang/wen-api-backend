package com.wen.wenapicommon.service;

/**
 * 内部服务：用户-接口信息操作
 *
 * @author wen
 */
public interface InnerUserInterfaceInfoService{

    /**
     * 接口调用统计操作
     *
     * @param interfaceInfoId 接口 ID
     * @param userId 用户 ID
     * @return 是否查询成功
     */
    boolean invokeCount(long interfaceInfoId, long userId);

}

package com.wen.wenapiproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wen.wenapicommon.model.domain.InterfaceInfo;
import com.wen.wenapicommon.model.request.interfaceinfo.InterfaceUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 针对表【interface_info(接口信息表)】的数据库操作Service
 *
 * @author wen
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 更新接口信息
     *
     * @param interfaceUpdateRequest 更新参数信息
     * @param request 请求信息
     * @return 是否更新成功
     */
    boolean updateInterface(InterfaceUpdateRequest interfaceUpdateRequest, HttpServletRequest request);
}

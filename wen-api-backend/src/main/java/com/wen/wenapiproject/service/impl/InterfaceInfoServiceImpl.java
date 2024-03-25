package com.wen.wenapiproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.InterfaceInfo;
import com.wen.wenapicommon.model.request.interfaceinfo.InterfaceUpdateRequest;
import com.wen.wenapiproject.service.InterfaceInfoService;
import com.wen.wenapiproject.mapper.InterfaceInfoMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 针对表【interface_info(接口信息表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 更新接口信息具体实现
     *
     * @param interfaceUpdateRequest 更新参数信息
     * @param request                请求信息
     * @return 更新成功
     */
    @Override
    public boolean updateInterface(InterfaceUpdateRequest interfaceUpdateRequest, HttpServletRequest request) {
        // 得到数据库中的接口信息
        Long id = interfaceUpdateRequest.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(BaseCode.PARAMS_ERROR);
        }
        // 请求信息的非空判断
        String interfaceName = interfaceUpdateRequest.getInterfaceName();
        if (StringUtils.isAnyBlank(interfaceName)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, " 接口名不能为空");
        }
        String interfaceUrl = interfaceUpdateRequest.getInterfaceUrl();
        if (StringUtils.isAnyBlank(interfaceUrl)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, " 接口地址不能为空");
        }
        String interfaceMethod = interfaceUpdateRequest.getInterfaceMethod();
        if (StringUtils.isAnyBlank(interfaceMethod)) {
            throw new BusinessException(BaseCode.PARAMS_NULL_ERROR, " 请求类型不能为空");
        }
        // 更新接口信息
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, interfaceInfo);
        int res = interfaceInfoMapper.updateById(interfaceInfo);
        if (res <= 0) {
            throw new BusinessException(BaseCode.INTERNAL_ERROR, "更新接口信息失败");
        }
        return true;
    }

}





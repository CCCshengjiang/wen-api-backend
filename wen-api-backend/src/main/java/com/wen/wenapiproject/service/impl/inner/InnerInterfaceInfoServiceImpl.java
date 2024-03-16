package com.wen.wenapiproject.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.InterfaceInfo;
import com.wen.wenapicommon.service.InnerInterfaceInfoService;
import com.wen.wenapiproject.mapper.InterfaceInfoMapper;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 针对表【interface_info(接口信息表)】的数据库操作Service实现
 *
 * @author wen
 */
@DubboService
public class InnerInterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 根据路径和接口类型查询接口是否存在
     *
     * @param url 接口地址
     * @param method 接口类型
     * @return 接口信息
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interface_url", url);
        queryWrapper.eq("interface_method", method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        if (interfaceInfo == null) {
            throw new BusinessException(BaseCode.RESOURCE_NOT_FOUND);
        }
        return interfaceInfo;
    }
}





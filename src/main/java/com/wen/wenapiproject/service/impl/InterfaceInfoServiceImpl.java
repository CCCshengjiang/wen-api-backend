package com.wen.wenapiproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapiproject.model.domain.InterfaceInfo;
import com.wen.wenapiproject.service.InterfaceInfoService;
import com.wen.wenapiproject.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【interface_info(接口信息表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

}





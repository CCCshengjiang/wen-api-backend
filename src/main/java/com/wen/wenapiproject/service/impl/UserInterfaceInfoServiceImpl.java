package com.wen.wenapiproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapiproject.model.domain.UserInterfaceInfo;
import com.wen.wenapiproject.service.UserInterfaceInfoService;
import com.wen.wenapiproject.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【user_interface_info(用户——接口信息表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

}





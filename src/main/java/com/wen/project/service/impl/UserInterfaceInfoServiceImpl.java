package com.wen.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.project.model.domain.UserInterfaceInfo;
import com.wen.project.service.UserInterfaceInfoService;
import com.wen.project.mapper.UserInterfaceInfoMapper;
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





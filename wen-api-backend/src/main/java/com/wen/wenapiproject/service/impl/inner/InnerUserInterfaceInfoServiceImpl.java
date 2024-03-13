package com.wen.wenapiproject.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapicommon.model.domain.UserInterfaceInfo;
import com.wen.wenapicommon.service.InnerUserInterfaceInfoService;
import com.wen.wenapiproject.common.BaseCode;
import com.wen.wenapiproject.exception.BusinessException;
import com.wen.wenapiproject.mapper.UserInterfaceInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 针对表【user_interface_info(用户——接口信息表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class InnerUserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    /**
     * 接口调用统计操作
     *
     * @param interfaceInfoId 接口 ID
     * @param userId 用户 ID
     * @return 是否查询成功
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 1.先查找个关系记录是否存在
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("interface_id", interfaceInfoId);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        // 如果记录不存在，就创建
        if (userInterfaceInfo == null) {
            userInterfaceInfo = new UserInterfaceInfo();
            userInterfaceInfo.setUserId(userId);
            userInterfaceInfo.setInterfaceId(interfaceInfoId);
            userInterfaceInfo.setTotalNum(0);
            userInterfaceInfo.setBalanceNum(1000);
            userInterfaceInfoMapper.insert(userInterfaceInfo);
        }
        // 2.查询剩余调用次数是否为 0
        int balanceNum = userInterfaceInfo.getBalanceNum();
        if (balanceNum <= 0) {
            throw new BusinessException(BaseCode.RATE_LIMIT_EXCEEDED, "接口调用次数已用完！");
        }
        // 3.更新记录：调用次数加一，剩余次数减一
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("total_num = total_num + 1, balance_num = balance_num - 1")
                .eq("interface_id", interfaceInfoId)
                .eq("user_id", interfaceInfoId);
        return this.update(updateWrapper);
    }

}





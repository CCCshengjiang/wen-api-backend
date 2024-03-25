package com.wen.wenapiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.common.BaseResponse;
import com.wen.wenapicommon.common.request.DeleteRequest;
import com.wen.wenapicommon.common.request.IdRequest;
import com.wen.wenapicommon.common.utils.ReturnUtil;
import com.wen.wenapicommon.exception.BusinessException;
import com.wen.wenapicommon.model.domain.InterfaceInfo;
import com.wen.wenapicommon.model.domain.UserInterfaceInfo;
import com.wen.wenapicommon.model.request.userinterface.UserInterfaceAddRequest;
import com.wen.wenapicommon.model.request.userinterface.UserInterfaceSearchRequest;
import com.wen.wenapicommon.model.request.userinterface.UserInterfaceUpdateRequest;
import com.wen.wenapiproject.annotation.AuthCheck;
import com.wen.wenapiproject.model.vo.InterfaceTopVO;
import com.wen.wenapiproject.service.InterfaceInfoService;
import com.wen.wenapiproject.service.UserInterfaceInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wen
 */
@RestController
@RequestMapping("/userInterface")
@CrossOrigin(origins = {"http://localhost:8000/"}, allowCredentials = "true")
// @CrossOrigin(origins = { "https://wen-api.cwblue.cn/" }, allowCredentials = "true")
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @PostMapping("/invoke/top")
    @AuthCheck
    public BaseResponse<List<InterfaceTopVO>> invokeInterfaceTop(HttpServletRequest request) {
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("interface_id")
                .select("interface_id", "SUM(total_num) as total_num");
        List<InterfaceTopVO> interfaceTopVOList = new ArrayList<>();
        List<UserInterfaceInfo> list = userInterfaceInfoService.list(queryWrapper);
        InterfaceTopVO interfaceTopVO;
        for (UserInterfaceInfo userInterfaceInfo : list) {
            interfaceTopVO = new InterfaceTopVO();
            InterfaceInfo interfaceInfo = interfaceInfoService.getById(userInterfaceInfo.getInterfaceId());
            interfaceTopVO.setInterfaceName(interfaceInfo.getInterfaceName());
            interfaceTopVO.setInvokeNum(userInterfaceInfo.getTotalNum());
            interfaceTopVOList.add(interfaceTopVO);
        }
        return ReturnUtil.success(interfaceTopVOList);
    }

}

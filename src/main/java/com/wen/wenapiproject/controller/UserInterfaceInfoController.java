package com.wen.wenapiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.wenapiproject.common.BaseResponse;
import com.wen.wenapiproject.common.request.DeleteRequest;
import com.wen.wenapiproject.common.request.IdRequest;
import com.wen.wenapiproject.common.request.PageRequest;
import com.wen.wenapiproject.common.utils.ReturnUtil;
import com.wen.wenapiproject.exception.BusinessException;
import com.wen.wenapiproject.model.domain.UserInterfaceInfo;
import com.wen.wenapiproject.model.request.userinterface.UserInterfaceAddRequest;
import com.wen.wenapiproject.model.request.userinterface.UserInterfaceSearchRequest;
import com.wen.wenapiproject.model.request.userinterface.UserInterfaceUpdateRequest;
import com.wen.wenapiproject.service.UserInterfaceInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wen.wenapiproject.common.BaseCode.*;

/**
 * @author wen
 */
@RestController
@RequestMapping("/userInterface")
@CrossOrigin(origins = {"http://localhost:8000/"}, allowCredentials = "true")
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 添加用户-接口信息
     *
     * @param userInterfaceAddRequest 请求参数
     * @param request             请求信息
     * @return 添加后的 id
     */
    @PostMapping("/add")
    public BaseResponse<Long> addUserInterface(@RequestBody UserInterfaceAddRequest userInterfaceAddRequest, HttpServletRequest request) {
        if (userInterfaceAddRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceAddRequest, userInterfaceInfo);
/*        interfaceInfo.setUserId(1L);*/
        boolean res = userInterfaceInfoService.save(userInterfaceInfo);
        if (!res) {
            throw new BusinessException(INTERNAL_ERROR);
        }
        return ReturnUtil.success(userInterfaceInfo.getId());
    }

    /**
     * 删除接口
     *
     * @param deleteRequest 请求参数
     * @param request       请求信息
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        return ReturnUtil.success(userInterfaceInfoService.removeById(deleteRequest.getId()));
    }

    /**
     * 更新接口信息
     *
     * @param userInterfaceUpdateRequest 请求参数
     * @param request                请求信息
     * @return 是否更新成功
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUserInterface(@RequestBody UserInterfaceUpdateRequest userInterfaceUpdateRequest, HttpServletRequest request) {
        if (userInterfaceUpdateRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceUpdateRequest, userInterfaceInfo);
        boolean res = userInterfaceInfoService.updateById(userInterfaceInfo);
        if (!res) {
            throw new BusinessException(PARAMS_ERROR, "查询接口失败");
        }
        return ReturnUtil.success(true);
    }

    /**
     * 查询接口
     *
     * @param userInterfaceSearchRequest 请求参数
     * @param request                请求信息
     * @return 接口列表
     */
    @GetMapping("/search")
    public BaseResponse<List<UserInterfaceInfo>> searchUserInterface(UserInterfaceSearchRequest userInterfaceSearchRequest, HttpServletRequest request) {
        if (userInterfaceSearchRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        Long id = userInterfaceSearchRequest.getId();
        if (id != null) {
            interfaceInfoQueryWrapper.eq("id", id);
        }
        Long interfaceId = userInterfaceSearchRequest.getInterfaceId();
        if (interfaceId != null) {
            interfaceInfoQueryWrapper.like("interface_id", interfaceId);
        }
        Long userId = userInterfaceSearchRequest.getUserId();
        if (userId != null) {
            interfaceInfoQueryWrapper.like("user_Id", userId);
        }
        Integer totalNum = userInterfaceSearchRequest.getTotalNum();
        if (totalNum != null) {
            interfaceInfoQueryWrapper.like("total_num", totalNum);
        }
        Integer balanceNum = userInterfaceSearchRequest.getBalanceNum();
        if (balanceNum != null) {
            interfaceInfoQueryWrapper.eq("balance_num", balanceNum);
        }
        Integer userInterfaceStatus = userInterfaceSearchRequest.getUserInterfaceStatus();
        if (userInterfaceStatus != null) {
            interfaceInfoQueryWrapper.eq("method", userInterfaceStatus);
        }
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.list(interfaceInfoQueryWrapper);
        return ReturnUtil.success(userInterfaceInfoList);
    }

    @GetMapping("/list")
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceByPage(PageRequest pageRequest, HttpServletRequest request) {
        int pageNum = pageRequest.getCurrent();
        int pageSize = pageRequest.getPageSize();
        Page<UserInterfaceInfo> interfaceInfoList = userInterfaceInfoService.page(new Page<>(pageNum, pageSize));
        return ReturnUtil.success(interfaceInfoList);
    }


    @GetMapping("/get")
    public BaseResponse<UserInterfaceInfo> searchUserInterfaceById(IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        Long id = idRequest.getId();
        if (id == null || id < 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        UserInterfaceInfo interfaceInfoList = userInterfaceInfoService.getById(id);
        return ReturnUtil.success(interfaceInfoList);
    }

}

package com.wen.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.project.common.BaseResponse;
import com.wen.project.common.request.DeleteRequest;
import com.wen.project.common.utils.ReturnUtil;
import com.wen.project.exception.BusinessException;
import com.wen.project.model.domain.InterfaceInfo;
import com.wen.project.model.request.interfaceinfo.InterfaceAddRequest;
import com.wen.project.model.request.interfaceinfo.InterfaceSearchRequest;
import com.wen.project.model.request.interfaceinfo.InterfaceUpdateRequest;
import com.wen.project.service.InterfaceInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wen.project.common.BaseCode.INTERNAL_ERROR;
import static com.wen.project.common.BaseCode.PARAMS_NULL_ERROR;

/**
 * @author wen
 */
@RestController
@RequestMapping("/interface")
@CrossOrigin(origins = { "http://localhost:8000/" }, allowCredentials = "true")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 添加接口
     *
     * @param interfaceAddRequest 请求参数
     * @param request 请求信息
     * @return 添加后的 id
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterface(@RequestBody InterfaceAddRequest interfaceAddRequest, HttpServletRequest request) {
        if (interfaceAddRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceAddRequest, interfaceInfo);
        boolean res = interfaceInfoService.save(interfaceInfo);
        if (!res) {
            throw new BusinessException(INTERNAL_ERROR);
        }
        return ReturnUtil.success(interfaceInfo.getId());
    }

    /**
     * 删除接口
     *
     * @param deleteRequest 请求参数
     * @param request 请求信息
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        return ReturnUtil.success(interfaceInfoService.removeById(deleteRequest.getId()));
    }

    /**
     * 更新接口信息
     *
     * @param interfaceUpdateRequest 请求参数
     * @param request 请求信息
     * @return 是否更新成功
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterface(@RequestBody InterfaceUpdateRequest interfaceUpdateRequest, HttpServletRequest request) {
        if (interfaceUpdateRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, interfaceInfo);
        return ReturnUtil.success(interfaceInfoService.updateById(interfaceInfo));
    }

    /**
     * 查询接口
     *
     * @param interfaceSearchRequest 请求参数
     * @param request 请求信息
     * @return 接口列表
     */
    @GetMapping("/search")
    public BaseResponse<List<InterfaceInfo>> searchInterface(InterfaceSearchRequest interfaceSearchRequest, HttpServletRequest request) {
        if (interfaceSearchRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        Long id = interfaceSearchRequest.getId();
        interfaceInfoQueryWrapper.eq("id", id);
        String name = interfaceSearchRequest.getName();
        interfaceInfoQueryWrapper.like("name", name);
        String description = interfaceSearchRequest.getDescription();
        interfaceInfoQueryWrapper.like("description", description);
        String userId = interfaceSearchRequest.getUserId();
        interfaceInfoQueryWrapper.eq("userId", userId);
        String url = interfaceSearchRequest.getUrl();
        interfaceInfoQueryWrapper.like("url", url);
        String method = interfaceSearchRequest.getMethod();
        interfaceInfoQueryWrapper.eq("method", method);
        String requestHeader = interfaceSearchRequest.getRequestHeader();
        interfaceInfoQueryWrapper.like("requestHeader", requestHeader);
        String responseHeader = interfaceSearchRequest.getResponseHeader();
        interfaceInfoQueryWrapper.like("responseHeader", responseHeader);
        Integer status = interfaceSearchRequest.getStatus();
        interfaceInfoQueryWrapper.eq("status", status);
        return ReturnUtil.success(interfaceInfoService.list(interfaceInfoQueryWrapper));
    }



}

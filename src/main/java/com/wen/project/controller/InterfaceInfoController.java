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

import static com.wen.project.common.BaseCode.*;

/**
 * @author wen
 */
@RestController
@RequestMapping("/interface")
@CrossOrigin(origins = {"http://localhost:8000/"}, allowCredentials = "true")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 添加接口
     *
     * @param interfaceAddRequest 请求参数
     * @param request             请求信息
     * @return 添加后的 id
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterface(@RequestBody InterfaceAddRequest interfaceAddRequest, HttpServletRequest request) {
        if (interfaceAddRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceAddRequest, interfaceInfo);
/*        interfaceInfo.setUserId(1L);*/
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
     * @param request       请求信息
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
     * @param request                请求信息
     * @return 是否更新成功
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterface(@RequestBody InterfaceUpdateRequest interfaceUpdateRequest, HttpServletRequest request) {
        if (interfaceUpdateRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, interfaceInfo);
        boolean res = interfaceInfoService.updateById(interfaceInfo);
        if (!res) {
            throw new BusinessException(PARAMS_ERROR, "查询接口失败");
        }
        return ReturnUtil.success(true);
    }

    /**
     * 查询接口
     *
     * @param interfaceSearchRequest 请求参数
     * @param request                请求信息
     * @return 接口列表
     */
    @GetMapping("/search")
    public BaseResponse<List<InterfaceInfo>> searchInterface(InterfaceSearchRequest interfaceSearchRequest, HttpServletRequest request) {
        if (interfaceSearchRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        Long id = interfaceSearchRequest.getId();
        if (id != null) {
            interfaceInfoQueryWrapper.eq("id", id);
        }
        String name = interfaceSearchRequest.getName();
        if (name != null) {
            interfaceInfoQueryWrapper.like("name", name);
        }
        String description = interfaceSearchRequest.getDescription();
        if (description != null) {
            interfaceInfoQueryWrapper.like("description", description);
        }
        Long userId = interfaceSearchRequest.getUserId();
        if (userId != null) {
            interfaceInfoQueryWrapper.eq("userId", userId);
        }
        String url = interfaceSearchRequest.getUrl();
        if (url != null) {
            interfaceInfoQueryWrapper.like("url", url);
        }
        String method = interfaceSearchRequest.getMethod();
        if (method != null) {
            interfaceInfoQueryWrapper.eq("method", method);
        }
        String requestHeader = interfaceSearchRequest.getRequestHeader();
        if (requestHeader != null) {
            interfaceInfoQueryWrapper.like("requestHeader", requestHeader);
        }
        String responseHeader = interfaceSearchRequest.getResponseHeader();
        if (responseHeader != null) {
            interfaceInfoQueryWrapper.like("responseHeader", responseHeader);
        }
        Integer status = interfaceSearchRequest.getStatus();
        if (status != null) {
            interfaceInfoQueryWrapper.eq("status", status);
        }
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(interfaceInfoQueryWrapper);
        return ReturnUtil.success(interfaceInfoList);
    }


}

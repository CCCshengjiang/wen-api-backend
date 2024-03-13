package com.wen.wenapiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wen.wenapicommon.model.domain.InterfaceInfo;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapiproject.common.BaseResponse;
import com.wen.wenapiproject.common.request.DeleteRequest;
import com.wen.wenapiproject.common.request.IdRequest;
import com.wen.wenapiproject.common.request.PageRequest;
import com.wen.wenapiproject.common.utils.ReturnUtil;
import com.wen.wenapiproject.constant.InterfaceConstant;
import com.wen.wenapiproject.exception.BusinessException;
import com.wen.wenapiproject.model.request.interfaceinfo.InterfaceAddRequest;
import com.wen.wenapiproject.model.request.interfaceinfo.InterfaceInvokeRequest;
import com.wen.wenapiproject.model.request.interfaceinfo.InterfaceSearchRequest;
import com.wen.wenapiproject.model.request.interfaceinfo.InterfaceUpdateRequest;
import com.wen.wenapiproject.service.InterfaceInfoService;
import com.wen.wenapiproject.service.UserService;
import com.wen.wenapiclient.client.WenApiClient;
import com.wen.wenapiclient.model.UserInClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wen.wenapiproject.common.BaseCode.*;
import static com.wen.wenapiproject.constant.InterfaceConstant.INTERFACE_OFFLINE;
import static com.wen.wenapiproject.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author wen
 */
@RestController
@RequestMapping("/interface")
@CrossOrigin(origins = {"http://localhost:8000/"}, allowCredentials = "true")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private WenApiClient wenApiClient;

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
        String name = interfaceSearchRequest.getInterfaceName();
        if (name != null) {
            interfaceInfoQueryWrapper.like("name", name);
        }
        String description = interfaceSearchRequest.getInterfaceDescription();
        if (description != null) {
            interfaceInfoQueryWrapper.like("description", description);
        }
        Long userId = interfaceSearchRequest.getUserId();
        if (userId != null) {
            interfaceInfoQueryWrapper.eq("userId", userId);
        }
        String url = interfaceSearchRequest.getInterfaceUrl();
        if (url != null) {
            interfaceInfoQueryWrapper.like("url", url);
        }
        String method = interfaceSearchRequest.getInterfaceMethod();
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
        Integer status = interfaceSearchRequest.getInterfaceStatus();
        if (status != null) {
            interfaceInfoQueryWrapper.eq("status", status);
        }
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(interfaceInfoQueryWrapper);
        return ReturnUtil.success(interfaceInfoList);
    }

    /**
     * 接口发布
     *
     * @param idRequest 请求参数
     * @param request 请求信息
     * @return 是否发布成功
     */
    @PostMapping("/online")
    public BaseResponse<Boolean> onlineInterface(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        Long id = idRequest.getId();
        if (id < 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        UserInClient user = new UserInClient();
        user.setUsername("admin");
        String nameByPost = wenApiClient.getUsernameByPost(user);
        if (StringUtils.isAnyBlank(nameByPost)) {
            throw new BusinessException(ACCESS_DENIED);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setInterfaceStatus(InterfaceConstant.INTERFACE_ONLINE);
        boolean res = interfaceInfoService.updateById(interfaceInfo);
        return ReturnUtil.success(res);
    }

    /**
     * 接口下线
     *
     * @param idRequest 请求参数
     * @param request 请求信息
     * @return 是否下线成功
     */
    @PostMapping("/offline")
    public BaseResponse<Boolean> offlineInterface(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        Long id = idRequest.getId();
        if (id < 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setInterfaceStatus(INTERFACE_OFFLINE);
        boolean res = interfaceInfoService.updateById(interfaceInfo);
        return ReturnUtil.success(res);
    }

    @GetMapping("/list")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceByPage(PageRequest pageRequest, HttpServletRequest request) {
        int pageNum = pageRequest.getCurrent();
        int pageSize = pageRequest.getPageSize();
        Page<InterfaceInfo> interfaceInfoList = interfaceInfoService.page(new Page<InterfaceInfo>(pageNum, pageSize));
        return ReturnUtil.success(interfaceInfoList);
    }


    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> searchInterfaceById(IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        Long id = idRequest.getId();
        if (id == null || id < 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoList = interfaceInfoService.getById(id);
        return ReturnUtil.success(interfaceInfoList);
    }

    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterface(@RequestBody InterfaceInvokeRequest interfaceInvokeRequest, HttpServletRequest request) {
        if (interfaceInvokeRequest == null || request == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        Long id = interfaceInvokeRequest.getId();
        if (id == null || id < 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(PARAMS_ERROR, "接口不存在");
        }
        // 判断接口状态
        if (interfaceInfo.getInterfaceStatus().equals(INTERFACE_OFFLINE)) {
            throw new BusinessException(PARAMS_ERROR, "接口已关闭");
        }
        // 签名认证
        String userRequestParams = interfaceInvokeRequest.getUserRequestParams();
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATUS);
        String accessKey = currentUser.getAccessKey();
        String secretKey = currentUser.getSecretKey();
        WenApiClient tempClient = new WenApiClient(accessKey, secretKey);
        // 参数转换为 Json 格式
        UserInClient user = new UserInClient();
        user.setUsername(userRequestParams);
        // 向接口请求
        String usernameByPost = tempClient.getUsernameByPost(user);
        return ReturnUtil.success(usernameByPost);
    }

}

package com.wen.wenapicommon.model.request.interfaceinfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询接口的前端请求封装类
 *
 * @author wen
 */
@Data
public class InterfaceSearchRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口描述
     */
    private String interfaceDescription;

    /**
     * 创建人 ID
     */
    private Long userId;

    /**
     * 接口地址
     */
    private String interfaceUrl;

    /**
     * 请求类型
     */
    private String interfaceMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态
     */
    private Integer interfaceStatus;

    @Serial
    private static final long serialVersionUID = -4101547925360839551L;
}

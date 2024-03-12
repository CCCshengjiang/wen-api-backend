package com.wen.project.model.request.interfaceinfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 新增接口的前端请求封装类
 *
 * @author wen
 */
@Data
public class InterfaceAddRequest implements Serializable {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 接口描述
     */
    private String interfaceDescription;

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

    @Serial
    private static final long serialVersionUID = 8358772836012855444L;
}

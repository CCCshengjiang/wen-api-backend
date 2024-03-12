package com.wen.project.model.request.interfaceinfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 调用接口的请求参数封装类
 *
 * @author wen
 */
@Data
public class InterfaceInvokeRequest implements Serializable {

    /**
     * 接口 id
     */
    private Long id;

    /**
     * 用户传递的请求参数
     */
    private String userRequestParams;


    @Serial
    private static final long serialVersionUID = 2594776101186210235L;
}

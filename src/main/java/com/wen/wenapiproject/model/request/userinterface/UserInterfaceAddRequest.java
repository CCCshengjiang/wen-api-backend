package com.wen.wenapiproject.model.request.userinterface;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户-接口信息添加请求参数类
 *
 * @author wen
 */
@Data
public class UserInterfaceAddRequest implements Serializable {

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 接口 ID
     */
    private Long interfaceId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer balanceNum;

    @Serial
    private static final long serialVersionUID = 1839431622998418030L;
}
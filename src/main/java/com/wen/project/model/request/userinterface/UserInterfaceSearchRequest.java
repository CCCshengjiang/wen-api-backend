package com.wen.project.model.request.userinterface;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户-接口查询请求参数类
 *
 * @author wen
 */
@Data
public class UserInterfaceSearchRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

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

    /**
     * 0-正常，1-禁用
     */
    private Integer userInterfaceStatus;

    @Serial
    private static final long serialVersionUID = 3868331172698571580L;
}
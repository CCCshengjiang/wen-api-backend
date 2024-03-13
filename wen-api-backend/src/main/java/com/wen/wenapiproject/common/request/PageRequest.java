package com.wen.wenapiproject.common.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用的分页请求参数
 * 
 * @author wen
 */
@Data
public class PageRequest implements Serializable {

    /**
     * 当前页码
     */
    protected int current;

    /**
     * 每页的数量
     */
    protected int pageSize;

    @Serial
    private static final long serialVersionUID = -5369343613090974934L;
}
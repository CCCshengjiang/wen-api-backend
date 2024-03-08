package com.wen.project.common.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用的删除请求参数封装类
 *
 * @author wen
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * 主键 id
     */
    private Long id;

    @Serial
    private static final long serialVersionUID = -1426292813639151499L;
}

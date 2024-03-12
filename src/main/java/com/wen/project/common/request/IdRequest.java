package com.wen.project.common.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Id 请求封装类
 *
 * @author wen
 */
@Data
public class IdRequest implements Serializable {

    /**
     * Id 主键
     */
    private Long id;

    @Serial
    private static final long serialVersionUID = -8502317994536567994L;
}

package com.wen.wenapiproject.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * api 密钥
 *
 * @author wen
 */
@Data
public class ApiKeyVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8159562653861614333L;

    /**
     * 用户标识
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;
}

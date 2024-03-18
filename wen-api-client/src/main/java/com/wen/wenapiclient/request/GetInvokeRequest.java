package com.wen.wenapiclient.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 封装请求信息
 *
 * @author wen
 */
@Data
public class GetInvokeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6997361794081847456L;

    private String url;

    private String params;

    private String accessKey;

    private String secretKey;

}

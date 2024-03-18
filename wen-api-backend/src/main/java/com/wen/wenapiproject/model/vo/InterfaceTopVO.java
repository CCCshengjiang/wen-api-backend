package com.wen.wenapiproject.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 接口调用次数返回类
 *
 * @author wen
 */
@Data
public class InterfaceTopVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7541695578558178074L;

    private String interfaceName;

    private Integer invokeNum;

}

package com.wen.wenapiproject.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wen.wenapicommon.constant.UserConstant;
import com.wen.wenapiproject.model.vo.ApiKeyVO;

/**
 * 生成密钥
 *
 * @author wen
 */
public class ApiKey {
    private ApiKey(){}
    /**
     * 生成 apiKey
     *
     * @param userAccount 用户账号
     * @return apikey封装类
     */
    public static ApiKeyVO getApiKey(String userAccount) {
        String accessKey = DigestUtil.md5Hex(UserConstant.SALT + userAccount + RandomUtil.randomNumbers(5));
        String secretKey = DigestUtil.md5Hex(userAccount + UserConstant.SALT + RandomUtil.randomNumbers(8));
        ApiKeyVO apiKeyVO = new ApiKeyVO();
        apiKeyVO.setSecretKey(secretKey);
        apiKeyVO.setAccessKey(accessKey);
        return apiKeyVO;
    }
}

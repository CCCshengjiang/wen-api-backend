package com.wen.wenapiinterface.service;

import cn.hutool.json.JSON;
import com.wen.wenapiinterface.domain.LoveWords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 针对表【love_words(情话表)】的数据库操作Service
 *
 * @author wen
 */
public interface LoveWordsService extends IService<LoveWords> {

    /**
     * 情话
     *
     * @return JSON 格式数据
     */
    JSON getLoveWords();
}

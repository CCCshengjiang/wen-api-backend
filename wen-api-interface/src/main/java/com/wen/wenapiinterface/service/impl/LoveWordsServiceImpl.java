package com.wen.wenapiinterface.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapiinterface.domain.LoveWords;
import com.wen.wenapiinterface.service.LoveWordsService;
import com.wen.wenapiinterface.mapper.LoveWordsMapper;
import com.wen.wenapiinterface.util.JsonReturn;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 针对表【love_words(情话表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class LoveWordsServiceImpl extends ServiceImpl<LoveWordsMapper, LoveWords>
    implements LoveWordsService{

    @Resource
    private LoveWordsMapper loveWordsMapper;

    /**
     * 情话
     *
     * @return JSON 格式
     */
    @Override
    public JSON getLoveWords() {
        // 随机取值
        int id = RandomUtil.randomInt(1000);
        LoveWords loveWords = loveWordsMapper.selectById(id);
        // 提升容错
        if (loveWords == null) {
            id = RandomUtil.randomInt(10);
            loveWords = loveWordsMapper.selectById(id);
        }
        return JsonReturn.success("情话", "text", loveWords.getDataInfo());
    }
}





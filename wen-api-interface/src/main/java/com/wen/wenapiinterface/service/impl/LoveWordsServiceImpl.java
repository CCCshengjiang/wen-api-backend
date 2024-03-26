package com.wen.wenapiinterface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapiinterface.domain.LoveWords;
import com.wen.wenapiinterface.service.LoveWordsService;
import com.wen.wenapiinterface.mapper.LoveWordsMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【love_words(情话表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class LoveWordsServiceImpl extends ServiceImpl<LoveWordsMapper, LoveWords>
    implements LoveWordsService{

}





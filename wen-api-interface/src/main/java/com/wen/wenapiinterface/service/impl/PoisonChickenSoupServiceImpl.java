package com.wen.wenapiinterface.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.wenapiinterface.domain.PoisonChickenSoup;
import com.wen.wenapiinterface.service.PoisonChickenSoupService;
import com.wen.wenapiinterface.mapper.PoisonChickenSoupMapper;
import com.wen.wenapiinterface.util.JsonReturn;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 针对表【poison_chicken_soup(毒鸡汤表)】的数据库操作Service实现
 *
 * @author wen
 */
@Service
public class PoisonChickenSoupServiceImpl extends ServiceImpl<PoisonChickenSoupMapper, PoisonChickenSoup>
    implements PoisonChickenSoupService {

    @Resource
    private PoisonChickenSoupMapper poisonChickenSoupMapper;

    /**
     * 随机毒鸡汤
     *
     * @return JSON 格式
     */
    @Override
    public JSON getPoisonChickenSoup() {
        // 随机取值
        int id = RandomUtil.randomInt(1000);
        PoisonChickenSoup poisonChickenSoup = poisonChickenSoupMapper.selectById(id);
        // 提升容错
        if (poisonChickenSoup == null) {
            id = RandomUtil.randomInt(10);
            poisonChickenSoup = poisonChickenSoupMapper.selectById(id);
        }
        return JsonReturn.success("毒鸡汤", "text", poisonChickenSoup.getDataInfo());
    }
}





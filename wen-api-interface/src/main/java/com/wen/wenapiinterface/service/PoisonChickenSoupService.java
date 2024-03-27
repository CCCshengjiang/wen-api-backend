package com.wen.wenapiinterface.service;

import cn.hutool.json.JSON;
import com.wen.wenapiinterface.domain.PoisonChickenSoup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 针对表【poison_chicken_soup(毒鸡汤表)】的数据库操作Service
 *
 * @author wen
 */
public interface PoisonChickenSoupService extends IService<PoisonChickenSoup> {

    /**
     * 随机毒鸡汤
     *
     * @return JSON 格式
     */
    JSON getPoisonChickenSoup();

}

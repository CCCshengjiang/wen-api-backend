package com.wen.wenapiinterface.service;

import cn.hutool.json.JSON;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PoisonChickenSoupServiceTest {

    @Resource
    private PoisonChickenSoupService poisonChickenSoupService;

    @Test
    void getPoisonChickenSoup() {
        JSON poisonChickenSoup = null;
        int num = 1000;
        while (--num != 0) {
            poisonChickenSoup = poisonChickenSoupService.getPoisonChickenSoup();
            System.out.println(poisonChickenSoup);
        }
    }
}
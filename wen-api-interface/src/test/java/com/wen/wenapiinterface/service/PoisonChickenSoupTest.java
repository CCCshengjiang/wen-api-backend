package com.wen.wenapiinterface.service;

import com.wen.wenapiinterface.domain.PoisonChickenSoup;
import com.wen.wenapiinterface.mapper.PoisonChickenSoupMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.*;

/**
 * 向毒鸡汤表中插入数据
 *
 * @author wen
 */
@SpringBootTest
class PoisonChickenSoupTest {

    @Resource
    private PoisonChickenSoupService poisonChickenSoupService;

    @Test
    void insertPoisonChickenSoup() throws IOException, InterruptedException, ExecutionException {
        long times1 = System.currentTimeMillis();
        String url = "https://api.btstu.cn/yan/api.php?charset=utf-8";
        int totalNum = 500;
        CopyOnWriteArrayList<PoisonChickenSoup> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < totalNum; i++) {
            try {
                HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> send = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
                PoisonChickenSoup poisonChickenSoup = new PoisonChickenSoup();
                poisonChickenSoup.setDataInfo(send.body());
                list.add(poisonChickenSoup);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        poisonChickenSoupService.saveBatch(list);
        long times2 = System.currentTimeMillis();
        System.out.println(times2 - times1);
    }

}

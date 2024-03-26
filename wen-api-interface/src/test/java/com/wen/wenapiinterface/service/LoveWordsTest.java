package com.wen.wenapiinterface.service;

import com.wen.wenapiinterface.domain.LoveWords;
import com.wen.wenapiinterface.domain.PoisonChickenSoup;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class LoveWordsTest {

    @Resource
    private LoveWordsService loveWordsService;

    @Test
    void insertLoveWords() throws IOException, InterruptedException, ExecutionException {
        long times1 = System.currentTimeMillis();
        String url = "https://api.vvhan.com/api/text/love";
        int totalNum = 900;
        CopyOnWriteArrayList<LoveWords> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < totalNum; i++) {
            try {
                HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> send = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
                LoveWords loveWords = new LoveWords();
                loveWords.setDataInfo(send.body());
                list.add(loveWords);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        loveWordsService.saveBatch(list);
        long times2 = System.currentTimeMillis();
        System.out.println(times2 - times1);
    }

}

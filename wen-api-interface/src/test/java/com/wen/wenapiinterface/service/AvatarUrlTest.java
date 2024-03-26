package com.wen.wenapiinterface.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.wenapiinterface.domain.AvatarUrl;
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
class AvatarUrlTest {

    @Resource
    private AvatarUrlService avatarUrlService;

    @Test
    void insertLoveWords() throws IOException, InterruptedException, ExecutionException {
        long times1 = System.currentTimeMillis();
        String url = "https://api.vvhan.com/api/avatar/rand?type=json";
        int totalNum = 400;
        CopyOnWriteArrayList<AvatarUrl> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < totalNum; i++) {
            try {
                HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url))
                        .GET()
                        .build();
                HttpResponse<String> send = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
                ObjectMapper objectMapper = new ObjectMapper();
                String data = objectMapper.readTree(send.body()).get("url").asText();
                AvatarUrl avatarUrl = new AvatarUrl();
                avatarUrl.setDataInfo(data);
                list.add(avatarUrl);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        avatarUrlService.saveBatch(list);
        long times2 = System.currentTimeMillis();
        System.out.println(times2 - times1);
    }

}


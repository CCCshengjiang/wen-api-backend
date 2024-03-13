package com.wen.wenapiproject.service.impl.inner;

import com.wen.wenapicommon.service.InnerUserInterfaceInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InnerUserInterfaceInfoServiceImplTest {

    @Resource
    private InnerUserInterfaceInfoService userInterfaceInfoService;

    @Test
    void invokeCountTest() {
        boolean res = userInterfaceInfoService.invokeCount(1L, 1L);
        assertTrue(res);
    }
}
package com.wen.wenapiproject.service.impl.inner;

import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.service.InnerUserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InnerUserServiceImplTest {

    @Resource
    private InnerUserService userService;

    @Test
    void getInvokeUserTest() {
        User invokeUser = userService.getInvokeUser("wen-api");
        Assertions.assertEquals(invokeUser.getId(), 1L);
    }
}
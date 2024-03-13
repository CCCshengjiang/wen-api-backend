package com.wen.wenapiproject.service.impl.inner;

import com.wen.wenapicommon.model.domain.InterfaceInfo;
import com.wen.wenapicommon.service.InnerInterfaceInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InnerInterfaceInfoServiceImplTest {

    @Resource
    private InnerInterfaceInfoService interfaceInfoService;

    @Test
    void getInterfaceInfoTest() {
        InterfaceInfo interfaceInfo = interfaceInfoService.getInterfaceInfo("http://localhost:8123/api/name/user", "b0");
        Assertions.assertEquals(3L, interfaceInfo.getId());
    }
}
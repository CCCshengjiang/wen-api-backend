package com.wen.wenapiinterface.controller;

import cn.hutool.json.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NameControllerTest {

    @Test
    void getQrCode() {
        NameController nameController = new NameController();
        JSON qrCode = nameController.getAvatar();
        System.out.println(qrCode);
    }
}
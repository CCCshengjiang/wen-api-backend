package com.wen.wenapiinterface;

import com.wen.wenapiclient.WenApiClientConfig;
import com.wen.wenapiclient.client.WenApiClient;
import com.wen.wenapiclient.model.UserInClient;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
/*@Import(WenApiClientConfig.class)  // 导入配置类*/
class WenApiInterfaceApplicationTests {

    @Resource
    private WenApiClient wenApiClient;

    @Test
    void testUserPost() {
        UserInClient user = new UserInClient();
        user.setUsername("wen");
        String usernameByPost = wenApiClient.getUsernameByPost(user);
        System.out.println(usernameByPost);
    }

}

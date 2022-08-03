package com.inlym.lifehelper.external.wechat;

import com.inlym.lifehelper.external.wechat.pojo.WeChatGetAccessTokenResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 微信 HTTP 请求服务测试类
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/3
 * @since 1.3.0
 **/
@SpringBootTest
public class WeChatHttpServiceTest {
    @Autowired
    private WeChatHttpService service;

    @Test
    void testGetAccessToken() {
        WeChatGetAccessTokenResponse data = service.getAccessToken();

        Assertions.assertNotNull(data.getAccessToken());
    }
}

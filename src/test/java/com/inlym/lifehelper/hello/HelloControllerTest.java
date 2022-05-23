package com.inlym.lifehelper.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * 通用调试控制器测试
 *
 * <h2>说明
 * <p>测试通用调试控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/23
 * @since 1.2.3
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testPing() {
        String value = testRestTemplate.getForObject("/", String.class);
        Assertions.assertEquals("pong", value);
    }
}

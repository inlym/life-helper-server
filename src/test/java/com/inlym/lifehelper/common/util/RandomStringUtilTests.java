package com.inlym.lifehelper.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 字符串工具集测试
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/5/31
 * @since 2.3.0
 **/
@SpringBootTest
class RandomStringUtilTests {

    @Test
    void testGenerate() {
        // 检测生成的字符串长度
        assertThat(RandomStringUtil.generate(10).length()).isEqualTo(10);

        // 生成1万个，检测不重复
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            assertThat(set.add(RandomStringUtil.generate(8))).isTrue();
        }
    }
}

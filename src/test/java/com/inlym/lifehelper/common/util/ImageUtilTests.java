package com.inlym.lifehelper.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 类名称
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/5/27
 * @since 2.3.0
 **/
@SpringBootTest
class ImageUtilTests {

    @Test
    void testDetectFormat() {
        String url1 = "https://p.upyun.com/demo/webp/jpg/2.jpg";
        String url2 = "https://p.upyun.com/demo/webp/png/2.png";
        String url3 = "https://p.upyun.com/demo/webp/gif/2.gif";
        String url4 = "https://p.upyun.com/demo/webp/animated-gif/2.gif";
        String url5 = "https://p.upyun.com/demo/webp/webp/animated-gif-2.webp";
        String url6 = "https://p.upyun.com/demo/webp/webp/jpg-2.webp";

        assertThat(ImageUtil.detectFormat(url1)).isEqualTo("jpg");
        assertThat(ImageUtil.detectFormat(url2)).isEqualTo("png");
        assertThat(ImageUtil.detectFormat(url3)).isEqualTo("gif");
        assertThat(ImageUtil.detectFormat(url4)).isEqualTo("gif");
        assertThat(ImageUtil.detectFormat(url5)).isEqualTo("webp");
        assertThat(ImageUtil.detectFormat(url6)).isEqualTo("webp");
    }
}

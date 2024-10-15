package com.weutil.common.model;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 自定义缓存请求体封装
 *
 * <h2>说明
 * <p>为什么不使用 {@link org.springframework.web.util.ContentCachingRequestWrapper} ?
 * <p>{@link org.springframework.web.util.ContentCachingRequestWrapper} 没有修改 getInputStream() 和 getReader() 方法，只能在使用 @RequestBody 注解后使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/10/15
 * @since 3.0.0
 **/
public class CustomCachingRequestWrapper extends HttpServletRequestWrapper {
    // 用于缓存请求体内容
    private final byte[] content;

    public CustomCachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        // 在构造方法中将请求体内容缓存到内部属性中
        this.content = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        // 将缓存下来的内容转换为字节流
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {}

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    // 重写 getReader() 方法，这里复用 getInputStream() 的逻辑
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}

package com.inlym.lifehelper.common.listener;

import com.inlym.lifehelper.common.model.CustomRequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.time.LocalDateTime;

/**
 * 通用请求监听器
 *
 * <h2>主要用途
 * <p>请求初始化时，构建自定义请求上下文对象 {@link com.inlym.lifehelper.common.model.CustomRequestContext}。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/23
 * @since 1.7.0
 **/
@Component
public class CommonRequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        CustomRequestContext context = CustomRequestContext
            .builder()
            .requestTime(LocalDateTime.now())
            .build();

        sre
            .getServletRequest()
            .setAttribute(CustomRequestContext.attributeName, context);
    }
}

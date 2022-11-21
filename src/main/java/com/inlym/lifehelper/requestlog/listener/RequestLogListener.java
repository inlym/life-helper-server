package com.inlym.lifehelper.requestlog.listener;

import com.inlym.lifehelper.requestlog.service.RequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * API 访问监听器
 *
 * <h2>主要用途
 * <p>监听并记录 API 访问情况
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/20
 * @since 1.7.0
 **/
@Component
@RequiredArgsConstructor
public class RequestLogListener implements ServletRequestListener {
    private final RequestLogService requestLogService;

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        if (sre.getServletRequest() instanceof HttpServletRequest request) {
            requestLogService.resolveRequest(request);
        }
    }
}

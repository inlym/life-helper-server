package com.inlym.lifehelper.requestlog.service;

import com.inlym.lifehelper.common.base.aliyun.ots.timeseries.TimeseriesExecutor;
import com.inlym.lifehelper.common.constant.CustomRequestAttribute;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.IpLocation;
import com.inlym.lifehelper.requestlog.entity.RequestLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * 请求日志服务
 *
 * <h2>主要用途
 * <p>处理请求日志相关的方法。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/22
 * @since 1.7.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class RequestLogService {
    private final TimeseriesExecutor timeseriesExecutor;

    private final LocationService locationService;

    /**
     * 处理请求日志信息
     *
     * @param request 请求对象
     *
     * @since 1.7.0
     */
    public void resolveRequest(HttpServletRequest request) {
        int userId = request.getAttribute(CustomRequestAttribute.USER_ID) != null ? (int) request.getAttribute(CustomRequestAttribute.USER_ID) : 0;
        String ip = (String) request.getAttribute(CustomRequestAttribute.CLIENT_IP);
        System.out.println(ip);
        String method = request.getMethod();
        String path = request.getRequestURI();
        String querystring = request.getQueryString();
        String requestId = (String) request.getAttribute(CustomRequestAttribute.REQUEST_ID);
        String url = path + (querystring != null ? "?" + querystring : "");

        log.info("{} {} {} {} {}", requestId, ip, userId, method, url);

        IpLocation ipLocation = locationService.locateIpUpToCity(ip);

        RequestLog requestLog = RequestLog
            .builder()
            .userId(userId)
            .date(LocalDate.now())
            .requestId(requestId)
            .method(method)
            .path(path)
            .querystring(querystring)
            .ip(ip)
            .province(ipLocation.getProvince())
            .city(ipLocation.getCity())
            .district(ipLocation.getDistrict())
            .build();

        timeseriesExecutor.insertAsync(requestLog);
    }

    /**
     * 处理请求日志信息（异步）
     *
     * @param request 请求对象
     *
     * @since 1.7.0
     */
    @Async
    public void resolveRequestAsync(HttpServletRequest request) {
        resolveRequest(request);
    }
}

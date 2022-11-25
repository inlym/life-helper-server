package com.inlym.lifehelper.requestlog.service;

import com.inlym.lifehelper.common.base.aliyun.ots.timeseries.TimeseriesExecutor;
import com.inlym.lifehelper.common.model.CustomRequestContext;
import com.inlym.lifehelper.location.LocationService;
import com.inlym.lifehelper.location.pojo.IpLocation;
import com.inlym.lifehelper.requestlog.entity.RequestLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        List<String> whiteList = List.of("/ping");
        String path = request.getRequestURI();

        if (!whiteList.contains(path)) {
            CustomRequestContext context = (CustomRequestContext) request.getAttribute(CustomRequestContext.attributeName);

            int userId = context.getUserId();
            String ip = context.getClientIp();
            String requestId = context.getRequestId();

            String method = request.getMethod();
            String querystring = request.getQueryString();
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
    }
}

package com.inlym.lifehelper.external.lbsqq;

import com.inlym.lifehelper.common.exception.ExternalHttpRequestException;
import com.inlym.lifehelper.external.lbsqq.model.LbsqqLocateIPResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 腾讯位置服务
 * <p>
 * [主要用途]
 * 二次封装微信位置服务 HTTP 请求，提供对外可调用的方法。
 * <p>
 * [注意事项]
 * 1. 外部（项目内其他位置）应使用当前类，不允许直接使用 `LbsqqHttpService` 类。
 *
 * @author inlym
 * @since 2022-01-18 22:01
 **/
@Service
public class LbsqqService {
    private final Log logger = LogFactory.getLog(getClass());

    private final LbsqqHttpService lbsqqHttpService;

    public LbsqqService(LbsqqHttpService lbsqqHttpService) {this.lbsqqHttpService = lbsqqHttpService;}

    /**
     * IP 定位
     *
     * @param ip IP 地址
     */
    public LbsqqLocateIPResponse locateIP(String ip) throws ExternalHttpRequestException {
        Assert.notNull(ip, "IP 地址不允许为空");
        Assert.isTrue(!ip.isEmpty(), "IP 地址不允许为空");

        return lbsqqHttpService.locateIP(ip);
    }
}

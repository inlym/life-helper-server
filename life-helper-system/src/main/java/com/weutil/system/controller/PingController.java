package com.weutil.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 连通性测试控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@RestController
public class PingController {
    /**
     * 接口连通性测试
     *
     * <h2>主要用途
     * <p>用于负载均衡健康检查，只要项目没挂，这个接口就可以正常返回。
     *
     * <h2>注意事项
     * <p>项目内部进行日志记录时，不要记录当前接口，因为负载均衡的健康检查频率为1次/秒，记录日志会造成大量无效日志。
     *
     * @date 2024/6/30
     * @since 2.3.0
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong1";
    }
}

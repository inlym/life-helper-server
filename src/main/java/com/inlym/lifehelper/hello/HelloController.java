package com.inlym.lifehelper.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用接口调试控制器
 *
 * <h2>主要用途
 * <p>用于接口调试，用于定义一些与业务无关的接口。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @version 1.3.0
 * @date 2022-01-22
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class HelloController {
    /**
     * 根路由
     *
     * <h2>主要用途
     * <p>确认项目是否正常启动时，会直接输入域名访问，这个接口主要用于这个调试。
     */
    @GetMapping("/")
    public String index() {
        return "hello lifehelper";
    }

    /**
     * 接口连通性测试
     *
     * <h2>主要用途
     * <p>用于负载均衡健康检查，只要项目没挂，这个接口就可以正常返回。
     *
     * <h2>注意事项
     * <p>项目内部进行日志记录时，不要记录当前接口，因为负载均衡的健康检查频率为1次/秒，记录日志会造成大量无效日志。
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}

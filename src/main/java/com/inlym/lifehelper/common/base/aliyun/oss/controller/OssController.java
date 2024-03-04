package com.inlym.lifehelper.common.base.aliyun.oss.controller;

import com.inlym.lifehelper.common.base.aliyun.oss.model.OssPostCredential;
import com.inlym.lifehelper.common.base.aliyun.oss.service.OssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 阿里云 OSS 服务控制器
 *
 * <h2>主要用途
 * <p>封装直接调用 OSS 服务的相关 API 接口
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/13
 * @since 1.4.0
 **/
@RestController
@RequiredArgsConstructor
public class OssController {
    private final OssService ossService;

    /**
     * 获取阿里云 OSS 直传凭证
     *
     * @since 1.4.0
     */
    @GetMapping("/oss/credential")
    public OssPostCredential getOssPostCredential(@RequestParam(required = false, value = "ext") String ext) {
        return ossService.generatePostCredential(ext);
    }
}

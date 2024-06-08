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
 * @author <a href="https://www.inlym.com">inlym</a>
 * @version 2.3.0
 * @since 2024/6/8
 **/
@RestController
@RequiredArgsConstructor
public class OssController {
    private final OssService ossService;

    /**
     * 获取阿里云 OSS 直传凭证
     *
     * @param ext 文件后缀名
     *
     * @since 2024/6/8
     */
    @GetMapping("/oss/credential")
    public OssPostCredential getOssPostCredential(@RequestParam(value = "ext") String ext) {
        return ossService.generatePostCredential(ext);
    }
}

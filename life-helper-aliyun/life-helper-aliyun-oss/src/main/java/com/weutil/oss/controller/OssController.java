package com.weutil.oss.controller;

import com.weutil.oss.model.GeneratingPostCredentialDTO;
import com.weutil.oss.model.GeneratingPostCredentialOptions;
import com.weutil.oss.model.OssPostCredential;
import com.weutil.oss.service.OssService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * 阿里云对象存储管理控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/15
 * @since 3.0.0
 **/
@RestController
@RequiredArgsConstructor
@Validated
public class OssController {
    private final OssService ossService;

    /**
     * 生成 OSS 直传凭据
     *
     * @param dto 请求数据
     *
     * @date 2024/7/15
     * @since 3.0.0
     */
    @PostMapping("/oss/credential")
    public OssPostCredential generatePostCredential(@Valid @RequestBody GeneratingPostCredentialDTO dto) {
        String extension = dto.getExtension();
        GeneratingPostCredentialOptions options = GeneratingPostCredentialOptions.builder()
            .extension(extension)
            .sizeMB(100L)
            .duration(Duration.ofHours(2L))
            .build();

        return ossService.generatePostCredential(options);
    }
}

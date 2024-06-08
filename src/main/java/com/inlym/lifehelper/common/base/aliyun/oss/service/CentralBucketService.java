package com.inlym.lifehelper.common.base.aliyun.oss.service;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.inlym.lifehelper.common.base.aliyun.oss.config.CentralBucketProperties;
import com.inlym.lifehelper.common.base.aliyun.oss.constant.OssDir;
import com.inlym.lifehelper.common.util.ImageUtil;
import com.inlym.lifehelper.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Date;

/**
 * 主存储空间服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @since 2024/6/8
 **/
@Service
@RequiredArgsConstructor
public class CentralBucketService {
    private final CentralBucketProperties properties;

    private final OSS centralBucketClient;

    private final OSS centralBucketWithCustomDomainClient;

    /**
     * 保存图片
     *
     * @param dir   要保存的目录
     * @param bytes 图片内容
     *
     * @return 保存后的文件路径，示例值：{@code avatar/nUztcRQGBetU.webp}
     * @since 2024/6/8
     */
    public String saveImage(OssDir dir, byte[] bytes) {
        String ext = ImageUtil.detectFormat(bytes);
        String key = dir.getDirname() + "/" + StringUtil.generateRandomString(12) + "." + ext;
        centralBucketClient.putObject(properties.getBucketName(), key, new ByteArrayInputStream(bytes));
        return key;
    }

    /**
     * 获取资源的可读（预签名） URL 地址（）
     *
     * <h3>说明
     * <p>由于存储空间（bucket）是私有的，因此需要通过以下方法生成用户可直接访问的链接。
     *
     * @param key 资源在 OSS 的存储路径
     *
     * @return 完整的 URL 地址
     * @see
     * <a href="https://help.aliyun.com/zh/oss/user-guide/how-to-obtain-the-url-of-a-single-object-or-the-urls-of-multiple-objects">使用文件URL分享文件</a>
     * @since 2024/6/8
     */
    public String getPresignedUrl(String key) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(properties.getBucketName(), key,
                                                                              HttpMethod.GET);
        // 设置过期时间，目前默认：30天
        request.setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(30L).toMillis()));

        return centralBucketWithCustomDomainClient.generatePresignedUrl(request).toString();
    }
}

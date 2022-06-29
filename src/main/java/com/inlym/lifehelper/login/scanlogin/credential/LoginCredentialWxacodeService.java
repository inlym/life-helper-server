package com.inlym.lifehelper.login.scanlogin.credential;

import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.external.weixin.WeixinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 登录凭证的小程序码服务
 *
 * <h2>说明
 * <p>处理用于扫码登录的小程序码的生成、获取等相关事项
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/29
 * @since 1.3.0
 **/
@Service
@RequiredArgsConstructor
public class LoginCredentialWxacodeService {
    /** 每次批量生成数量 */
    private static final int BATCH_GENERATE_QUANTITY = 5;

    /** 未使用的小程序码列表 */
    private static final String UNUSED_WXACODE_LIST = "weixin:unused_login_credential";

    private final WeixinService weixinService;

    private final OssService ossService;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取资源在 OSS 中的存储路径
     *
     * @since 1.3.0
     */
    private static String getPath(String id) {
        return OssDir.WXACODE + "/" + id + ".png";
    }

    /**
     * 获取资源的完整 URL 地址
     *
     * @since 1.3.0
     */
    public String getUrl(String id) {
        return ossService.concatUrl(getPath(id));
    }

    /**
     * 生成一个可用的小程序码
     *
     * <h2>主要流程
     * <li>生成一个随机字符串 ID
     * <li>根据参数向微信服务器发起请求获取小程序码文件
     * <li>将小程序文件上传至阿里云 OSS 中
     *
     * @since 1.3.0
     */
    public String generate() {
        String id = UUID
            .randomUUID()
            .toString()
            .toLowerCase()
            .replaceAll("-", "");

        // 在 OSS 中的存储路径
        String path = getPath(id);

        // 小程序码宽度，单位：px
        int width = 430;

        // 小程序的“扫码登录页”路径
        String page = "pages/scan/login";

        // 从微信服务器获取小程序码
        byte[] bytes = weixinService.generateWxacode(page, id, width);

        // 将图片上传 OSS
        ossService.upload(path, bytes);

        return id;
    }

    /**
     * 获取一个可用的小程序码 ID
     *
     * @since 1.3.0
     */
    public String getOne() {
        String id = stringRedisTemplate
            .opsForList()
            .rightPop(UNUSED_WXACODE_LIST);

        if (id != null) {
            return id;
        } else {
            return generate();
        }
    }

    /**
     * 异步批量生成小程序码资源
     *
     * @since 1.3.0
     */
    @Async
    public void batchGenerateIfNeedAsync() {
        Long size = stringRedisTemplate
            .opsForList()
            .size(UNUSED_WXACODE_LIST);
        assert size != null;

        if (size < BATCH_GENERATE_QUANTITY) {
            for (int i = 0; i < BATCH_GENERATE_QUANTITY; i++) {
                String id = generate();
                stringRedisTemplate
                    .opsForList()
                    .leftPush(UNUSED_WXACODE_LIST, id);
            }
        }
    }
}

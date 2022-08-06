package com.inlym.lifehelper.login.qrcode;

import cn.hutool.core.util.IdUtil;
import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.external.wechat.WeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 微信小程序码服务
 *
 * <h2>主要用途
 * <p>主要用于维护“扫码登录”使用的微信小程序码。
 *
 * <h2>说明
 * <li>小程序码的最终用途只是提供一个 ID，扫码端和被扫码端以及服务器之间通过这个 ID 进行交互。
 * <li>把这个小程序码替换成普通的二维码，只要能够提供一个 ID，那么逻辑上就不发生变化。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/5
 * @since 1.3.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class WeChatQrCodeService {
    /**
     * 用于存储未使用的小程序码列表的 Redis 键名
     *
     * <h2>说明
     * <p>小程序码提前生成好，避免要使用的时候等待太久。
     */
    private static final String UNUSED_WECHAT_QRCODE_LIST = "wechat:scan_login_qrcode_list";

    /** 每次批量生成数量 */
    private static final int BATCH_GENERATE_QUANTITY = 5;

    private final WeChatService weChatService;

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
     * 生成一个用于扫码登录的小程序码
     *
     * <h2>说明
     * <p>包含了获取小程序码图片以及转储至 OSS 两步。
     *
     * @since 1.3.0
     */
    public String generate() {
        String id = IdUtil.objectId();
        String path = getPath(id);
        String page = "pages/scan/login";

        byte[] bytes = weChatService.getUnlimitedQrCode(page, id);
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
            .rightPop(UNUSED_WECHAT_QRCODE_LIST);

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
            .size(UNUSED_WECHAT_QRCODE_LIST);
        assert size != null;

        if (size < BATCH_GENERATE_QUANTITY) {
            for (int i = 0; i < BATCH_GENERATE_QUANTITY; i++) {
                String id = generate();
                stringRedisTemplate
                    .opsForList()
                    .leftPush(UNUSED_WECHAT_QRCODE_LIST, id);
            }
        }
    }
}

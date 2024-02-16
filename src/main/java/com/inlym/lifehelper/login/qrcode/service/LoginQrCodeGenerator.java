package com.inlym.lifehelper.login.qrcode.service;

import cn.hutool.core.util.HexUtil;
import com.inlym.lifehelper.common.base.aliyun.oss.OssDir;
import com.inlym.lifehelper.common.base.aliyun.oss.OssService;
import com.inlym.lifehelper.extern.wechat.WeChatService;
import com.inlym.lifehelper.extern.wechat.config.WeChatProperties;
import com.inlym.lifehelper.extern.wechat.pojo.UnlimitedQrCodeOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * 扫码登录二维码（微信小程序码）生成器
 *
 * <h2>主要用途
 * <p>用于管理扫码登录使用的二维码的生成和获取服务。
 *
 * <h2>注意事项
 * <p>1. 当前服务不负责二维码凭据的生命状态管理。
 * <p>2. 当前模块所有的「二维码」均特指「微信小程序码」。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/15
 * @since 2.0.0
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginQrCodeGenerator {
    /** 存储在 Redis 中的可用二维码列表使用的键名 */
    private static final String AVAILABLE_QRCODE_LIST = "wechat:qrcode:scan:list";

    private final WeChatProperties weChatProperties;

    private final StringRedisTemplate stringRedisTemplate;

    private final WeChatService weChatService;

    private final OssService ossService;

    /**
     * 封装用于外部调用的获取二维码 ID 的方法
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    public String getOne() {
        String id = stringRedisTemplate
            .opsForList()
            .leftPop(AVAILABLE_QRCODE_LIST);

        return Objects.requireNonNullElseGet(id, this::generate);
    }

    /**
     * 获取二维码资源在 OSS 中的路径地址
     *
     * @param id 凭据 ID
     *
     * @date 2023/5/16
     * @since 2.0.0
     */
    public String getOssPath(String id) {
        return OssDir.WXACODE + "/" + id + ".png";
    }

    /**
     * 当数量较少时，异步批量生成一批新的
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    @Async
    public void batchGenerateIfNeedAsync() {
        // 预警值
        int n = 10;

        Long size = stringRedisTemplate
            .opsForList()
            .size(AVAILABLE_QRCODE_LIST);
        assert size != null;

        // 如果当前数量低于预警值，则批量生成一批新的二维码
        if (size < n) {
            for (int i = 0; i < n; i++) {
                String id = generate();

                // 将 id 添加至可用列表
                stringRedisTemplate
                    .opsForList()
                    .rightPush(AVAILABLE_QRCODE_LIST, id);
            }
        }
    }

    /**
     * 生成一个新的二维码（并上传至 OSS）
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    private String generate() {
        String id = getRandomId();
        // 小程序中用于扫码登录页面的路径
        String page = "pages/scan/login";
        // 二维码宽度
        int width = 300;

        UnlimitedQrCodeOptions options = UnlimitedQrCodeOptions
            .builder()
            .scene(id)
            .page(page)
            .width(width)
            .envVersion("release")
            .build();

        String appId = weChatProperties
            .getMainApp()
            .getAppId();

        // 向微信服务器获取二维码
        byte[] qrcode = weChatService.getUnlimitedQrCode(appId, options);

        // 上传至阿里云 OSS
        ossService.upload(getOssPath(id), qrcode);

        return id;
    }

    /**
     * 获取随机字符串 ID
     *
     * <h2>说明
     * <p>字符串长度 19~22
     *
     * @date 2023/5/15
     * @since 2.0.0
     */
    private String getRandomId() {
        String id = UUID
            .randomUUID()
            .toString()
            .toLowerCase()
            .replace("-", "");

        return Base64
            .getEncoder()
            .encodeToString(HexUtil.decodeHex(id))
            .replace("+", "")
            .replace("/", "")
            .replace("=", "");
    }
}

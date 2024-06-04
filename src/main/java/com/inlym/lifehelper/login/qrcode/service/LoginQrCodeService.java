package com.inlym.lifehelper.login.qrcode.service;

import cn.hutool.core.util.IdUtil;
import com.inlym.lifehelper.common.base.aliyun.oss2.constant.AliyunOssDir;
import com.inlym.lifehelper.common.base.aliyun.oss2.service.AliyunOssService;
import com.inlym.lifehelper.common.exception.UnpredictableException;
import com.inlym.lifehelper.extern.wechat.WeChatService;
import com.inlym.lifehelper.extern.wechat.config.WeChatProperties;
import com.inlym.lifehelper.extern.wechat.pojo.UnlimitedQrCodeOptions;
import com.inlym.lifehelper.login.qrcode.event.LoginQrCodeLackEvent;
import com.inlym.lifehelper.login.qrcode.model.LoginQrCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * （用于扫码登录的）二维码相关操作（生成、获取）
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/2/26
 * @since 2.2.0
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginQrCodeService {
    /** 小程序中用于扫码登录页面的路径 */
    private static final String WECHAT_SCAN_LOGIN_PAGE = "pages/scan/login";

    /** 存储在 Redis 中的可用二维码列表使用的键名 */
    private static final String AVAILABLE_QRCODE_LIST = "wechat:login-qrcode:list";

    /** 预留的小程序码数量 */
    private static final long RESERVED_LOGIN_QRCODE_NUMBER = 10;

    private final WeChatProperties weChatProperties;

    private final StringRedisTemplate stringRedisTemplate;

    private final WeChatService weChatService;

    private final AliyunOssService aliyunOssService;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 封装外部获取一个二维码的方法
     *
     * <h2>主要逻辑
     * <p>在缓存中有提前生成的，则从中取一个；若无，则直接生成过一个。
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    public LoginQrCode getOne() {
        Long size = stringRedisTemplate.opsForList().size(AVAILABLE_QRCODE_LIST);

        // (2024-02-26) 实测：size 不会为 null，当列表不存在时，也会返回 `0`
        if (size != null) {
            if (size < RESERVED_LOGIN_QRCODE_NUMBER) {
                applicationEventPublisher.publishEvent(new LoginQrCodeLackEvent(RESERVED_LOGIN_QRCODE_NUMBER));
            }

            if (size > 0) {
                String result = stringRedisTemplate.opsForList().leftPop(AVAILABLE_QRCODE_LIST);

                if (result != null) {
                    String[] strs = result.split(",");
                    return LoginQrCode.builder().id(strs[0]).url(strs[1]).build();
                }
            }

            return generate();
        }

        throw new UnpredictableException("出现了 stringRedisTemplate.opsForList().size 值为 null 的情况");
    }

    /**
     * 生成一个新的二维码（并上传至 OSS）
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    private LoginQrCode generate() {
        String id = IdUtil.simpleUUID();

        UnlimitedQrCodeOptions options = UnlimitedQrCodeOptions
                .builder()
                .scene(id)
                .page(WECHAT_SCAN_LOGIN_PAGE)
                .width(300)
                .envVersion("release")
                .build();

        String appId = weChatProperties.getMainApp().getAppId();

        // 向微信服务器获取二维码
        byte[] qrcodeBytes = weChatService.getUnlimitedQrCode(appId, options);

        String path = aliyunOssService.uploadImageBytes(AliyunOssDir.WEACODE, qrcodeBytes);
        String url = aliyunOssService.concatUrl(path);
        log.trace("新生成一个用于扫码登录的二维码，信息为：id={}, url={}", id, url);

        return LoginQrCode.builder().id(id).url(url).build();
    }

    /**
     * 监听小程序码不足事件处理
     *
     * @date 2024/2/26
     * @since 2.2.0
     */
    @EventListener(LoginQrCodeLackEvent.class)
    @Async
    public void listenLoginQrCodeLackEvent() {
        List<LoginQrCode> list = batchGenerate();
        log.debug("监听到用于扫码登录的小程序码不足，已批量生成 {} 个", list.size());
    }

    /**
     * 批量生成（并将列表存入到 Redis，用于后续快速获取）
     *
     * @return 新生成的二维码信息列表
     * @date 2024/2/26
     * @since 2.2.0
     */
    private List<LoginQrCode> batchGenerate() {
        List<LoginQrCode> list = new ArrayList<>();

        for (int i = 0; i < RESERVED_LOGIN_QRCODE_NUMBER; i++) {
            LoginQrCode loginQrCode = generate();
            // 使用如下字符串格式存入 Redis List，避免普通对象格式读取时解析异常
            String str = loginQrCode.getId() + "," + loginQrCode.getUrl();

            list.add(loginQrCode);
            stringRedisTemplate.opsForList().rightPush(AVAILABLE_QRCODE_LIST, str);
        }

        return list;
    }
}

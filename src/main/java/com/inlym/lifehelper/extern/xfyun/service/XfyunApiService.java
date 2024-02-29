package com.inlym.lifehelper.extern.xfyun.service;

import com.inlym.lifehelper.extern.xfyun.config.XfyunProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

/**
 * 讯飞开放平台 API 服务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/3/1
 * @since 2.3.0
 **/
@Service
@RequiredArgsConstructor
public class XfyunApiService {
    private final XfyunProperties properties;

    /**
     * 生成鉴权 URL
     *
     * @param hostUrl 请求地址
     *
     * @date 2024/3/1
     * @since 2.3.0
     */
    @SneakyThrows
    public String getAuthUrl(String hostUrl) {
        URL url = new URL(hostUrl);

        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of("GMT"));
        String date = formatter.format(LocalDateTime.now());

        String preStr = "host: " + url.getHost() + "\n" + "date: " + date + "\n" + "POST " + url.getPath() + " HTTP/1.1";

        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(properties
                                                   .getSecret()
                                                   .getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));

        // Base64加密
        String sha = Base64
            .getEncoder()
            .encodeToString(hexDigits);
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", properties.getKey(), "hmac-sha256", "host "
            + "date " + "request-line", sha);

        // 拼接地址
        HttpUrl httpUrl = Objects
            .requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath()))
            .newBuilder()
            .addQueryParameter("authorization", Base64
                .getEncoder()
                .encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
            .addQueryParameter("date", date)
            .addQueryParameter("host", url.getHost())
            .build();

        return httpUrl.toString();
    }
}

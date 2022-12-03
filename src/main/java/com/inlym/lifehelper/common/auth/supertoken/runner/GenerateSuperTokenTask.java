package com.inlym.lifehelper.common.auth.supertoken.runner;

import com.inlym.lifehelper.common.auth.supertoken.SuperTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 生成超级登录令牌任务
 *
 * <h2>主要用途
 * <p>在应用启动时，生成一个超级登录令牌并存入 Redis
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/3
 * @since 1.7.2
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class GenerateSuperTokenTask implements ApplicationRunner {
    private final SuperTokenService superTokenService;

    @Override
    public void run(ApplicationArguments args) {
        superTokenService.generate();
    }
}

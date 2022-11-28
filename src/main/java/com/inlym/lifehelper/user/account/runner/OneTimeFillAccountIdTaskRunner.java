package com.inlym.lifehelper.user.account.runner;

import com.inlym.lifehelper.user.account.UserAccountService;
import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 一次性补充账户 ID 任务
 *
 * <h2>主要用途
 * <p>账户 ID 为后增字段，存量数据无该字段值，需要跑一次任务补充上。
 *
 * <h2>备注（2022.11.29）
 * <p>该自动任务仅需上线后跑一次即可（后续多次重复运行也无副作用），之后即可下线。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/28
 * @since 1.7.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class OneTimeFillAccountIdTaskRunner implements ApplicationRunner {
    private final UserRepository userRepository;

    private final UserAccountService userAccountService;

    @Async
    @Override
    public void run(ApplicationArguments args) {
        for (User user : userRepository.findAll()) {
            if (user.getAccountId() == null) {
                user.setAccountId(userAccountService.calcAccountId(user.getId()));
                userRepository.save(user);
            }
        }
    }
}

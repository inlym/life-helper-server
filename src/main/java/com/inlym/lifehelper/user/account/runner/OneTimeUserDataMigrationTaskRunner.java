package com.inlym.lifehelper.user.account.runner;

import com.inlym.lifehelper.user.account.UserAccountService;
import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.repository.UserRepository;
import com.inlym.lifehelper.user.info.entity.UserInfo;
import com.inlym.lifehelper.user.info.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 一次性用户数据迁移任务
 *
 * <h2>主要用途
 * <p>事项（1）：账户 ID 为后增字段，存量数据无该字段值，需要跑一次任务补充上。
 * <p>事项（2）：用户头像和昵称迁移到了新的数据表，完成数据迁移。
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
public class OneTimeUserDataMigrationTaskRunner implements ApplicationRunner {
    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final UserAccountService userAccountService;

    @Async
    @Override
    public void run(ApplicationArguments args) {
        for (User user : userRepository.findAll()) {
            // 处理账户 ID 事项
            if (user.getAccountId() == null) {
                user.setAccountId(userAccountService.calcAccountId(user.getId()));
                userRepository.save(user);
            }

            // 处理昵称和头像事项
            if (StringUtils.hasText(user.getNickName())) {
                UserInfo userInfo = userInfoRepository.findByUserId(user.getId());
                if (userInfo == null) {
                    UserInfo info = UserInfo
                        .builder()
                        .userId(user.getId())
                        .nickName(user.getNickName())
                        .avatarPath(user.getAvatar())
                        .build();

                    userInfoRepository.save(info);
                }
            }
        }
    }
}

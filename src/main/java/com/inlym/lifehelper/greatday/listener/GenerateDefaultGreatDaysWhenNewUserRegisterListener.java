package com.inlym.lifehelper.greatday.listener;

import com.inlym.lifehelper.greatday.GreatDayService;
import com.inlym.lifehelper.user.account.event.NewUserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 监听新用户注册事件，并创建默认纪念日
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/11
 * @since 1.8.0
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class GenerateDefaultGreatDaysWhenNewUserRegisterListener implements ApplicationListener<NewUserRegistrationEvent> {
    private final GreatDayService greatDayService;

    @Override
    @Async
    public void onApplicationEvent(NewUserRegistrationEvent event) {
        int userId = event.getUserId();
        greatDayService.generateDefaultGreatDays(userId);
    }
}

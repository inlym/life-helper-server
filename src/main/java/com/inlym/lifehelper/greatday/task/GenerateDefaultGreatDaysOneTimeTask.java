package com.inlym.lifehelper.greatday.task;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.inlym.lifehelper.greatday.GreatDayService;
import com.inlym.lifehelper.user.account.entity.User;
import com.inlym.lifehelper.user.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 一次性任务：给存量用户创建默认的纪念日
 *
 * <h2>使用说明
 * <p>当前版本上线后，后任务后台手动触发一次即可，后续直接删除这个任务。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/12
 * @since 1.8.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class GenerateDefaultGreatDaysOneTimeTask extends JavaProcessor {
    private final UserRepository userRepository;

    private final GreatDayService greatDayService;

    @Override
    public ProcessResult process(JobContext context) {
        log.info("[执行任务] 一次性任务：给存量用户创建默认的纪念日");

        for (User user : userRepository.findAll()) {
            greatDayService.generateDefaultGreatDays(user.getId());
        }

        return new ProcessResult(true);
    }
}

package com.inlym.lifehelper.greatday.once;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.inlym.lifehelper.common.base.aliyun.ots.core.model.WideColumnClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 删除初始数据一次性任务
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/4/26
 * @since 2.0.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class GreatDayDeletingDefaultDataOneTimeJob extends JavaProcessor {
    private final WideColumnClient client;

    @Override
    public ProcessResult process(JobContext context) {
        // todo
        // 待补充删除原先已经填充的初始化数据方法
        return new ProcessResult(true);
    }
}

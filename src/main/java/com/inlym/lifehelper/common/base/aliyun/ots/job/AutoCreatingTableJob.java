package com.inlym.lifehelper.common.base.aliyun.ots.job;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.inlym.lifehelper.common.base.aliyun.ots.core.model.WideColumnClient;
import com.inlym.lifehelper.greatday.service.GreatDayRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动建表任务
 *
 * <h2>主要用途
 * <p>自动创建位于阿里云 ots 中的数据表。
 *
 * <h2>说明
 * <p>任务调度托管在阿里云分布式任务调度平台。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/5/28
 * @since 2.0.0
 **/
@Component
@RequiredArgsConstructor
public class AutoCreatingTableJob extends JavaProcessor {
    private static final Logger logger = LoggerFactory.getLogger("schedulerx");

    private final WideColumnClient wideColumnClient;

    private final GreatDayRepository greatDayRepository;

    @Override
    public ProcessResult process(JobContext context) {
        List<String> tableNames = wideColumnClient
            .listTable()
            .getTableNames();

        if (!tableNames.contains(GreatDayRepository.TABLE_NAME)) {
            greatDayRepository.createTable();
            logger.info("成功创建数据表");
        }

        return new ProcessResult(true);
    }
}

package com.weutil.external.wechat.schedule;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.weutil.external.wechat.service.WeChatStableAccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信稳定版接口调用凭据刷新任务
 *
 * <h2>定时任务
 * <p>每3分钟运行一次。
 *
 * <h2>托管说明
 * <p>定时任务的执行逻辑托管在了阿里云的 <a href="https://www.aliyun.com/ntms/middleware/schedulerx?userCode=lzfqdh6g">分布式任务调度平台</a> 上，
 * 此处仅用于留存备忘。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/4/24
 * @since 2.0.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class WeChatStableAccessTokenRefreshingJob extends JavaProcessor {
    private final WeChatStableAccessTokenService weChatStableAccessTokenService;

    /**
     * 执行任务方法
     *
     * @param context 任务上下文
     *
     * @date 2023/4/24
     * @since 2.0.0
     */
    @Override
    public ProcessResult process(JobContext context) {
        try {
            weChatStableAccessTokenService.refreshAll();

            log.info("[Schedule Job] 微信稳定版接口调用凭据刷新任务执行成功，任务实例ID={}", context.getJobInstanceId());
            return new ProcessResult(true);
        } catch (Exception e) {
            log.error("[Schedule Job] 微信稳定版接口调用凭据刷新任务执行失败，任务实例ID={}", context.getJobInstanceId());
            return new ProcessResult(false);
        }
    }
}

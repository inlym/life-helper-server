package com.inlym.lifehelper.extern.wechat.schedule;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.inlym.lifehelper.extern.wechat.service.WeChatAccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 更新微信服务端接口调用凭据定时任务
 *
 * <h2>说明
 * <p>当前类定义了一个定时任务，任务的执行逻辑托管在了阿里云的 <a href="https://www.aliyun.com/ntms/middleware/schedulerx?userCode=lzfqdh6g">分布式任务调度平台</a> 上，
 * 此处仅用于留存备忘。
 *
 * <li> [任务名称]：更新微信服务端接口调用凭据
 * <li> [任务描述]：每半小时强制更新微信服务端接口调用凭据（不管原有凭据是否有效）。
 * <li> [执行时间]：每半小时一次（即每小时的 00 和 30 分）
 *
 * <h2>注意事项
 * <p>在非“生产环境”下，不要开启这个定时任务，会导致微信服务端接口调用凭据的有效性冲突。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/15
 * @since 1.4.0
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshWeChatAccessTokenProcessor extends JavaProcessor {
    private final WeChatAccessTokenService weChatAccessTokenService;

    @Override
    public ProcessResult process(JobContext context) {
        log.info("[定时任务] 更新微信服务端接口调用凭据定时任务");
        weChatAccessTokenService.refresh();

        return new ProcessResult(true);
    }
}

package com.weutil.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 流水线信息
 *
 * <h2>说明
 * <p>CI/CD 阶段的流水线环境变量信息。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/11
 * @see <a href="https://help.aliyun.com/zh/yunxiao/user-guide/environment-variables">环境变量</a>
 * @since 3.0.0
 **/
@Component
@ConfigurationProperties(prefix = "pipeline")
@Data
public class PipelineProperties {
    /** 流水线的运行编号，从1开始，按自然数自增 */
    private String buildNumber;

    /** 本次部署代码版本的 commit ID */
    private String commitSha;

    /** 本次部署代码版本的分支名或标签名 */
    private String commitRefName;
}

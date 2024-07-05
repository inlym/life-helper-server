package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.base.mybatisflex.LogMessageCollector;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.logicdelete.LogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.DateTimeLogicDeleteProcessor;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Flex 配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/10/9
 * @since 2.0.3
 **/
@Configuration
@RequiredArgsConstructor
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {
    private final LogMessageCollector logMessageCollector;

    /**
     * 配置逻辑删除处理器
     *
     * @date 2023/10/9
     * @see <a href="https://mybatis-flex.com/zh/core/logic-delete.html">逻辑删除</a>
     * @since 2.0.3
     */
    @Bean
    public LogicDeleteProcessor logicDeleteProcessor() {
        return new DateTimeLogicDeleteProcessor();
    }

    /**
     * MyBatis-Flex 初始化配置
     *
     * @param config 公共配置
     *
     * @date 2023/10/10
     * @see <a href="https://mybatis-flex.com/zh/base/mybatis-flex-customizer.html">MyBatisFlexCustomizer</a>
     * @since 2.0.3
     */
    @Override
    public void customize(FlexGlobalConfig config) {
        // 设置自定义的 SQL 审计日志收集器
        AuditManager.setMessageCollector(logMessageCollector);

        // 开启审计功能
        AuditManager.setAuditEnable(true);

        // 关闭控制台打印标志
        config.setPrintBanner(false);
    }
}

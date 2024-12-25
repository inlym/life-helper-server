package com.weutil.common.config;

import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.AuditMessage;
import com.mybatisflex.core.audit.MessageCollector;
import com.mybatisflex.core.logicdelete.LogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.DateTimeLogicDeleteProcessor;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import com.weutil.common.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Flex 配置
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @see <a href="https://mybatis-flex.com/zh/base/mybatis-flex-customizer.html">MyBatisFlexCustomizer</a>
 * @since 3.0.0
 **/
@Configuration
public class MyBatisFlexConfig implements MyBatisFlexCustomizer {
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
        AuditManager.setMessageCollector(new LogMessageCollector());

        // 开启审计功能
        AuditManager.setAuditEnable(true);

        // 关闭控制台打印标志
        config.setPrintBanner(false);

        ClientIpListener clientIpListener = new ClientIpListener();
        config.registerInsertListener(clientIpListener, BaseEntity.class);
        config.registerUpdateListener(clientIpListener, BaseEntity.class);
    }

    @Slf4j
    static class LogMessageCollector implements MessageCollector {
        @Override
        public void collect(AuditMessage message) {
            log.info("{} <<< {}ms", message.getFullSql(), message.getElapsedTime());
        }
    }

    static class ClientIpListener implements InsertListener, UpdateListener {
        @Override
        public void onInsert(Object o) {
            String clientIp = MDC.get("CLIENT_IP");
            if (clientIp != null && o instanceof BaseEntity) {
                ((BaseEntity) o).setCreateClientIp(clientIp);
            }
        }

        @Override
        public void onUpdate(Object o) {
            String clientIp = MDC.get("CLIENT_IP");
            if (clientIp != null && o instanceof BaseEntity) {
                ((BaseEntity) o).setUpdateClientIp(clientIp);
            }
        }
    }
}

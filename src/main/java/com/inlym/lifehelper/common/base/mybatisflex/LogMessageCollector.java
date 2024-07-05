package com.inlym.lifehelper.common.base.mybatisflex;

import com.mybatisflex.core.audit.AuditMessage;
import com.mybatisflex.core.audit.MessageCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * SQL 日志审计收集器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/5
 * @see <a href="https://mybatis-flex.com/zh/core/sql-print.html">SQL 日志打印</a>
 * @since 2.3.0
 **/
@Component
@Slf4j
public class LogMessageCollector implements MessageCollector {
    @Override
    public void collect(AuditMessage message) {
        log.info("{} <<< {}ms", message.getFullSql(), message.getElapsedTime());
    }
}

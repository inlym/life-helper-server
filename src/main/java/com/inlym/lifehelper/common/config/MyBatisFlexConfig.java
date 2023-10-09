package com.inlym.lifehelper.common.config;

import com.mybatisflex.core.logicdelete.LogicDeleteProcessor;
import com.mybatisflex.core.logicdelete.impl.DateTimeLogicDeleteProcessor;
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
public class MyBatisFlexConfig {
    /**
     * 配置逻辑删除处理器
     *
     * @see <a href="https://mybatis-flex.com/zh/core/logic-delete.html">逻辑删除</a>
     */
    @Bean
    public LogicDeleteProcessor logicDeleteProcessor() {
        return new DateTimeLogicDeleteProcessor();
    }
}

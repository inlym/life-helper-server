package com.inlym.lifehelper.common.config;

import com.inlym.lifehelper.common.filter.InitialFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * <h2>主要用途
 * <p>注册过滤器。
 *
 * <h2>为什么不直接使用 `@WebFilter` + `@ServletComponentScan` 的方式注册过滤器？
 * <p>大部分过滤器使用这种方式进行注册，但使用 `@WebFilter` 注册过滤器时，无法定义过滤器执行顺序——即使设置了 `@Order` 也是无效的，只能默认按照过滤器名的字典顺序进行排序。
 *
 * <h2>哪些过滤器需要放在这个类中注册？
 * <p>仅需要额外设定执行顺序的过滤器放在这个类中注册，其他不在乎执行顺序的过滤器仍使用传统方式注册。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/2/2
 * @since 1.9.0
 **/
@Configuration
@RequiredArgsConstructor
public class WebFilterConfig {
    private final InitialFilter initialFilter;

    /**
     * 初始化过滤器
     *
     * <h2>注意事项
     * <p>这个过滤器必须放在所有自定义过滤器的最前面。
     */
    @Bean
    public FilterRegistrationBean<InitialFilter> initialFilterBean() {
        FilterRegistrationBean<InitialFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(initialFilter);
        bean.setOrder(-9999);

        return bean;
    }
}

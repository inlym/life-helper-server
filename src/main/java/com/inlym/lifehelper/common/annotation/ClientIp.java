package com.inlym.lifehelper.common.annotation;

import com.inlym.lifehelper.common.annotation.resolver.ClientIpMethodArgumentResolver;

import java.lang.annotation.*;

/**
 * 客户端 IP 地址注入器
 *
 * <h2>主要用途
 * <p>在控制器方法的参数中注入客户端 IP 地址，以便在方法内部快捷获取和使用。
 *
 * <h2>使用示例
 * <p>
 * <pre class="code">
 * &#064;GetMapping("/abc")
 * public Object someMethod(@ClientIp String ip) {
 *     System.out.println("IP 地址是:" + ip);
 *     // ...
 * }
 * </pre> *
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/3/27
 * @see ClientIpMethodArgumentResolver
 * @since 1.0.0
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClientIp {
    String value() default "";
}

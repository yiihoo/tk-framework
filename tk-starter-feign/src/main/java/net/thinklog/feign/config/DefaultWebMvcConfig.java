package net.thinklog.feign.config;

import net.thinklog.feign.resolver.TokenArgumentResolver;
import net.thinklog.feign.service.UserBaseFeignRpcService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * 默认SpringMVC拦截器
 *
 * @author DELL
 */
public class DefaultWebMvcConfig implements WebMvcConfigurer {
    @Lazy
    @Resource
    private UserBaseFeignRpcService userBaseFeignRpcService;

    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userBaseFeignRpcService));
    }
}

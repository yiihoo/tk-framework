package net.thinklog.loadbalancer.config;

import cn.hutool.core.util.StrUtil;
import net.thinklog.common.bean.enums.PlatformEnum;
import net.thinklog.common.config.AuthConstant;
import net.thinklog.common.context.PlatformContextHolder;
import net.thinklog.common.context.TenantContextHolder;
import net.thinklog.common.kit.StrKit;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * feign拦截器，只包含基础数据
 *
 * @author azhao
 */
public class FeignInterceptorConfig {
    /**
     * 使用feign client访问别的微服务时，将上游传过来的client等信息放入header传递给下一个服务
     */
    @Bean
    public RequestInterceptor baseFeignInterceptor() {
        return template -> {
            //传递client
            String tenant = TenantContextHolder.getTenant();
            if (StrUtil.isNotEmpty(tenant)) {
                template.header(AuthConstant.TENANT_HEADER, tenant);
            }
            //设置平台ID
            String platformId = PlatformContextHolder.getPlatformId().toString();
            if (StrKit.isBlank(platformId)) {
                platformId = PlatformEnum.DEFAULT.getValue().toString();
            }
            template.header(AuthConstant.PLATFORM_HEADER, platformId);
        };
    }
}

package net.thinklog.feign.config;

import net.thinklog.feign.service.UserBaseFeignRpcService;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * feign自动配置
 *
 * @author azhao
 * @date 2019/8/13
 */
@EnableFeignClients(basePackageClasses = {UserBaseFeignRpcService.class})
public class DefaultFeignAutoConfigure {
}

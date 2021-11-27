package net.thinklog.feign.config;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 处理自己调用自己错误
 * feign api Ambiguous mapping
 *
 * @author DELL
 */
@ConditionalOnClass({Feign.class})
public class FeignOnlyScanRestControllerConfig {
    @Bean
    public WebMvcRegistrations feignWebRegistrations() {
        RequestMappingHandlerMapping handlerMapping = this.requestMappingHandlerMapping();
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return handlerMapping;
            }
        };
    }

    /**
     * 使SpringMVC只扫描带有@RestController的@RequestMapping，
     * 而忽略掉带有@RequestMapping的FeignClient的接口，从而避免启动报Ambiguous mapping错误
     */
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
            @Override
            protected boolean isHandler(Class<?> beanType) {
                return super.isHandler(beanType) && (AnnotationUtils.findAnnotation(beanType, RestController.class) != null);
            }
        };
    }
}
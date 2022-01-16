package net.thinklog.feign.config;

import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author azhao
 * @date 2019/8/5
 */
@ConditionalOnClass({WebMvcConfigurer.class})
public class WebMvcConfig extends DefaultWebMvcConfig {
}

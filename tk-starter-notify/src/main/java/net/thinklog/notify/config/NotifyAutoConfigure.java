package net.thinklog.notify.config;

import com.zaxxer.hikari.HikariConfig;
import net.thinklog.notify.properties.EventNotifyProperties;
import net.thinklog.notify.properties.NotifyDbProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * feign自动配置
 *
 * @author azhao
 * @date 2019/8/13
 */
@ConditionalOnClass(HikariConfig.class)
@EnableConfigurationProperties({NotifyDbProperties.class, EventNotifyProperties.class})
public class NotifyAutoConfigure {

}

package net.thinklog.notify;

import com.zaxxer.hikari.HikariConfig;
import net.thinklog.notify.properties.EventNotifyProperties;
import net.thinklog.notify.properties.NotifyDbProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * notify自动配置模块
 *
 * @author azhao
 * @date 2019/8/13
 */
@ComponentScan
@ConditionalOnClass(HikariConfig.class)
@ConditionalOnProperty(name = "tk.notify.type", havingValue = "db")
@EnableConfigurationProperties({NotifyDbProperties.class, EventNotifyProperties.class})
public class NotifyAutoConfigure {

}

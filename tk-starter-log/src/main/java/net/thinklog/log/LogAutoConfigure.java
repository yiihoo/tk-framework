package net.thinklog.log;

import com.zaxxer.hikari.HikariConfig;
import net.thinklog.log.properties.AuditLogProperties;
import net.thinklog.log.properties.LogDbProperties;
import net.thinklog.log.properties.TraceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动配置
 *
 * @author azhao
 * @date 2019/8/13
 */
@EnableConfigurationProperties({TraceProperties.class, AuditLogProperties.class})
public class LogAutoConfigure {
    /**
     * 日志数据库配置
     */
    @Configuration
    @ConditionalOnClass(HikariConfig.class)
    @EnableConfigurationProperties(LogDbProperties.class)
    public static class LogDbAutoConfigure {}
}

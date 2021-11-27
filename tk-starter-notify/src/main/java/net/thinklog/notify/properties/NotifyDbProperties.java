package net.thinklog.notify.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * feign数据源配置
 * logType=db时生效(非必须)，如果不配置则使用当前数据源
 *
 * @author azhao
 * @date 2020/2/8

 */
@Setter
@Getter
@ConfigurationProperties(prefix = "tk.notify.datasource")
public class NotifyDbProperties extends HikariConfig {
}

package net.thinklog.notify.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author azhao
 * 2021/8/7 8:46
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "tk.notify")
public class EventNotifyProperties {
    List<String> url;
}

package net.thinklog.database.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.thinklog.common.bean.properties.TenantProperties;
import net.thinklog.common.context.TenantContextHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 多租户自动配置
 *
 * https://baomidou.com/guide/interceptor-tenant-line.html#tenantlineinnerinterceptor
 * @author azhao
 * @date 2019/8/5
 */
@EnableConfigurationProperties(TenantProperties.class)
public class TenantAutoConfigure {
    @Autowired
    private TenantProperties tenantProperties;

    @Bean
    public TenantLineHandler tenantHandler() {
        return new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                Long tenant = TenantContextHolder.getTenant();
                if (tenant != null) {
                    return new StringValue(TenantContextHolder.getTenant()+"");
                }
                return new NullValue();
            }
        };
    }
}

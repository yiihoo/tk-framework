package net.thinklog.log.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.TtlMDCAdapter;

/**
 * 初始化TtlMDCAdapter实例，并替换MDC中的adapter对象
 *
 * @author azhao
 * @date 2019/8/17
 */
public class TtlMDCAdapterInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        //加载TtlMDCAdapter实例
        TtlMDCAdapter.getInstance();
    }
}
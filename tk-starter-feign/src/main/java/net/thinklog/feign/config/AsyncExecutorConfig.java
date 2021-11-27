package net.thinklog.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author azhao
 * 2021/7/30 23:46
 */
@EnableAsync
public class AsyncExecutorConfig implements AsyncConfigurer {
    public static final String ASYNC_NAME = "tc-async-executor";
    public static final String ASYNC_PREFIX = "tc-async-executor-";

    /**
     * 自定义线程池
     */
    @Override
    @Bean(ASYNC_NAME)
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //此方法返回可用处理器的虚拟机的最大数量; 不小于1
        int core = Runtime.getRuntime().availableProcessors();
        //设置核心线程数
        executor.setCorePoolSize(core);
        //设置最大线程数
        executor.setMaxPoolSize(core * 2 + 1);
        //除核心线程外的线程存活时间
        executor.setKeepAliveSeconds(3);
        //如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setQueueCapacity(40);
        //线程名称前缀
        executor.setThreadNamePrefix(ASYNC_PREFIX);
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}

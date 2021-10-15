package com.github.far2away.core.spring.async;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * async线程池配置
 *
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
public class AsyncExecutorConfig {

    private static final String BEAN_NAME_ASYNC_EXECUTOR = "asyncExecutor";

    @Bean
    @Primary
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor applicationExecutor = new ThreadPoolTaskExecutor();
        applicationExecutor.setCorePoolSize(2);
        applicationExecutor.setMaxPoolSize(4);
        applicationExecutor.setKeepAliveSeconds(60);
        applicationExecutor.setQueueCapacity(2000);
        applicationExecutor.setThreadNamePrefix("async-");
        applicationExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return applicationExecutor;
    }

    @Bean
    public AsyncConfigurer asyncConfigurer(@Qualifier(BEAN_NAME_ASYNC_EXECUTOR) ThreadPoolTaskScheduler asyncExecutor) {
        return new AsyncConfigurer() {
            @Override
            public Executor getAsyncExecutor() {
                return asyncExecutor;
            }

            @Override
            public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return new AsyncErrorHandler();
            }
        };
    }

    @ConditionalOnClass(MeterRegistry.class)
    @ConditionalOnBean(MeterRegistry.class)
    static class ScheduleExecutorMetrics {

        @Bean
        public ExecutorServiceMetrics taskSchedulerMetrics(MeterRegistry registry,
                                                           @Qualifier(BEAN_NAME_ASYNC_EXECUTOR) ThreadPoolTaskScheduler asyncExecutor) {
            ExecutorServiceMetrics executorServiceMetrics = new ExecutorServiceMetrics(asyncExecutor.getScheduledThreadPoolExecutor(),
                "async_executor", Tags.empty());
            executorServiceMetrics.bindTo(registry);
            return executorServiceMetrics;
        }

    }

}

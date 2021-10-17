package com.github.far2away.core.spring.async;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步任务自动配置类
 *
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
@Configuration
@AutoConfigureBefore(TaskExecutionAutoConfiguration.class)
@Import(AsyncMetricsAspect.class)
@ConditionalOnProperty(name = "far2away.core.async.executor.enabled", havingValue = "true", matchIfMissing = true)
public class AsyncAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_async_metrics_auto_configured");
    }

    @ConditionalOnClass(MeterRegistry.class)
    static class AsyncExecutorMetrics {

        private static final String METRICS_NAME_ASYNC_THREAD_POOL = "async_thread_pool";

        @Bean
        public ExecutorServiceMetrics applicationTaskExecutorMetrics(MeterRegistry registry, ThreadPoolTaskExecutor applicationTaskExecutor) {
            ExecutorServiceMetrics executorServiceMetrics = new ExecutorServiceMetrics(applicationTaskExecutor.getThreadPoolExecutor(),
                METRICS_NAME_ASYNC_THREAD_POOL, Tags.empty());
            executorServiceMetrics.bindTo(registry);
            log.debug("far2away_core_async_thread_pool_metrics_{}_configured", METRICS_NAME_ASYNC_THREAD_POOL);
            return executorServiceMetrics;
        }

    }

}

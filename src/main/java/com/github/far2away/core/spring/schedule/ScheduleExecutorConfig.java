package com.github.far2away.core.spring.schedule;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * schedule线程池配置
 *
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
@ConditionalOnProperty(name = "far2away.core.schedule.executor.enabled", havingValue = "true", matchIfMissing = true)
public class ScheduleExecutorConfig {

    private static final String BEAN_NAME_SCHEDULE_EXECUTOR = "scheduleExecutor";

    @Bean(BEAN_NAME_SCHEDULE_EXECUTOR)
    public ThreadPoolTaskScheduler scheduleExecutor() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(2);
        taskScheduler.setThreadNamePrefix("schedule-");
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return taskScheduler;
    }

    @Bean
    public SchedulingConfigurer schedulingConfigurer(@Qualifier(BEAN_NAME_SCHEDULE_EXECUTOR) ThreadPoolTaskScheduler scheduleExecutor) {
        return taskRegistrar -> {
            taskRegistrar.setTaskScheduler(scheduleExecutor);
            log.info("far2away_core_schedule_executor_configured");
        };
    }

    @ConditionalOnClass(MeterRegistry.class)
    @ConditionalOnBean(MeterRegistry.class)
    static class ScheduleExecutorMetrics {

        @Bean
        public ExecutorServiceMetrics taskSchedulerMetrics(MeterRegistry registry,
                                                           @Qualifier(BEAN_NAME_SCHEDULE_EXECUTOR) ThreadPoolTaskScheduler scheduleExecutor) {
            ExecutorServiceMetrics executorServiceMetrics = new ExecutorServiceMetrics(scheduleExecutor.getScheduledThreadPoolExecutor(),
                "schedule_executor", Tags.empty());
            executorServiceMetrics.bindTo(registry);
            return executorServiceMetrics;
        }

    }

}

package com.github.far2away.core.spring.scheduling;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@AutoConfigureBefore(TaskSchedulingAutoConfiguration.class)
@Import({SchedulingCorePoolSizeCustomizer.class, SchedulingMetricsAspect.class})
@ConditionalOnProperty(name = "far2away.core.schedule.enabled", havingValue = "true", matchIfMissing = true)
public class SchedulingAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_schedule_auto_configured");
    }

    /**
     * 定时任务线程池-增加error handler
     *
     * @return the task scheduler customizer
     */
    @Bean
    public TaskSchedulerCustomizer taskSchedulerErrorHandlerCustomizer() {
        return taskScheduler -> {
            //自定义定时任务的错误处理器，增加指标
            taskScheduler.setErrorHandler(new SchedulingErrorHandlerCustomizer());
            log.debug("far2away_core_schedule_error_handler_configured");
        };
    }

    @ConditionalOnClass(MeterRegistry.class)
    static class ScheduleExecutorMetrics {

        private static final String METRICS_NAME_SCHEDULING_THREAD_POOL = "scheduling_thread_pool";

        @Bean
        public ExecutorServiceMetrics taskSchedulerMetrics(MeterRegistry registry, ThreadPoolTaskScheduler taskScheduler) {
            ExecutorServiceMetrics executorServiceMetrics = new ExecutorServiceMetrics(taskScheduler.getScheduledThreadPoolExecutor(),
                METRICS_NAME_SCHEDULING_THREAD_POOL, Tags.empty());
            executorServiceMetrics.bindTo(registry);
            log.debug("far2away_core_schedule_thread_pool_metrics_{}_configured", METRICS_NAME_SCHEDULING_THREAD_POOL);
            return executorServiceMetrics;
        }

    }

}

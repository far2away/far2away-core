package com.github.far2away.core.spring.schedule;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "far2away.core.schedule.enabled", havingValue = "true", matchIfMissing = true)
public class SpringScheduleAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_schedule_auto_configured");
    }

    @Bean
    public TaskSchedulerCustomizer taskSchedulerCustomizer() {
        return taskScheduler -> {
            //自定义定时任务的错误处理器，增加指标
            taskScheduler.setErrorHandler(new SpringScheduleErrorHandler());
        };
    }

}

package com.github.far2away.core.spring.schedule;

import io.micrometer.core.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
public class ScheduleCorePoolSizeCustomizer implements TaskSchedulerCustomizer, EnvironmentAware {

    private static final String PROPERTY_SCHEDULE_CORE_POOL_SIZE = "spring.task.scheduling.pool.size";

    private static final Integer DEFAULT_SCHEDULE_POOL_SIZE = 3;

    @Nullable
    private Environment environment;

    @Override
    public void customize(ThreadPoolTaskScheduler taskScheduler) {
        Assert.notNull(environment, "environment_not_initialized");
        String coolPoolSize = environment.getProperty(PROPERTY_SCHEDULE_CORE_POOL_SIZE);
        if (StringUtils.hasText(coolPoolSize)) {
            return;
        }

        //如果没有配置核心线程池大小，更改为3
        taskScheduler.setPoolSize(DEFAULT_SCHEDULE_POOL_SIZE);
        log.debug("far2away_core_schedule_cool_pool_size_to_{}_configured", DEFAULT_SCHEDULE_POOL_SIZE);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}

package com.github.far2away.core.spring.metrics;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@ConditionalOnClass({Timed.class, MeterRegistry.class})
@ConditionalOnBean(MeterRegistry.class)
@AutoConfigureAfter(MetricsAutoConfiguration.class)
public class TimeAspectAutoConfig {

    /**
     * Enable timed aspect annotation.
     *
     * @param registry the registry
     * @return the timed aspect
     */
    @Bean
    @ConditionalOnMissingBean(TimedAspect.class)
    public TimedAspect timedAspect(MeterRegistry registry) {
        log.info("far2away_core_time_aspect_annotation_configured");
        return new TimedAspect(registry);
    }

}


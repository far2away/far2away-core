package com.github.far2away.core.spring.metrics;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Import(MeterRegistryInitialize.class)
@ConditionalOnProperty(name = "far2away.core.metrics.enabled", havingValue = "true", matchIfMissing = true)
public class MetricsAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_metrics_auto_configured");
    }

}

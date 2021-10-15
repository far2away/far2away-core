package com.github.far2away.core.spring.async;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Import(AsyncExecutorConfig.class)
@ConditionalOnProperty(name = "far2away.core.async.executor.enabled", havingValue = "true", matchIfMissing = true)
public class AsyncAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_async_executor_auto_configured");
    }

}

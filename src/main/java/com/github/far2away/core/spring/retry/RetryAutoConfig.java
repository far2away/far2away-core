package com.github.far2away.core.spring.retry;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.retry.annotation.RetryConfiguration;

/**
 * retry的自动配置
 *
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(RetryConfiguration.class)
@ConditionalOnBean(RetryConfiguration.class)
@ConditionalOnProperty(name = "far2away.core.retry.enabled", havingValue = "true", matchIfMissing = true)
public class RetryAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_retry_auto_configured");
    }

    @Bean
    public RetryLogListener retryLogListener() {
        return new RetryLogListener();
    }

}

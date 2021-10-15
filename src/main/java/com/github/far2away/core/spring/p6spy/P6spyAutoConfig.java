package com.github.far2away.core.spring.p6spy;

import com.p6spy.engine.spy.P6SpyDriver;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * p6spy自动配置
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@ConditionalOnClass(P6SpyDriver.class)
@Import(P6spyBeanPostProcessor.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(P6spyProperties.class)
@ConditionalOnProperty(prefix = P6spyProperties.P6SPY_CONFIG_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class P6spyAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_p6spy_auto_configured");
    }

    @Bean
    public P6spyInitializer p6spyInitializer(P6spyProperties p6spyProperties) {
        return new P6spyInitializer(p6spyProperties);
    }

}

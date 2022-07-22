package com.github.far2away.core.spring.context;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

/**
 * spring context自动配置类
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Import(SpringContextHolder.class)
public class SpringContextAutoConfig {

    @PostConstruct
    public void initLog() {
        log.debug("far2away_core_spring_context_auto_configured");
    }

}

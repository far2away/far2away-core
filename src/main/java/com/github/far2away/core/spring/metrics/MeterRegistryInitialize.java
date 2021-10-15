package com.github.far2away.core.spring.metrics;

import com.github.far2away.core.util.holder.MetricsUtils;
import com.github.far2away.core.util.holder.SpringUtils;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Component
@ConditionalOnClass(MeterRegistry.class)
public class MeterRegistryInitialize implements ApplicationContextAware {

    @Nullable
    private ApplicationContext applicationContext;

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        if (!applicationContext.equals(this.applicationContext)) {
            return;
        }

        Object meterRegistry = SpringUtils.getBeanSilently(MeterRegistry.class);
        if (Objects.nonNull(meterRegistry)) {
            MetricsUtils.openMetrics();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}

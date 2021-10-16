package com.github.far2away.core.spring.async;

import com.github.far2away.core.util.holder.MetricsUtils;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass({Aspect.class, MeterRegistry.class})
public class AsyncMetricsAspect {

    @PostConstruct
    public void initLog() {
        log.debug("far2away_core_async_exec_metrics_configured");
    }

    @Around("execution (@org.springframework.scheduling.annotation.Async  * *.*(..))")
    public Object traceBackgroundThread(final ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        StringBuilder sb = new StringBuilder();
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } catch (Throwable ex) {
            sb.append(ex.getClass().getName());
            throw ex;
        } finally {
            long end = System.currentTimeMillis();
            MetricsUtils.recordAsyncExec(end - start, className, methodName, sb.toString());
        }
    }

}

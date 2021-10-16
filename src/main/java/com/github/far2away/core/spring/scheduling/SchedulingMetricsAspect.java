package com.github.far2away.core.spring.scheduling;

import com.github.far2away.core.util.holder.MetricsUtils;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * @author far2away
 * @since 2021/10/16
 */
@Slf4j
@Aspect
@ConditionalOnClass({Aspect.class, MeterRegistry.class})
public class SchedulingMetricsAspect {

    @PostConstruct
    public void initLog() {
        log.debug("far2away_core_schedule_exec_metrics_configured");
    }

    @Around("execution (@org.springframework.scheduling.annotation.Scheduled  * *.*(..))")
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
            MetricsUtils.recordSchedulingExec(end - start, className, methodName, sb.toString());
        }
    }

}

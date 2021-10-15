package com.github.far2away.core.spring.context;

import java.util.Objects;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

/**
 * @author far2away
 * @since 2021/10/15
 */
public class SpringContextHolder implements ApplicationContextAware {

    @Nullable
    private volatile static ApplicationContext APPLICATION_CONTEXT_HOLDER = null;

    public static ApplicationContext getApplicationContext() {
        ApplicationContext applicationContextHolder = APPLICATION_CONTEXT_HOLDER;
        if (Objects.isNull(applicationContextHolder)) {
            throw new RuntimeException("SpringContext_not_initialized");
        }
        return applicationContextHolder;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT_HOLDER = applicationContext;
    }

}

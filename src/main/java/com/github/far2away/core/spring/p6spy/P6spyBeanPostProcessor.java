package com.github.far2away.core.spring.p6spy;

import com.p6spy.engine.spy.P6DataSource;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

/**
 * p6spy后置处理器，把数据源包装成p6spy代理
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
public class P6spyBeanPostProcessor implements BeanPostProcessor, Ordered, ApplicationContextAware {

    @Nullable
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource && Objects.nonNull(applicationContext)) {
            //make sure p6spy initializer has been created
            this.applicationContext.getBean(P6spyInitializer.class);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            //proxy datasource with p6spy to record sql
            log.info("successfully_delegated_datasource_{}_{}", bean.getClass().getSimpleName(), beanName);
            return new P6DataSource((DataSource) bean);
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}

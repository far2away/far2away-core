package com.github.far2away.core.spring.exception;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@Import({FeignExceptionHandler.class, DuplicateKeyExceptionHandler.class, GlobalExceptionHandler.class})
@ConditionalOnProperty(name = "far2away.core.exception.globalHandler.enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionHandlerAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_exception_handler_auto_configured");
    }

    @Bean
    public FilterRegistrationBean<GlobalExceptionFilter> globalExceptionFilterRegistrationBean() {
        FilterRegistrationBean<GlobalExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GlobalExceptionFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 102);
        return registrationBean;
    }

}

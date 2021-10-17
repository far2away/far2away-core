package com.github.far2away.core.spring.i18n;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 国际化自动配置类
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnProperty(name = "far2away.core.i18n.enabled", havingValue = "true", matchIfMissing = true)
public class I18nAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_i18n_auto_configured");
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(LocaleContextHolder.getLocale());
        return localeResolver;
    }

    @Bean
    public I18nLocaleChangeInterceptor i18nLocaleChangeInterceptor() {
        return new I18nLocaleChangeInterceptor();
    }

    /**
     * 添加I18n拦截器
     */
    @Bean
    WebMvcConfigurer webMvcConfigurer(I18nLocaleChangeInterceptor i18nLocaleChangeInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(i18nLocaleChangeInterceptor);
            }
        };
    }

    /**
     * 添加I18n过滤器
     */
    @Bean
    public FilterRegistrationBean<I18nLocaleFilter> i18nLocaleFilterRegistrationBean() {
        FilterRegistrationBean<I18nLocaleFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new I18nLocaleFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER - 104);
        return registrationBean;
    }

}

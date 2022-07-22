package com.github.far2away.core.spring.mybatis;

import com.github.pagehelper.PageInterceptor;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * page Interceptor分页插件配置类
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@ConditionalOnClass(PageInterceptor.class)
public class PageInterceptorConfig {

    @Bean
    @ConditionalOnMissingBean(PageInterceptor.class)
    public Interceptor pageInterceptor() {
        log.debug("far2away_core_mybatis_page_interceptor_configured");
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("offsetAsPageNum", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}

package com.github.far2away.core.spring.mybatis;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ObjectUtils;

/**
 * mybatis自动配置类
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class})
@Import(PageInterceptorConfig.class)
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnProperty(name = "far2away.core.mybatis.enabled", havingValue = "true", matchIfMissing = true)
public class MybatisAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_mybatis_auto_configured");
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, ObjectProvider<Interceptor[]> interceptorsProvider) throws IOException {
        log.info("far2away_core_mybatis_sql_session_configured");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Slf4jImpl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);

        //增加拦截插件，例如page helper
        Interceptor[] interceptors = interceptorsProvider.getIfAvailable();
        if (!ObjectUtils.isEmpty(interceptors)) {
            sqlSessionFactoryBean.setPlugins(interceptors);
        }

        //增加默认包路径，resource下面的mapper
        PathMatchingResourcePatternResolver pathM3R = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(pathM3R.getResources("classpath*:mapper/**/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

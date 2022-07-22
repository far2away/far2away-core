package com.github.far2away.core.spring.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jackson自动配置类
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@ConditionalOnClass(ObjectMapper.class)
@ConditionalOnProperty(name = "far2away.core.jackson.enabled", havingValue = "true", matchIfMissing = true)
public class JacksonAutoConfig {

    private static final String JACKSON_CONFIG_PREFIX = "far2away.core.jackson";

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_jackson_auto_configured");
    }

    @Bean
    @ConditionalOnProperty(prefix = JACKSON_CONFIG_PREFIX, name = "i18nDetails.enabled", havingValue = "true", matchIfMissing = true)
    public Module i18nMessageDetailsModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(I18nMessageDetailsSerializable.INSTANCE);
        log.debug("far2away_core_jackson_i18nDetails_serialize_configured");
        return module;
    }

    @Bean
    @ConditionalOnProperty(prefix = JACKSON_CONFIG_PREFIX, name = "i18nEnum.enabled", havingValue = "true", matchIfMissing = true)
    public Module i18nMessageEnumDetailsModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(I18nMessageEnumDetailsSerializable.INSTANCE);
        log.debug("far2away_core_jackson_i18nEnum_serialize_configured");
        return module;
    }

    @Bean
    @ConditionalOnProperty(prefix = JACKSON_CONFIG_PREFIX, name = "number2string.enabled", havingValue = "true", matchIfMissing = true)
    public Module number2StringModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        module.addSerializer(BigInteger.class, ToStringSerializer.instance);
        module.addSerializer(BigDecimal.class, BigDecimalAsStringSerializable.INSTANCE);
        log.debug("far2away_core_jackson_number2string_configured");
        return module;
    }

    @Bean
    @ConditionalOnProperty(prefix = JACKSON_CONFIG_PREFIX, name = "date2timestamp.enabled", havingValue = "true", matchIfMissing = true)
    public Module date2TimestampModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateSerializer(true, null));
        module.addSerializer(ZonedDateTime.class, ZoneDateTimeSerializable.INSTANCE);
        log.debug("far2away_core_jackson_date2timestamp_configured");
        return module;
    }

    @Bean
    @ConditionalOnProperty(prefix = JACKSON_CONFIG_PREFIX, name = "defaultSetting.enabled", havingValue = "true", matchIfMissing = true)
    Jackson2ObjectMapperBuilderCustomizer customJacksonCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializationInclusion(Include.ALWAYS);
            jacksonObjectMapperBuilder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            log.debug("far2away_core_jackson_defaultSetting_configured");
        };
    }

}

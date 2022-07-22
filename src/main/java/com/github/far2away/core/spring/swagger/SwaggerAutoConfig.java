package com.github.far2away.core.spring.swagger;

import com.github.far2away.core.definition.constant.StringConstants;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.boot.starter.autoconfigure.OpenApiAutoConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.oas.configuration.OpenApiDocumentationConfiguration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger自动配置类
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(OpenApiDocumentationConfiguration.class)
@AutoConfigureBefore(OpenApiAutoConfiguration.class)
@EnableOpenApi
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfig {

    @PostConstruct
    public void initLog() {
        log.info("far2away_core_swagger_auto_configured");
    }

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket docket(SwaggerProperties swaggerProperties) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo(swaggerProperties));

        //jwt config, default is true
        if (swaggerProperties.isJwtEnabled()) {
            docket.securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));
        }

        return docket.select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .build();
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        Contact contact = new Contact(swaggerProperties.getContactName(),
            StringConstants.EMPTY, swaggerProperties.getContactEmail());
        return new ApiInfoBuilder()
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .contact(contact)
            .version(swaggerProperties.getVersion())
            .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

}

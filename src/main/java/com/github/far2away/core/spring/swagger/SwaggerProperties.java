package com.github.far2away.core.spring.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Setter
@Getter
@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_CONFIG_PREFIX)
public class SwaggerProperties {

    public static final String SWAGGER_CONFIG_PREFIX = "far2away.core.swagger";

    /**
     * # Whether to enable jwt token, true is enable, default false
     */
    private boolean jwtEnabled = false;

    /**
     * # specifies the swagger api title # (default is Swagger API)
     */
    private String title = "Swagger API";

    /**
     * # specifies the swagger api description # (default is Swagger API Info)
     */
    private String description = "Swagger API Info";

    /**
     * # specifies the swagger api contact name # (default is far2away)
     */
    private String contactName = "far2away";

    /**
     * # specifies the swagger api contact email # (default is empty)
     */
    private String contactEmail = "";

    /**
     * # specifies the swagger api version # (default is V1)
     */
    private String version = "V1";

}

package com.github.far2away.core.spring.p6spy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * p6spy配置属性
 *
 * @author far2away
 * @since 2021/10/15
 */
@Setter
@Getter
@ConfigurationProperties(prefix = P6spyProperties.P6SPY_CONFIG_PREFIX)
public class P6spyProperties {

    public static final String P6SPY_CONFIG_PREFIX = "far2away.core.p6spy";

    /**
     * # specifies the appender to use for logging # (default is com.github.far2away.core.spring.p6spy.P6spySqlLogger)
     */
    private String appender = "com.github.far2away.core.spring.p6spy.P6spySqlLogger";

    /**
     * # class to use for formatting log messages (default is com.github.far2away.core.spring.p6spy.P6spySqlFormat)
     */
    private String logMessageFormat = "com.github.far2away.core.spring.p6spy.P6spySqlFormat";

    /**
     * # sets the date format using Java's SimpleDateFormat routine (default is yyyy-MM-dd HH:mm:ss.SSS)
     */
    private String dateformat = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * # format that is used for logging of the java.util.Date implementations (default is yyyy-MM-dd HH:mm:ss.SSS)
     */
    private String databaseDialectDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * # format that is used for logging of the java.sql.Timestamp implementations (default is yyyy-MM-dd HH:mm:ss.SSS)
     */
    private String databaseDialectTimestampFormat = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * # whether to expose options via JMX or not (default is false)
     */
    private String jmx = "false";

}

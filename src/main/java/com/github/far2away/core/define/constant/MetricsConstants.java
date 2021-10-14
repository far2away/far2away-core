package com.github.far2away.core.define.constant;

import lombok.experimental.UtilityClass;

/**
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class MetricsConstants {

    /**
     * 统一所有指标前缀
     */
    public final String METRICS_CORE_PREFIX = "core_";

    public final String METRICS_NAME_UNKNOWN_EXCEPTION = METRICS_CORE_PREFIX + "global_exception_handler";

    public final String METRICS_NAME_SQL_EXEC = METRICS_CORE_PREFIX + "sql_exec";

    public final String METRICS_NAME_LONG_SQL_EXEC = METRICS_CORE_PREFIX + "long_sql_exec";

    public final String METRICS_NAME_UNEXPECTED_EXCEPTION_ON_TASK = METRICS_CORE_PREFIX + "unexpected_exception_on_task";

    public final String TAG_NAME_EXCEPTION_NAME = "exception_name";

    public final String TAG_NAME_RESPONSE_CODE = "response_code";

    public final String TAG_NAME_SQL_PREPARED_MD5 = "md5_prepared";

    public final String TAG_NAME_CLASS_NAME = "class_name";

    public final String TAG_NAME_METHOD_NAME = "method_name";

}

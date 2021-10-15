package com.github.far2away.core.definition.constant;

import lombok.experimental.UtilityClass;

/**
 * 指标常量，用于记录指标，一般用于内部使用
 *
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

    public final String METRICS_NAME_UNEXPECTED_EXCEPTION_ON_ASYNC = METRICS_CORE_PREFIX + "unexpected_exception_on_task_async";

    public final String TAG_NAME_URL = "url";

    public final String TAG_NAME_EXCEPTION_NAME = "exception_name";

    public final String TAG_NAME_RESPONSE_CODE = "response_code";

    public final String TAG_NAME_SQL_PREPARED_MD5 = "md5_prepared";

    public final String TAG_NAME_CLASS_NAME = "class_name";

    public final String TAG_NAME_METHOD_NAME = "method_name";

}

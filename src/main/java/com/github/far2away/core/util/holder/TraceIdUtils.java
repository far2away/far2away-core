package com.github.far2away.core.util.holder;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

/**
 * 获取trace id的工具类
 *
 * <note>
 * 请在引入spring cloud sleuth后使用，否则可能返回空
 * </note>
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class TraceIdUtils {

    private final String MDC_TRACE_ID_KEY = "traceId";

    /**
     * 获取Trade Id
     *
     * @return the trace id
     */
    public String getTraceId() {
        return MDC.get(MDC_TRACE_ID_KEY);
    }

}

package com.github.far2away.core.util.holder;

import com.github.far2away.core.definition.constant.MetricsConstants;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

/**
 * 指标工具类，仅仅供Core模块中使用
 * <b>在spring初始化后-具体是ContextRefreshedEvent事件发出后，此指标工具才会生效</b>
 *
 * @author far2away
 * @since 2021/10/14
 */
@Slf4j
@UtilityClass
public class MetricsUtils {

    private final AtomicBoolean METRICS_ENABLE = new AtomicBoolean(false);

    public void openMetrics() {
        log.info("far2away_core_open_metrics");
        METRICS_ENABLE.set(true);
    }

    public Optional<MeterRegistry> getOptionalInstance() {
        if (!METRICS_ENABLE.get()) {
            return Optional.empty();
        }
        return Optional.ofNullable(MeterRegistryHolder.INSTANCE);
    }

    /**
     * 记录指标Counter，并且结果加1
     *
     * @param name counter名字
     * @param tags tags指标标签，可为null或者空, Collections.emptyMap()
     */
    public void counter(String name, @Nullable Map<String, String> tags) {
        Optional<MeterRegistry> optionalInstance = getOptionalInstance();
        if (!optionalInstance.isPresent()) {
            return;
        }
        MeterRegistry meterRegistry = optionalInstance.get();
        Counter counter = meterRegistry.counter(name, mapToTagList(tags));
        counter.increment();
    }

    /**
     * 记录指标Timer
     *
     * @param name     timer名字
     * @param duration 记录的时间
     * @param tags     tags指标标签，可为null或者空, Collections.emptyMap()
     */
    public void timer(String name, Duration duration, @Nullable Map<String, String> tags) {
        Optional<MeterRegistry> optionalInstance = getOptionalInstance();
        if (!optionalInstance.isPresent()) {
            return;
        }
        MeterRegistry meterRegistry = optionalInstance.get();
        Timer timer = meterRegistry.timer(name, mapToTagList(tags));
        timer.record(duration);
    }

    /**
     * 特定用途：记录全局异常
     *
     * @param exceptionName the exception name
     * @param responseCode  the response code
     */
    public void recordGlobalException(String url, String exceptionName, String responseCode) {
        Map<String, String> tags = new HashMap<>(3);
        tags.put(MetricsConstants.TAG_NAME_URL, url);
        tags.put(MetricsConstants.TAG_NAME_EXCEPTION_NAME, exceptionName);
        tags.put(MetricsConstants.TAG_NAME_RESPONSE_CODE, responseCode);
        counter(MetricsConstants.METRICS_NAME_UNKNOWN_EXCEPTION, tags);
    }

    /**
     * 特定用途：记录SQL执行
     *
     * @param duration the duration
     */
    public void recordSqlExec(Duration duration) {
        timer(MetricsConstants.METRICS_NAME_SQL_EXEC, duration, Collections.emptyMap());
    }

    /**
     * 特定用途：记录长SQL
     *
     * @param duration the duration
     * @param md5      the md 5
     */
    public void recordLongSqlExec(Duration duration, String md5) {
        Map<String, String> tags = new HashMap<>(1);
        tags.put(MetricsConstants.TAG_NAME_SQL_PREPARED_MD5, md5);
        timer(MetricsConstants.METRICS_NAME_LONG_SQL_EXEC, duration, tags);
    }

    /**
     * 特定用途：记录定时任务中异常
     *
     * @param exceptionName the exception name
     * @param className     the class name
     * @param methodName    the method name
     */
    public void recordUnexpectedExceptionOnTask(String exceptionName, String className, String methodName) {
        Map<String, String> tags = new HashMap<>(3);
        tags.put(MetricsConstants.TAG_NAME_EXCEPTION_NAME, exceptionName);
        tags.put(MetricsConstants.TAG_NAME_CLASS_NAME, className);
        tags.put(MetricsConstants.TAG_NAME_METHOD_NAME, methodName);
        counter(MetricsConstants.METRICS_NAME_UNEXPECTED_EXCEPTION_ON_TASK, tags);
    }

    private List<Tag> mapToTagList(@Nullable Map<String, String> tags) {
        if (Objects.isNull(tags)) {
            return Collections.emptyList();
        }
        return tags.entrySet()
            .stream()
            .map(e -> Tag.of(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }

    private static class MeterRegistryHolder {

        @Nullable
        private static final MeterRegistry INSTANCE = SpringUtils.getBeanSilently(MeterRegistry.class);

    }

}

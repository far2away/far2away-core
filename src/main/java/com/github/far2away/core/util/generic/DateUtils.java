package com.github.far2away.core.util.generic;

import com.github.far2away.core.definition.constant.DateFormatterHolders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * 日期工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class DateUtils {

    @Nullable
    public LocalDateTime parseToLocalDateTime(@Nullable String dateStr, DateFormatterHolders dateFormatter) {
        return parseToLocalDateTime(dateStr, dateFormatter.getFormatter());
    }

    @Nullable
    public LocalDateTime parseToLocalDateTime(@Nullable String dateStr, DateTimeFormatter formatter) {
        if (Objects.isNull(dateStr)) {
            return null;
        }
        return LocalDateTime.parse(dateStr.trim(), formatter);
    }

    @Nullable
    public LocalDate parseToLocalDate(@Nullable String dateStr, DateFormatterHolders dateFormatter) {
        return parseToLocalDate(dateStr, dateFormatter.getFormatter());
    }

    @Nullable
    public LocalDate parseToLocalDate(@Nullable String dateStr, DateTimeFormatter formatter) {
        if (Objects.isNull(dateStr)) {
            return null;
        }
        return LocalDate.parse(dateStr.trim(), formatter);
    }

    /**
     * 把日期格式化为指定格式的字符串
     *
     * @param temporal      the temporal
     * @param dateFormatter the date formatter
     * @return string
     */
    public String format(TemporalAccessor temporal, DateFormatterHolders dateFormatter) {
        return dateFormatter.getFormatter().format(temporal);
    }

}

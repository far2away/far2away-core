package com.github.far2away.core.definition.constant;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 定义常见的日期格式
 *
 * @author far2away
 * @since 2021/10/14
 */
@Getter
@RequiredArgsConstructor
public enum DateFormatterHolders {

    LOCAL_DATE_TIME_GENERIC(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    LOCAL_DATE_TIME_ALL_NUMBER(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")),

    LOCAL_DATE_ISO(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    LOCAL_DATE_AMERICAN(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
    LOCAL_DATE_ALL_NUMBER(DateTimeFormatter.ofPattern("yyyyMMdd")),

    OFFSET_DATE_TIME_GENERIC(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxxx")),
    ;

    private final DateTimeFormatter formatter;

}

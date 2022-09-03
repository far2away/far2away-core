package com.github.far2away.core.definition.constant;

import java.text.DecimalFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 定义常见的数字格式
 *
 * @author far2away
 * @since 2021/10/14
 */
@Getter
@RequiredArgsConstructor
public enum DecimalFormatHolders {

    THOUSANDS_SEPARATOR(new DecimalFormat("#,##0")),
    TWO_DECIMAL(new DecimalFormat("##0.00")),
    THOUSANDS_SEPARATOR_WITH_TWO_DECIMAL(new DecimalFormat("#,##0.00")),
    ;

    private final DecimalFormat formatter;

}

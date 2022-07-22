package com.github.far2away.core.spring.p6spy;

import com.github.far2away.core.util.generic.StrUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * p6spy sql格式化
 *
 * @author far2away
 * @since 2021/10/15
 */
public class P6spySqlFormat implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {
        return "#" + connectionId + "_" + elapsed + "ms_" + StrUtils.singleLine(sql);
    }

}

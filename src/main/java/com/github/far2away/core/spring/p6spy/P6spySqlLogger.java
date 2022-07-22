package com.github.far2away.core.spring.p6spy;

import com.github.far2away.core.definition.constant.StringConstants;
import com.github.far2away.core.util.generic.ObjectUtils;
import com.github.far2away.core.util.generic.StrUtils;
import com.github.far2away.core.util.holder.MetricsUtils;
import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * p6spy sql日志记录
 *
 * @author far2away
 * @since 2021/10/15
 */
@Slf4j(topic = "SQL_LOG")
public class P6spySqlLogger extends FormattedLogger {

    @Override
    public void logException(Exception e) {
        log.error("p6spy_log_exception", e);
    }

    @Override
    public void logText(String text) {
        log.info(text);
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed,
                       Category category, String prepared, String sql, String url) {

        //如果开启debug，DEBUG输出所有SQL日志，默认single line格式
        if (log.isDebugEnabled()) {
            String debugSql = now + "|" + elapsed + "|" + category + "|connection " + connectionId + "|url " +
                url + "|" + P6Util.singleLine(prepared) + "|" + P6Util.singleLine(sql);
            log.debug("p6spy_debug_{}", debugSql);
        }

        //获取输出msg
        final String msg = strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url);

        //处理ERROR, WARN, DEBUG级别
        if (Category.ERROR.equals(category)) {
            log.error(msg);
            return;
        } else if (Category.WARN.equals(category)) {
            log.warn(msg);
            return;
        } else if (Category.DEBUG.equals(category)) {
            log.debug(msg);
            return;
        }

        //INFO级别，如果日志为空，不打印，跳过
        if (!StringUtils.hasText(sql)) {
            return;
        }

        //INFO级别，记录正常SQL指标，根据耗时选择打印方式
        MetricsUtils.recordSqlExec(elapsed);
        if (elapsed < 1000L) {
            //小于1s，正常info形式输出
            log.info(msg);
        } else {
            //记录长SQL指标
            String singleLinePrepared = ObjectUtils.value(StrUtils.singleLine(prepared), StringConstants.EMPTY);
            String md5 = DigestUtils.md5DigestAsHex(singleLinePrepared.getBytes(StandardCharsets.UTF_8));
            MetricsUtils.recordLongSqlExec(elapsed, md5);

            if (elapsed < 5000L) {
                //在1-5s，已warn形式输出
                log.warn(msg);
            } else {
                //大于5s，已error形式输出
                log.error(msg);
            }
        }
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        //处理ERROR, WARN, DEBUG级别
        if (Category.ERROR.equals(category)) {
            return log.isErrorEnabled();
        } else if (Category.WARN.equals(category)) {
            return log.isWarnEnabled();
        } else if (Category.DEBUG.equals(category)) {
            return log.isDebugEnabled();
        }
        //默认INFO级别
        return log.isInfoEnabled();
    }

}

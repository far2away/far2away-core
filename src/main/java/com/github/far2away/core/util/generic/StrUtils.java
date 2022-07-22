package com.github.far2away.core.util.generic;

import com.github.far2away.core.definition.constant.StringConstants;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * 字符串补充工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class StrUtils {

    private final Pattern SINGLE_LINE_PATTERN = Pattern.compile("\\s{2,}|\\t|\\r|\\n");

    private final String UTF8_BOM = "\uFEFF";

    /**
     * 移除字符串的utf8 bom
     *
     * @param s the s
     * @return the string
     */
    @Nullable
    public String removeUtf8Bom(@Nullable String s) {
        if (Objects.isNull(s)) {
            return null;
        }
        if (s.startsWith(UTF8_BOM)) {
            return s.substring(1);
        }
        return s;
    }

    /**
     * 把字符串变成单行
     *
     * @param str 输入字符串
     * @return 输出单行字符串
     */
    @Nullable
    public String singleLine(@Nullable String str) {
        if (Objects.isNull(str)) {
            return null;
        }
        Matcher matcher = SINGLE_LINE_PATTERN.matcher(str);
        return matcher.replaceAll(" ");
    }

    /**
     * 字符串超过100时，截取97长度+省略号...
     *
     * @param str the str
     * @return the string
     */
    @Nullable
    public String withLimit100(@Nullable String str) {
        return withLimit(str, 100);
    }

    /**
     * 字符串超过255时，截取252长度+省略号...
     *
     * @param str the str
     * @return the string
     */
    @Nullable
    public String withLimit255(@Nullable String str) {
        return withLimit(str, 255);
    }

    /**
     * 字符串过长时，截取固定长度
     *
     * @param str   the str
     * @param limit the limit
     * @return the string
     */
    @Nullable
    public String withLimit(@Nullable String str, Integer limit) {
        if (Objects.isNull(str)) {
            return null;
        }
        if (str.length() <= limit) {
            return str;
        }
        int ellipsisLength = StringConstants.ELLIPSIS.length();
        if (limit <= ellipsisLength) {
            return str.substring(0, limit);
        }
        return str.substring(0, limit - ellipsisLength) + StringConstants.ELLIPSIS;
    }

}

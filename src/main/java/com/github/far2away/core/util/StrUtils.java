package com.github.far2away.core.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class StrUtils {

    private final Pattern SINGLE_LINE_PATTERN = Pattern.compile("\\s{2,}|\\t|\\r|\\n");

    private final String UTF8_BOM = "\uFEFF";

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

}

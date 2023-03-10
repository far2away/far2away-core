package com.github.far2away.core.util.generic;

import com.github.far2away.core.definition.constant.DecimalFormatHolders;
import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

/**
 * 数字工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@UtilityClass
public class DecimalUtils {

    /**
     * 把数字格式化为指定格式的字符串
     *
     * @param bigDecimal           big decimal
     * @param decimalFormatHolders decimal format holders
     * @return string
     */
    public String format(BigDecimal bigDecimal, DecimalFormatHolders decimalFormatHolders) {
        return decimalFormatHolders.getFormatter().format(bigDecimal);
    }

}

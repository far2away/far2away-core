package com.github.far2away.core.spring.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * BigDecimal类型序列化为字符串
 *
 * @author far2away
 * @since 2021/10/15
 */
public class BigDecimalAsStringSerializable extends StdSerializer<BigDecimal> {

    public final static BigDecimalAsStringSerializable INSTANCE = new BigDecimalAsStringSerializable();

    private static final long serialVersionUID = -4154144976354987564L;

    private static final int MAX_BIG_DECIMAL_SCALE = 9999;

    protected BigDecimalAsStringSerializable() {
        super(BigDecimal.class);
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // BigDecimal小数位判断
        if (!verifyBigDecimalRange(value)) {
            final String errorMsg = String.format("Attempt to write plain `java.math.BigDecimal` with illegal scale (%d): needs to be between"
                + " [-%d, %d]", value.scale(), MAX_BIG_DECIMAL_SCALE, MAX_BIG_DECIMAL_SCALE);
            provider.reportMappingProblem(errorMsg);
        }
        gen.writeString(value.toPlainString());
    }

    private boolean verifyBigDecimalRange(BigDecimal value) {
        int scale = value.scale();
        return ((scale >= -MAX_BIG_DECIMAL_SCALE) && (scale <= MAX_BIG_DECIMAL_SCALE));
    }

}

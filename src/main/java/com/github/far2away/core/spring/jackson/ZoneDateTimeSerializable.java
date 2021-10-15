package com.github.far2away.core.spring.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * ZoneDateTime类型序列化为数字-时间戳
 *
 * @author far2away
 * @since 2021/10/15
 */
public class ZoneDateTimeSerializable extends StdSerializer<ZonedDateTime> {

    public final static ZoneDateTimeSerializable INSTANCE = new ZoneDateTimeSerializable();

    private static final long serialVersionUID = 634399040386754889L;

    protected ZoneDateTimeSerializable() {
        super(ZonedDateTime.class);
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.toInstant().getEpochSecond());
    }

}

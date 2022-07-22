package com.github.far2away.core.spring.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.far2away.core.definition.i18n.I18nMessageEnumDetails;
import com.github.far2away.core.util.holder.I18nUtils;
import java.io.IOException;

/**
 * I18nMessageEnumDetails类型序列化为国际化文本
 *
 * @author far2away
 * @since 2021/10/15
 */
public class I18nMessageEnumDetailsSerializable extends StdSerializer<I18nMessageEnumDetails> {

    public final static I18nMessageEnumDetailsSerializable INSTANCE = new I18nMessageEnumDetailsSerializable();

    private static final long serialVersionUID = 4666561813493860669L;

    protected I18nMessageEnumDetailsSerializable() {
        super(I18nMessageEnumDetails.class);
    }

    @Override
    public void serialize(I18nMessageEnumDetails i18nMessageEnumDetails, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String i18nMessage = I18nUtils.getI18nMessage(i18nMessageEnumDetails);
        gen.writeString(i18nMessage);
    }

}

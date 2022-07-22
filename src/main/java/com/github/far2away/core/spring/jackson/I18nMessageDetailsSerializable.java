package com.github.far2away.core.spring.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.far2away.core.definition.i18n.I18nMessageDetails;
import com.github.far2away.core.util.holder.I18nUtils;
import java.io.IOException;

/**
 * I18nMessageDetails类型序列化为国际化文本
 *
 * @author far2away
 * @since 2021/10/15
 */
public class I18nMessageDetailsSerializable extends StdSerializer<I18nMessageDetails> {

    public final static I18nMessageDetailsSerializable INSTANCE = new I18nMessageDetailsSerializable();

    private static final long serialVersionUID = 730113960355665344L;

    protected I18nMessageDetailsSerializable() {
        super(I18nMessageDetails.class);
    }

    @Override
    public void serialize(I18nMessageDetails i18nMessageDetails, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String i18nMessage = I18nUtils.getI18nMessage(i18nMessageDetails);
        gen.writeString(i18nMessage);
    }

}

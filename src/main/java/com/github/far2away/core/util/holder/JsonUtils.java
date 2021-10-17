package com.github.far2away.core.util.holder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.far2away.core.definition.exception.JsonException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

/**
 * json工具类
 *
 * @author far2away
 * @since 2021/10/14
 */
@Slf4j
@UtilityClass
public class JsonUtils {

    public String toJsonString(Object object, @Nullable SerializationFeature... features) {
        try {
            ObjectMapper objectMapper = getInstance();
            if (Objects.isNull(features) || features.length == 0) {
                return objectMapper.writeValueAsString(object);
            } else {
                ObjectWriter writer;
                if (features.length == 1) {
                    writer = objectMapper.writer(features[0]);
                } else {
                    SerializationFeature[] serializationFeatures = Arrays.copyOfRange(features, 1, features.length);
                    writer = objectMapper.writer(features[0], serializationFeatures);
                }
                return writer.writeValueAsString(object);
            }
        } catch (JsonProcessingException e) {
            log.error("json_failed_object_to_json_{}_{}", object, e.getMessage());
            throw new JsonException(e);
        }
    }

    public JsonNode parseObject(String text, @Nullable DeserializationFeature... features) {
        try {
            ObjectMapper objectMapper = getInstance();
            if (Objects.isNull(features) || features.length == 0) {
                return objectMapper.readTree(text);
            } else {
                ObjectReader reader;
                if (features.length == 1) {
                    reader = objectMapper.reader(features[0]);
                } else {
                    DeserializationFeature[] deserializationFeatures = Arrays.copyOfRange(features, 1, features.length);
                    reader = objectMapper.reader(features[0], deserializationFeatures);
                }
                return reader.readTree(text);
            }
        } catch (JsonProcessingException e) {
            log.error("json_failed_json_to_object_{}_{}", text, e.getMessage());
            throw new JsonException(e);
        }
    }

    public <T> T parseObject(String text, Class<T> clazz, @Nullable DeserializationFeature... features) {
        return doParseObject(text, clazz, features);
    }

    public <T> T parseObject(String text, TypeReference<T> type, @Nullable DeserializationFeature... features) {
        return doParseObject(text, type.getType(), features);
    }

    private <T> T doParseObject(String text, Type type, @Nullable DeserializationFeature... features) {
        ObjectMapper objectMapper = getInstance();
        JavaType javaType = objectMapper.getTypeFactory().constructType(type);
        try {
            if (Objects.isNull(features) || features.length == 0) {
                return objectMapper.readValue(text, javaType);
            } else {
                ObjectReader reader;
                if (features.length == 1) {
                    reader = objectMapper.reader(features[0]);
                } else {
                    DeserializationFeature[] deserializationFeatures = Arrays.copyOfRange(features, 1, features.length);
                    reader = objectMapper.reader(features[0], deserializationFeatures);
                }
                return reader.readValue(text);
            }
        } catch (JsonProcessingException e) {
            log.error("json_failed_json_to_class_{}_{}_{}", type.getTypeName(), text, e.getMessage());
            throw new JsonException(e);
        }
    }

    public <T> List<T> parseArray(String text, Class<T> clazz) {
        ObjectMapper objectMapper = getInstance();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        try {
            return getInstance().readValue(text, typeFactory.constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            log.error("json_failed_json_to_class[]_{}_{}_{}", clazz.getName(), text, e.getMessage());
            throw new JsonException(e);
        }
    }

    /**
     * 格式化Json(美化)
     *
     * @return json
     */
    public String format(String json) {
        try {
            ObjectMapper mapper = getInstance();
            JsonNode node = mapper.readTree(json);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IOException e) {
            log.error("json_failed_json_format_{}_{}", json, e.getMessage());
            throw new JsonException(e);
        }
    }

    /**
     * 判断字符串是否是json
     *
     * @return json
     */
    public boolean isJson(String json) {
        try {
            getInstance().readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ObjectMapper getInstance() {
        return ObjectMapperHolder.INSTANCE;
    }

    private static class ObjectMapperHolder {

        private static final ObjectMapper INSTANCE = SpringUtils.getBean(ObjectMapper.class);

    }

}
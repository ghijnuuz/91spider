package me.gzj.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
    private final static ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.findAndRegisterModules();
        OBJECT_MAPPER.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private JsonUtil() {}

    public static String convertObject2String(Object object) {
        String result = null;
        try {
            result = OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception ignore) {
        }
        return result;
    }

    public static <T> T convertString2Object(String value, Class<T> tClass) {
        T result = null;
        try {
            result = OBJECT_MAPPER.readValue(value, tClass);
        } catch (Exception ignore) {
        }
        return result;
    }

    public static <T> T convertString2Object(String value, TypeReference typeReference) {
        T result = null;
        try {
            result = OBJECT_MAPPER.readValue(value, typeReference);
        } catch (Exception ignore) {
        }
        return result;
    }
}

package net.thinklog.common.kit;

import cn.hutool.core.convert.Convert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 基于Jackson的JSON转换工具类
 *
 * @author ye17186
 * @version 2018/6/29 12:06
 */
@Slf4j
public class JsonKit {

    private final static ObjectMapper MAPPER = SpringKit.getBean(ObjectMapper.class);

    /**
     * 对象转换为json字符串
     *
     * @param o 要转换的对象
     */
    public static String toJson(Object o) {
        return toJson(o, false);
    }

    /**
     * 对象转换为json字符串
     *
     * @param o      要转换的对象
     * @param format 是否格式化json
     */
    public static String toJson(Object o, boolean format) {
        try {
            if (o == null) {
                return "";
            }
            if (o instanceof Number) {
                return o.toString();
            }
            if (o instanceof String) {
                return (String) o;
            }
            if (format) {
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            }
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为指定对象
     *
     * @param json json字符串
     * @param cls  目标对象
     */
    public static <T> T parse(String json, Class<T> cls) {
        if (StrKit.isBlank(json) || cls == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为指定对象，并增加泛型转义
     * 例如：List<Integer> test = toObject(jsonStr, List.class, Integer.class);
     *
     * @param json             json字符串
     * @param parametrized     目标对象
     * @param parameterClasses 泛型对象
     */
    public static <T> T parse(String json, Class<?> parametrized, Class<?>... parameterClasses) {
        if (StrKit.isBlank(json) || parametrized == null) {
            return null;
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为指定对象
     *
     * @param json          json字符串
     * @param typeReference 目标对象类型
     */
    public static <T> T parse(String json, TypeReference<T> typeReference) {
        if (StrKit.isBlank(json) || typeReference == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为JsonNode对象
     *
     * @param json json字符串
     */
    public static JsonNode parse(String json) {
        if (StrKit.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转换为map对象
     *
     * @param o 要转换的对象
     */
    public static <K, V> Map<K, V> toMap(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return parse((String) o, Map.class);
        }
        return MAPPER.convertValue(o, Map.class);
    }

    /**
     * json字符串转换为list对象
     *
     * @param json json字符串
     */
    public static <T> List<T> toList(String json) {
        if (StrKit.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为list对象，并指定元素类型
     *
     * @param json json字符串
     * @param cls  list的元素类型
     */
    public static <T> List<T> toList(String json, Class<T> cls) {
        if (StrKit.isBlank(json)) {
            return null;
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, cls);
            return MAPPER.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 由于设置了long类型返回为文本，导致：long类型返回为：""1200000""这种形式的
     */
    public static Object toType(Type type, String json) {
        if (StrKit.isBlank(json)) {
            return null;
        }
        if ("java.lang.String".equals(type.getTypeName())) {
            return json;
        }
        if ("java.lang.Integer".equals(type.getTypeName())) {
            return Convert.toInt(json);
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructType(type);
            return MAPPER.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
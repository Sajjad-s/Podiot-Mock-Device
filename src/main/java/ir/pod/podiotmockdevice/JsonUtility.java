package ir.pod.podiotmockdevice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.JsonSyntaxException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Ali Mojahed on 6/4/2021
 * @project spring-template
 **/

@Log4j2
public class JsonUtility {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .registerModule(
                        new SimpleModule().addSerializer(Double.class, new JsonSerializer<Double>() {
                            @Override
                            public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                                jsonGenerator.writeNumber(new BigDecimal(value.toString()).toPlainString());
                            }
                        })
                );
    }

    public static String getStringJson(Object obj) throws JsonProcessException {
        try {
            mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("unsuccessful parsing json", e);
            throw new JsonProcessException(e);
        }
    }

    public static String getIntentStringJson(Object obj) throws JsonProcessException {
        try {
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("unsuccessful parsing json", e);
            throw new JsonProcessException(e);
        }
    }

    public static Map<?, ?> getMap(Object o) {
        return mapper.convertValue(o, Map.class);
    }

    public static String getWithNoTimestampJson(Object obj) throws JsonProcessException {
        try {
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("unsuccessful parsing json", e);
            throw new JsonProcessException(e);
        }
    }

    public static <T> T getObject(byte[] json, Class<T> classOfT) throws JsonProcessException {
        try {
            String content = new String(json, StandardCharsets.UTF_8);
            log.debug("byte content: " + content);
            return mapper.readValue(content, classOfT);
        } catch (IOException e) {
            throw new JsonProcessException(e, classOfT.getSimpleName());
        }
    }

    public static <T> T getObject(String json, Class<T> classOfT) throws JsonProcessException {
        try {
            return mapper.readValue(json, classOfT);
        } catch (IOException e) {
            throw new JsonProcessException(e, classOfT.getSimpleName());
        }
    }

    public static <T> T getObject(Map map, Class<T> classOfT) throws JsonProcessException {
        try {
            return mapper.convertValue(map, classOfT);
        } catch (IllegalArgumentException e) {
            throw new JsonProcessException(e, classOfT.getSimpleName());
        }
    }

    public static <T> T getObject(String json, TypeReference<T> typeReference) throws JsonProcessException {
        try {
            log.info(typeReference.toString());
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            String typeName = typeReference.getType().getTypeName();
            throw new JsonProcessException(e, typeName.substring(typeName.lastIndexOf(".") + 1));
        }
    }

    public static JsonNode getJsonObject(String json) throws JsonProcessException {
        try {
            return json == null ? null : mapper.readTree(json);
        } catch (IOException e) {
            throw new JsonProcessException(e);
        }
    }

    public static boolean isValidJson(String s) {
        try {
            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
            parser.parse(s);
        } catch (JsonSyntaxException jse) {
            return false;
        }

        return true;
    }

    // https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


}

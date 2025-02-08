package cn.cncc.caos.common.core.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

public class JacksonUtil {
  private final static ObjectMapper JSON_MAPPER = new ObjectMapper();

  static {
    SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    JSON_MAPPER.setDateFormat(smt);
    JSON_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    JSON_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    JSON_MAPPER.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);

    JavaTimeModule module = new JavaTimeModule();
    LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    module.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
    module.addSerializer(LocalDateTime.class, dateTimeSerializer);
    JSON_MAPPER.registerModule(module);
  }

  public static void writeObjToJsonFile(Object object, String file) throws IOException {
    try (Writer writer = Files.newBufferedWriter(Paths.get(file))) {
      JSON_MAPPER.writeValue(writer, object);
    }
  }

  public static byte[] objToBytes(Object object) throws JsonProcessingException {
    return JSON_MAPPER.writeValueAsBytes(object);
  }

  public static <T> T bytesToObj(String bytes, Class clazz) throws IOException {
    JSON_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    return (T) JSON_MAPPER.readValue(bytes, clazz);
  }

  public static String objToJson(Object object) throws JsonProcessingException {
    return JSON_MAPPER.writeValueAsString(object);
  }

  public static String objToJsonWithNull(Object object) throws JsonProcessingException {
    JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    String str = JSON_MAPPER.writeValueAsString(object);
    JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return str;
  }

  public static <T> T jsonToObj(String json, Class clazz) throws IOException {
    JSON_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    T t = (T) JSON_MAPPER.readValue(json, clazz);
    return t;
  }
  public static <T> T jsonToObj(String json, TypeReference<T> reference) throws IOException {
    JSON_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    T t = (T) JSON_MAPPER.readValue(json, reference);
    return t;
  }

  public static <T> T jsonToObj(byte[] json, Class clazz) throws IOException {
    JSON_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    T t = (T) JSON_MAPPER.readValue(json, clazz);
    return t;
  }

  public static <T> T objToObj(Object object, Class clazz) throws IOException {
    return  jsonToObj(objToJson(object),clazz);
  }

  public static <T> T readFromJsonFile(String file, Class<T> elementClass) throws IOException {
    File dataFile = new File(file);
    return readFromJsonFile(dataFile, elementClass);
  }

  public static <T> T readFromJsonFile(File file, Class<T> elementClass) throws IOException {
    if (!file.exists()) {
      return null;
    }
    return JSON_MAPPER.readValue(file, elementClass);
  }
  public static <T> T readFromJsonFile(File file, TypeReference<T> reference) throws IOException {
    if (!file.exists()) {
      return null;
    }
    return JSON_MAPPER.readValue(file, reference);
  }
  public static <T> T readFromJsonFile(File file, Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException {
    CollectionType type = JSON_MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    return JSON_MAPPER.readValue(file, type);
  }

  public static <T> T readFromJsonFile(String file, Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException {
    File dataFile = new File(file);
    if (!dataFile.exists()) {
      return null;
    }
    return readFromJsonFile(dataFile,collectionClass,elementClass);
  }

  public static <T> T readFromJsonFile(String file, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) throws IOException {
    File dataFile = new File(file);
    if (!dataFile.exists()) {
      return null;
    }
    return readFromJsonFile(dataFile, mapClass, keyClass, valueClass);
  }

  public static <T> T readFromJsonFile(File dataFile, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) throws IOException {
    MapType type = JSON_MAPPER.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    return JSON_MAPPER.readValue(dataFile, type);
  }

  public static <T> T jsonToObj(String content, Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException {
    CollectionType type = JSON_MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    return JSON_MAPPER.readValue(content, type);
  }

  public static <T> T jsonToObj(String content, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) throws IOException {
    MapType type = JSON_MAPPER.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    return JSON_MAPPER.readValue(content, type);
  }

  /**
   * 非深度合并两个对象
   *
   * @param m1
   * @param m2
   * @param <T>
   * @return
   * @throws IOException
   */
  public static <T> T merge(Map m1, Map m2) throws IOException {
    ObjectReader updater = JSON_MAPPER.readerForUpdating(m1);
    String m2Str = objToJson(m2);
    return updater.readValue(m2Str);
  }

  public static <T> T mergeDepth(Map m1, Map m2) throws Exception {
    JsonNode m1JsonNode = (JsonNode) JSON_MAPPER.readTree(JSON_MAPPER.writeValueAsString(m1));
    JsonNode m2JsonNode = (JsonNode) JSON_MAPPER.readTree(JSON_MAPPER.writeValueAsString(m2));
    m1JsonNode = mergeD(m1JsonNode, m2JsonNode);
    return JSON_MAPPER.convertValue(m1JsonNode, (Class<T>) Map.class);
  }

  private static JsonNode mergeD(final JsonNode target, final JsonNode source) {
    if (target instanceof ArrayNode && source instanceof ArrayNode) {
      ((ArrayNode) target).addAll((ArrayNode) source);
      return target;
    } else if (target instanceof ObjectNode && source instanceof ObjectNode) {
      final Iterator<Map.Entry<String, JsonNode>> iterator = source.fields();
      while (iterator.hasNext()) {
        final Map.Entry<String, JsonNode> sourceFieldEntry = iterator.next();
        final JsonNode targetFieldValue = target.get(sourceFieldEntry.getKey());
        if (targetFieldValue != null) {
          final JsonNode newTargetFieldValue = mergeD(targetFieldValue, sourceFieldEntry.getValue());
          ((ObjectNode) target).set(sourceFieldEntry.getKey(), newTargetFieldValue);
        } else {
          ((ObjectNode) target).set(sourceFieldEntry.getKey(), sourceFieldEntry.getValue().deepCopy());
        }
      }
      return target;
    } else {
      return source.deepCopy();
    }
  }
}

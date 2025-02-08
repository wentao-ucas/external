package cn.cncc.caos.common.core.config;

import cn.cncc.mojito.rpc.common.constant.SerializeType;
import cn.cncc.mojito.rpc.serialization.Serializer;
import cn.cncc.mojito.rpc.serialization.SerializerFactory;
import cn.cncc.mojito.rpc.serialization.exception.DeserializationException;
import cn.cncc.mojito.rpc.serialization.exception.SerializationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * 对内部接口生效
 */
@Slf4j
public abstract class BaseJacksonSerializerConfig implements Serializer {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    // 属性为 NULL 不序列化
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    // 对象为空时是否报错，默认true
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // 如果出现自身引用自身的情况，则把成员对象变成null，例如Book的成员对象里有Book类型
    objectMapper.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
    objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

    JavaTimeModule module = new JavaTimeModule();
    LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    module.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
    module.addSerializer(LocalDateTime.class, dateTimeSerializer);
    objectMapper.registerModule(module);
  }

  public abstract void init();
  public <T extends BaseJacksonSerializerConfig> void initSerializer(T config) {
    SerializerFactory.getInstance().unregisterSerializer(SerializeType.JSON.name());
    SerializerFactory.getInstance().registerSerializer(SerializeType.JSON.name(), config);
  }

  /**
   * Jackson的序列化方法
   *
   * @param t   待序列化的对象
   * @param <T> 泛型类型
   * @return 序列化后的json串, 字节数组格式
   */
  @Override
  public <T> byte[] serialize(T t) throws SerializationException {
    long start = System.currentTimeMillis();
    try {
      return objectMapper.writeValueAsBytes(t);
    } catch (JsonProcessingException e) {
      log.error("Jackson序列化错误", e);
      throw new SerializationException(e.getMessage(), e.getCause());
    } finally {
      log.debug("jackson反序列化耗时: " + (System.currentTimeMillis() - start));
    }
  }

  /**
   * Jackson的反序列化方法
   *
   * @param bytes 待反序列化的json串,字节数组格式
   * @param type  反序列化输出的对象类型
   * @param <T>   泛型类型
   * @return 反序列化后的对象
   */
  @Override
  public <T> T deserialize(byte[] bytes, Type type) throws DeserializationException {
    long start = System.currentTimeMillis();
    if (bytes == null || bytes.length == 0) {
      return null;
    }
    try {
      return objectMapper.readValue(bytes, objectMapper.constructType(type));
    } catch (Exception e) {
      log.debug("jackson反序列化错误, [source bytes: {}], [targetType:{}]", Arrays.toString(bytes), type.getTypeName(), e);
      throw new DeserializationException("jackson反序列化错误, source bytes: [ " + Arrays.toString(bytes) + " ]", e);
    } finally {
      log.debug("jackson反序列化耗时: " + (System.currentTimeMillis() - start));
    }
  }

  @Override
  public <T> T deserialize(byte[] bytes, Type[] clazz) throws DeserializationException {
    JavaType[] javaTypes = new JavaType[clazz.length];
    for (int i = 0; i < clazz.length; i++) {
      JavaType javaType = objectMapper.getTypeFactory().constructType(clazz[i]);
      javaTypes[i] = javaType;
    }
    return (T) deserialize(bytes, javaTypes);
  }

  private Object[] deserialize(byte[] bytes, JavaType[] clazzList) throws DeserializationException {
    if (clazzList == null || clazzList.length == 0) {
      return new Object[0];
    }
    Object[] args = new Object[clazzList.length];

    try {

      JsonNode node = objectMapper.readTree(bytes);

      // json data is json arry
      if (node.isArray()) {
        // first parameter is Array or Collection Type
        if (clazzList.length == 1) {
          if (!clazzList[0].isCollectionLikeType() && !clazzList[0].isArrayType()) {
            throw new DeserializationException("JSON data can't be json array");
          }
          args[0] = objectMapper.readValue(node.traverse(), clazzList[0]);
          return args;
        } else {
          // if there is more than one parameter, but request json array size is not equal class type size.
          if (clazzList.length != node.size()) {
            throw new DeserializationException("JSON Array size is not equal parameter size");
          }

          for (int i = 0; i < clazzList.length; i++) {
            args[i] = objectMapper.readValue(node.get(i).traverse(), clazzList[i]);
          }
        }

      } else {

        if (clazzList.length > 1) {
          throw new DeserializationException("JSON data must be json array");
        }

        // json is other type(eg. map object string int...)
        args[0] = objectMapper.readValue(node.traverse(), clazzList[0]);
      }

      return args;
    } catch (Exception e) {
      throw new DeserializationException("jackson反序列化错误, source bytes: [ " + Arrays.toString(bytes) + " ]", e);
    }
  }
}

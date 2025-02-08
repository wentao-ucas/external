package cn.cncc.caos.common.core.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * 对外部接口生效
 */
public class BaseJacksonConverterConfig implements WebMvcConfigurer {
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper(); //jackson2HttpMessageConverter.getObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    // 如果出现自身引用自身的情况，则把成员对象变成null，例如Book的成员对象里有Book类型
    objectMapper.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
    // 对象为空时是否报错，默认true
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // 属性为 NULL 不序列化(加上此配置，会导致为空数据不返回前端判断有问题)
    // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    JavaTimeModule module = new JavaTimeModule();
    LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    module.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
    module.addSerializer(LocalDateTime.class, dateTimeSerializer);
    objectMapper.registerModule(module);

    jackson2HttpMessageConverter.setObjectMapper(objectMapper);
    // 放到第一个
    converters.add(0, jackson2HttpMessageConverter);
  }
}
package cn.cncc.caos.common.core.utils;

import cn.cncc.caos.common.core.annotation.ErrorDescription;
import cn.cncc.caos.common.core.response.IResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: BaseResponseUtil
 * @Description: TODO
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/11 10:50
 */

@Slf4j
public class BaseResponseUtil {
    private final static Map<String, String> RESPONSE_INFO = new HashMap<>();

    static {
        Field[] fields = IResponse.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(ErrorDescription.class)) {
                    ErrorDescription errorDescription = field.getDeclaredAnnotation(ErrorDescription.class);
                    Object value = field.get(null);
                    RESPONSE_INFO.put(String.valueOf(value), errorDescription.value());
                }
            }
        } catch (IllegalAccessException e) {
            log.error("", e);
        }
    }
    public static Map<String, String> getResponseErrMap(){return RESPONSE_INFO;}
}

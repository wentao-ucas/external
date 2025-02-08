package cn.cncc.caos.common.core.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;

public class CAOSUtil {
  public static StringBuilder buildErrorMessage(List<ObjectError> errors) {
    StringBuilder message = new StringBuilder();
    for (int i = 0; i < errors.size(); i++) {
      String fieldName = null;
      ObjectError error = errors.get(i);
      if (Objects.requireNonNull(error.getArguments()).length > 0) {
        Object resolvable = error.getArguments()[0];
        if (resolvable instanceof DefaultMessageSourceResolvable) {
          DefaultMessageSourceResolvable _resolvable = (DefaultMessageSourceResolvable) resolvable;
          fieldName = _resolvable.getDefaultMessage();
        }
      }
      message.append(i + 1).append(".");
      if (!StringUtil.isEmpty(fieldName)) {
        message.append(fieldName).append(": ");
      }
      message.
          append(error.getDefaultMessage()).
          append(" ");
    }
    return message;
  }
}
